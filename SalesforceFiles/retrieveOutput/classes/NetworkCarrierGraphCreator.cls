/**
https://c.na9.visual.force.com/apex/graphView?appLabel=my+first+app&requestType=NET_CARRIER

http://stackoverflow.com/questions/2919414/get-network-type

ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

//mobile
State mobile = conMan.getNetworkInfo(0).getState();

//wifi
State wifi = conMan.getNetworkInfo(1).getState();

*/

public class NetworkCarrierGraphCreator extends GraphCreator {
    private NetworkCarrierGraphRequest request;
    
    public NetworkCarrierGraphCreator(NetworkCarrierGraphRequest request) {
        this.request = request;
    }

    public override Datatable createJSDataTable(){
        List<AggregateResult> networkTypes;
        String appLabel = request.getAppLabel();
        
        List<Datatable.Column> tableColumns = new List<Datatable.Column>();
        tableColumns.add(new Datatable.Column('network', 'string', 'Network Carrier'));
        tableColumns.add(new Datatable.Column('counts', 'number', 'Count'));
        Datatable table = new Datatable(tableColumns);
        
        networkTypes = [SELECT s.C4PPMM__NetworkCarrier__c theNetwork, COUNT(Id) theCount
                        FROM C4PPMM__Session__c s
                        WHERE s.C4PPMM__App__r.C4PPMM__Label__c =: appLabel
                        GROUP BY s.C4PPMM__NetworkCarrier__c];
        
        for (AggregateResult result : networkTypes) {
            List<Object> cellVals = new List<Object>();
            Object network = result.get('theNetwork');
            Object theCount = result.get('theCount');
            System.debug('NETWORK IS: ' + network);
            System.debug('THE COUNT IS: ' + theCount);
            if (network instanceof String && theCount instanceof Integer) {
                cellVals.add(network);
                cellVals.add(theCount);
                table.addRow(cellVals);
            }
        }
        
        System.debug('TABLE INNER: ' + table.getRowCount());
        System.debug('value at: ' + table.getValueAt(0,0));
        System.debug('value at: ' + table.getValueAt(1,0));
        System.debug('value at: ' + table.getValueAt(0,1));
        System.debug('value at: ' + table.getValueAt(1,1));
        
        return table;
    }

}