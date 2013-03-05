public class EventOverTimeGraphRequest extends GraphRequest{
    private String eventName;
    private String interval;
    private String appLabel;
    
    public override GraphRequest.RequestType getRequestType(){
        return GraphRequest.RequestType.EVENT_OVER_TIME;
    }
    
    public EventOverTimeGraphRequest(){
    }
    
    public String getInterval() {
    	return interval;
    }
    
    public void setInterval(String interval) {
    	this.interval = interval;    
    }
    
    public String getAppLabel() {
    	return appLabel;    
    }
    
    public void setAppLabel(String appLabel) {
    	this.appLabel = appLabel;    
    }
    
    public String getEventName() {
       return eventName;   
    }
    
    public void setEventName(String eventName) {
    	this.eventName = eventName;    
    }
    
}