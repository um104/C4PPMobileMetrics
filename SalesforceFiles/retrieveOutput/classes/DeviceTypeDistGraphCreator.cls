public class DeviceTypeDistGraphCreator extends GraphCreator
{
    private DeviceTypeDistGraphRequest request;
    
    public DeviceTypeDistGraphCreator(DeviceTypeDistGraphRequest request)
    {
        this.request = request;
    }
    
    public override Datatable createJSDataTable()
    {
        CustomGraphRequest cgr = new CustomGraphRequest();
        cgr.setAppLabel(request.getAppLabel());
        cgr.setEventName1('');
        cgr.setAttribName1('DeviceType__c');
        
        CustomGraphCreator cgc = new CustomGraphCreator(cgr);
        
        return cgc.createJSDataTable();
    }
}