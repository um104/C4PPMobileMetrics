/**
 * PieGraphCreator
 * Graph request handler for a pie graph
 */
public class CustomGraphCreator extends GraphCreator {
    private CustomGraphRequest request;
    
    public CustomGraphCreator(CustomGraphRequest request) {
        this.request = request;
    }
    
    //see documentation for this method in GraphCreator
    public override Datatable createJSDataTable() {
        
        List<Datatable.Column> tableColumns = new List<Datatable.Column>();
        tableColumns.add(new Datatable.Column('vals', 'string', 'Device Object'));
        tableColumns.add(new Datatable.Column('counts', 'number', 'count'));
        Datatable table = new Datatable(tableColumns);
        
        String appLabel = request.getAppLabel();
        String firstEventName = request.getEventName1();
        String firstAttribName = request.getAttribName1();
        
        List<AggregateResult> sessions;
        if(firstEventName == null || firstEventName == '')
        {
            String soqlQuery = 'SELECT ' + firstAttribName + ' value , COUNT(Id) theCount ' + 
                               'FROM Session__c s '  + 
                               'WHERE s.App__r.Label__c = \'' + appLabel + '\' ' + 
                               'GROUP BY ' + firstAttribName;
            sessions = Database.query(soqlQuery);
        }
        else
        {
            sessions = [SELECT a.value__c value, COUNT(Id) theCount
                        FROM  Attribute__c a 
                        WHERE a.Event__r.Session__r.App__r.Label__c = :appLabel 
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

        return table; 
    }
}