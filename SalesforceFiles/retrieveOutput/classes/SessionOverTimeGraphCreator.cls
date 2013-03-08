public class SessionOverTimeGraphCreator extends GraphCreator{
    
    public class InvalidTimeIntervalException extends Exception{
    }
    
    private SessionOverTimeGraphRequest request;
    
    public SessionOverTimeGraphCreator(SessionOverTimeGraphRequest request){
        this.request = request;
        
    }
    
    public override Datatable createJSDatatable(){
        
        String appLabel = request.getAppLabel();
        String timeInterval = request.getTimeInterval();
        Datatable datatable;
        
        if(timeInterval == 'DAY'){
            datatable = createToday(appLabel, timeInterval);
        }
        else if(timeInterval == 'WEEK'){
            datatable = createLastWeek(appLabel, timeInterval);
        }
        else if(timeInterval == 'MONTH'){
            datatable = createLastMonth(appLabel, timeInterval);
        }
        else{
            throw new InvalidTimeIntervalException('Time interval must be either DAY, WEEK, MONTH.');
        }
        
        return  datatable;
    }
    
    private Datatable createToday(String appLabel, String timeInterval){
        List<Datatable.Column> cols = new List<Datatable.Column>();
        
        cols.add(new Datatable.Column('Intervals', 'string', 'Intervals'));
        cols.add(new Datatable.Column('Counts', 'number', 'Counts'));
        Datatable datatable = new Datatable(cols);
        Long minTime = dateTime.now().addDays(-1).getTime();
        minTime = minTime / 1000;
        Long maxTime = dateTime.now().getTime();
        maxTime = maxTime / 1000;
        
        List<AggregateResult> results = [SELECT HOUR_IN_DAY(convertTimeZone(SessionTime__c)) hour, COUNT(id) theCount
                                         FROM Session__c
                                         WHERE EpochTime__c > :minTime
                                           AND EpochTime__c < :maxTime
                                           AND App__r.Label__c = :appLabel
                                         GROUP BY HOUR_IN_DAY(convertTimeZone(SessionTime__c))];
        
        Map<Integer, Integer> dateValues = new Map<Integer, Integer>();
        
        List<Datetime> dates = new List<Datetime>();
        for(integer i = 0; i > -24; i--){
            dates.add(datetime.now().addHours(i));
        }
        dates.sort();
        
        for(AggregateResult result : results){
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
            
            datatable.addRow(cellVals);
        }
        
        System.debug(JSON.serialize(datatable));
        return datatable;
    }
    
    private Datatable createLastWeek(String appLabel, String timeInterval){
        List<Datatable.Column> cols = new List<Datatable.Column>();
        
        cols.add(new Datatable.Column('Intervals', 'string', 'Intervals'));
        cols.add(new Datatable.Column('Counts', 'number', 'Counts'));
        Datatable datatable = new Datatable(cols);
        Long minTime = dateTime.now().addDays(-7).getTime();
        minTime = minTime / 1000;
        Long maxTime = dateTime.now().getTime();
        maxTime = maxTime / 1000;
        
        List<AggregateResult> results = [SELECT DAY_ONLY(convertTimeZone(SessionTime__c)) day, COUNT(id) theCount
                                         FROM Session__c
                                         WHERE EpochTime__c > :minTime
                                           AND EpochTime__c < :maxTime
                                           AND App__r.Label__c = :appLabel
                                         GROUP BY DAY_ONLY(convertTimeZone(SessionTime__c))];
        
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
    
    private Datatable createLastMonth(String appLabel, String timeInterval){
        List<Datatable.Column> cols = new List<Datatable.Column>();
        
        cols.add(new Datatable.Column('Intervals', 'string', 'Intervals'));
        cols.add(new Datatable.Column('Counts', 'number', 'Counts'));
        Datatable datatable = new Datatable(cols);
        Long minTime = dateTime.now().addMonths(-1).getTime();
        minTime = minTime / 1000;
        Long maxTime = dateTime.now().getTime();
        maxTime = maxTime / 1000;
        
        List<AggregateResult> results = [SELECT DAY_ONLY(convertTimeZone(SessionTime__c)) day, COUNT(id) theCount
                                         FROM Session__c
                                         WHERE EpochTime__c > :minTime
                                           AND EpochTime__c < :maxTime
                                           AND App__r.Label__c = :appLabel
                                         GROUP BY DAY_ONLY(convertTimeZone(SessionTime__c))];
        
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