/**
	

*/

public class LineGraphCreator extends GraphCreator {
    public LineGraphCreator(){}
    
    //see documentation for this method in GraphCreator
    public override Datatable createJSDataTable(GraphRequest requestedGraph){
        List<Datatable.Column> tableColumns = new List<Datatable.Column>();
        //tableColumns.add(new Datatable.Column('eventName', 'string', 'Event Name'));
        tableColumns.add(new Datatable.Column('eventTime', 'num', 'Event Time'));
        tableColumns.add(new Datatable.Column('timeFrame', 'num', 'Time Frame'));
        Datatable table = new Datatable(tableColumns);
        
        String appName = requestedGraph.getAppName();
        String firstEventName = requestedGraph.getFirstAttribEvent();
        String firstAttribName = requestedGraph.getFirstAttrib();
        final Integer timeInterval = 3600;
        //TODO(rmtam): Time Interval needs to be fixed, it needs to be relative to epoch time
        //Datetime theTime = new Datetime();
        //Integer currentTime = theTime.getTime()*1000;
        Integer currentTime;
        
        List<AggregateResult> sessions;
        if (firstEventName == null || firstEventName == '') {
            String soqlQuery = 'SELECT ' + firstAttribName + ' value ,'
                             + 'FROM Event__c e '
                             + 'WHERE e.App__r.Name = \'' + appName + '\' '
                             + 'GROUP BY ' + firstAttribName;
            
            sessions = Database.query(soqlQuery);
        }
        else {
            sessions = [SELECT a.event__r.WallTimeSeconds__c seconds
                        FROM  Attribute__c a
                        WHERE a.Event__r.Session__r.App__r.Name = :appName
                          AND a.Event__r.EventName__c = :firstEventName
                          AND a.key__c = :firstAttribName // don't get the key
                        GROUP BY a.event__r.WallTimeSeconds__c];
        }
        // Time, EventName from eventstable where App_r.Name is the app
        // Walltime is between min max given (Where or forloop)
        // 
        currentTime = 0;
        
        for (AggregateResult result : sessions) {
            List<Object> cellVals = new List<Object>();
            Object eventTime = result.get('seconds');
            currentTime += timeInterval;
            Object timeMeasure = currentTime;
            if(eventTime instanceof Integer){
                cellVals.add(eventTime);
                cellVals.add(timeMeasure);
                table.addRow(cellVals);
            }
        }
        
        System.debug(JSON.serialize(table));
        return table; 
    }
}