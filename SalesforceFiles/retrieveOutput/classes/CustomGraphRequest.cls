public class CustomGraphRequest extends GraphRequest {
    private String attribName1;
    private String eventName1;
    private String appLabel;
    
    public override GraphRequest.RequestType getRequestType() {
        return GraphRequest.RequestType.CUSTOM;
    }
    
    public CustomGraphRequest() {
    }
    
    public void setAttribName1(String attribName1) {
        this.attribName1 = attribName1;
    }
    
    public void setEventName1(String eventName1) {
        this.eventName1 = eventName1;
    }
    
    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }
    
    public String getAttribName1() {
        return attribName1;
    }
    
    public String getEventName1() {
        return eventName1;
    }
    
    public String getAppLabel() {
        return appLabel;
    }
}