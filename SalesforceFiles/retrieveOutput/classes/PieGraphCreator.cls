/**
 * PieGraphCreator
 * Graph request handler for a pie graph
 */
public class PieGraphCreator extends GraphCreator {
    
    public PieGraphCreator(){}
    
    //see documentation for this method in GraphCreator
    public override Datatable createJSDataTable(GraphRequest requestedGraph){
        
        List<Datatable.Column> tableColumns = new List<Datatable.Column>();
        tableColumns.add(new Datatable.Column('vals', 'string', 'Device Object'));
        tableColumns.add(new Datatable.Column('counts', 'num', 'count'));
        Datatable table = new Datatable(tableColumns);
        
        String appName = requestedGraph.getAppName();
        String firstEventName = requestedGraph.getFirstAttribEvent();
        String firstAttribName = requestedGraph.getFirstAttrib();
        
        List<AggregateResult> sessions;
        if(firstEventName == null || firstEventName == '')
        {
            String soqlQuery = 'SELECT ' + firstAttribName + ' value , COUNT(Id) theCount '
                             + 'FROM Session__c s '
                             + 'WHERE s.App__r.Name = \'' + appName + '\' '
                             + 'GROUP BY ' + firstAttribName;
            
            sessions = Database.query(soqlQuery);
        }
        else
        {
            sessions = [SELECT a.value__c value, COUNT(Id) theCount 
                        FROM  Attribute__c a
                        WHERE a.Event__r.Session__r.App__r.Name = :appName
                          AND a.Event__r.EventName__c = :firstEventName
                          AND a.key__c = :firstAttribName
                        GROUP BY a.value__c];
        }
                                          
        for(AggregateResult result : sessions){
            List<Object> cellVals = new List<Object>();
            Object sliceName = result.get('value');
            Object count = result.get('theCount');
            if(sliceName instanceof String && count instanceof Integer){
                
                cellVals.add(sliceName);
                cellVals.add(count);
                table.addRow(cellVals);
            }
        }
        
        System.debug(JSON.serialize(table));
        return table; 
    }
}