public class NetworkCarrierGraphRequest extends GraphRequest{
    private String appLabel;
    public NetworkCarrierGraphRequest() {
    
    }
    
    public override GraphRequest.RequestType getRequestType(){
        return GraphRequest.RequestType.NET_CARRIER;
    }
    
    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;    
    }
    
    public String getAppLabel() {
        return this.appLabel;       
    }
    
    

}