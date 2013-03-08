public class GeochartGraphCreator extends GraphCreator
{
    private GeochartGraphRequest request;
    
    public GeochartGraphCreator(GeochartGraphRequest request)
    {
        this.request = request;
    }
    
    public override Datatable createJSDataTable()
    {
        List<Datatable.Column> tableColumns = new List<Datatable.Column>();
        tableColumns.add(new Datatable.Column('Country', 'string', 'Country'));
        tableColumns.add(new Datatable.Column('Popularity', 'number', 'Popularity'));
        Datatable table = new Datatable(tableColumns);
        String appLabel = request.getAppLabel();
        
        List<AggregateResult> soqlResult = [SELECT DeviceCountry__c Country, COUNT(Id) CountryCount 
                                            FROM Session__c s
                                            WHERE App__r.Label__c = :appLabel
                                            GROUP BY DeviceCountry__c];
        
        for(AggregateResult result : soqlResult)
        {
            List<Object> cellVals = new List<Object>();
            Object country = result.get('Country');
            Object countryCount = result.get('CountryCount');
            if(country instanceof String && countryCount instanceof Integer)
            {    
                cellVals.add(country);
                cellVals.add(countryCount);
                table.addRow(cellVals);
            }
        }
        
        System.debug(JSON.serialize(table));
        return table;
    }
}