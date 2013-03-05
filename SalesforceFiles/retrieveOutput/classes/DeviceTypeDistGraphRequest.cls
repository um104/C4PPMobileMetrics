public class DeviceTypeDistGraphRequest extends GraphRequest
{
    private String appLabel;
    
    public override GraphRequest.RequestType getRequestType()
    {
        return GraphRequest.RequestType.DEVICE_TYPE_DIST;
    }
    
    public DeviceTypeDistGraphRequest()
    {
    }
    
    public void setAppLabel(String appLabel) 
    {
        this.appLabel = appLabel;
    }
    
    public String getAppLabel() 
    {
        return appLabel;
    }
}