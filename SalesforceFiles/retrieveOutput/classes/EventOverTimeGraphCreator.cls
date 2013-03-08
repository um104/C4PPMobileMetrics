/**
	Author: Ray Tam

	--Developer Notes

		Working soql query for specified EventName and AppName 

		SELECT e.WallTimeSeconds__c
                    FROM Event__c e
                    WHERE e.EventName__c = 'button pressed'
                    AND e.Session__r.App__r.Name = 'a04E00000034dlH'
                    AND e.WallTimeSeconds__c < 1360960000
                    AND e.WallTimeSeconds__c > 1360913380


		events = [SELECT e.WallTimeSeconds__c
                    FROM Event__c e
                    WHERE e.EventName__c =: eventName
                    AND e.Session__r.App__r.Name =: appLabel
                    AND e.WallTimeSeconds__c <: endTime
                    AND e.WallTimeSeconds__c >: startTime];

*/

public class EventOverTimeGraphCreator extends GraphCreator {
    
    public class InvalidTimeIntervalException extends Exception {
        /*
        public TimeIntervalException(String message) {
        	super(message);    
        }
		*/
    }
    
    private EventOverTimeGraphRequest request;
    
    public EventOverTimeGraphCreator(EventOverTimeGraphRequest request){
        this.request = request;
    }
    
    //see documentation for this method in GraphCreator
    public override Datatable createJSDataTable(){
        //final Integer timeInterval = 3600;
        //TODO(rmtam): Time Interval needs to be fixed, it needs to be relative to epoch time
        //Datetime theTime = DateTime.now(); 
        //Long theDateTime = theTime.getTime();
        
        List<AggregateResult> events;
        
        String appLabel = request.getAppLabel();
        String eventName = request.getEventName();
        String interval = request.getInterval();
        
        Datatable table;
        

        if (interval.equals('DAY')) {
			table = getDayDatatable(appLabel, eventName);
        }
        else if (interval.equals('WEEK')) {
            table = getWeekDatatable(appLabel, eventName);
        }
        else if (interval.equals('MONTH')) {
            table = getMonthDatatable(appLabel, eventName);
        }
        else {
        	throw new InvalidTimeIntervalException('Time interval must be either DAY, WEEK, MONTH.');    
        }    
        /*
        for (AggregateResult result : events) {
            List<Object> cellVals = new List<Object>();
            Object eventTime = (Integer)result.get('timeCount');
            Object timeMeasure = (Integer)result.get('timeInterval');
            
            cellVals.add(timeMeasure);
            cellVals.add(eventTime);
            
            table.addRow(cellVals);
        }
        */
        System.debug(JSON.serialize(table));
        return table;
    }
    //convertTime zone
    //Day only, hour only
    private Datatable getDayDatatable(String appLabel, String eventName) {
		List<AggregateResult> events;
        
        List<Datatable.Column> tableColumns = new List<Datatable.Column>();
        tableColumns.add(new Datatable.Column('Intervals', 'string', 'Intervals'));
        tableColumns.add(new Datatable.Column('Events', 'number', 'Events'));
        Datatable table = new Datatable(tableColumns);
        
        
        //dateTime minTime = dateTime.now().addDays(-1);
        //dateTime maxTime = dateTime.now();
        
        Long minTime = dateTime.now().addDays(-1).getTime();
        minTime = minTime / 1000;
        Long maxTime = dateTime.now().getTime();
        maxTime = maxTime / 1000;

        
        events = [SELECT HOUR_IN_DAY(convertTimezone(e.EventTime__c)) hour, COUNT(e.EventTime__c) theCount
                  FROM Event__c e
                  WHERE e.EventName__c =: eventName
                  AND e.Session__r.App__r.Label__c =: appLabel
                  AND e.Session__r.EpochTime__c >: minTime
                  AND e.Session__r.EpochTime__c <: maxTime
                  GROUP BY HOUR_IN_DAY(convertTimezone(e.EventTime__c))];
        
        Map<Integer, Integer> dateValues = new Map<Integer, Integer>();
        
        List<Datetime> dates = new List<Datetime>();
        for(integer i = 0; i > -24; i--){
            dates.add(datetime.now().addHours(i));
        }
        dates.sort();
        
        for(AggregateResult result : events){
            Integer hour = (Integer)result.get('hour');
            Integer quantity = (Integer)result.get('theCount');
            dateValues.put(hour, quantity);
        }
        
        for(Datetime currentDate : dates){
            List<Object> cellVals = new List<Object>();
            cellVals.add(currentDate.format('M/d ha'));
            
            Integer count = dateValues.get(currentDate.hour());
            if(count == null){
                count = 0;
            }
            
            cellVals.add(count);
            table.addRow(cellVals);
        }
        
        System.debug(JSON.serialize(table));
        return table;
    }
    
    private Datatable getWeekDatatable(String appLabel, String eventName) {
        List<Datatable.Column> cols = new List<Datatable.Column>();
        
        cols.add(new Datatable.Column('Intervals', 'string', 'Intervals'));
        cols.add(new Datatable.Column('Counts', 'number', 'Counts'));
        Datatable datatable = new Datatable(cols);
        Long minTime = dateTime.now().addDays(-7).getTime();
        minTime = minTime / 1000;
        Long maxTime = dateTime.now().getTime();
        maxTime = maxTime / 1000;
        
        List<AggregateResult> results = [SELECT DAY_ONLY(convertTimeZone(e.EventTime__c)) day, COUNT(e.EventTime__c) theCount
                                         FROM Event__c e
                                         WHERE e.Session__r.EpochTime__c >: minTime
                  						   AND e.Session__r.EpochTime__c <: maxTime
                                           AND e.Session__r.App__r.Label__c =: appLabel
                                           AND e.EventName__c =: eventName
                                         GROUP BY DAY_ONLY(convertTimeZone(e.EventTime__c))];
        
        Map<String, Integer> dateValues = new Map<String, Integer>();
        
        List<Date> dates = new List<Date>();
        for(integer i = 0; i > -7; i--){
            dates.add(datetime.now().addDays(i).date());
        }
        dates.sort();
        
        for(AggregateResult result : results){
            Date resultDate = (Date)result.get('day');
            Integer quantity = (Integer)result.get('theCount');
            dateValues.put(resultDate.format(), quantity);
        }
        
        dates.sort();
        for(Date currentDate : dates){
            List<Object> cellVals = new List<Object>();
            cellVals.add(currentDate.format());
            
            Integer count = dateValues.get(currentDate.format());
            if(count == null){
                count = 0;
            }
            cellVals.add(count);
            
            datatable.addRow(cellVals);
        }
        
        System.debug(JSON.serialize(datatable));
        return datatable;
    }
    
    private Datatable getMonthDatatable(String appLabel, String eventName) {
        List<Datatable.Column> cols = new List<Datatable.Column>();
        
        cols.add(new Datatable.Column('Intervals', 'string', 'Intervals'));
        cols.add(new Datatable.Column('Counts', 'number', 'Counts'));
        Datatable datatable = new Datatable(cols);
        Long minTime = dateTime.now().addMonths(-1).getTime();
        minTime = minTime / 1000;
        Long maxTime = dateTime.now().getTime();
        maxTime = maxTime / 1000;
        
        List<AggregateResult> results = [SELECT DAY_ONLY(convertTimeZone(e.EventTime__c)) day, COUNT(e.EventTime__c) theCount
                                         FROM Event__c e
                                         WHERE e.Session__r.EpochTime__c >: minTime
                  						   AND e.Session__r.EpochTime__c <: maxTime
                                           AND e.Session__r.App__r.Label__c =: appLabel
                                           AND e.EventName__c =: eventName
                                         GROUP BY DAY_ONLY(convertTimeZone(e.EventTime__c))];
        
        Map<String, Integer> dateValues = new Map<String, Integer>();
        
        List<Date> dates = new List<Date>();
        for(integer i = 0; i > -30; i--){
            dates.add(datetime.now().addDays(i).date());
        }
        dates.sort();
        
        for(AggregateResult result : results){
            Date resultDate = (Date)result.get('day');
            Integer quantity = (Integer)result.get('theCount');
            dateValues.put(resultDate.format(), quantity);
        }
        
        dates.sort();
        for(Date currentDate : dates){
            List<Object> cellVals = new List<Object>();
            cellVals.add(currentDate.format());
            
            Integer count = dateValues.get(currentDate.format());
            if(count == null){
                count = 0;
            }
            cellVals.add(count);
            
            datatable.addRow(cellVals);
        }
        
        System.debug(JSON.serialize(datatable));
        return datatable;
    }
}