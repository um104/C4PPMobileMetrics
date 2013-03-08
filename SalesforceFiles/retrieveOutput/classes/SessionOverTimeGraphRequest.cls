public class SessionOverTimeGraphRequest extends  GraphRequest{
    private String appLabel;
    private String timeInterval;
    
    public SessionOverTimeGraphRequest(){
        
    }
    
    public override RequestType getRequestType(){
        return GraphRequest.RequestType.SESSION_OVER_TIME;
    }
    
    public void setAppLabel(String appLabel){
        this.appLabel = appLabel;
    }
    
    public void setTimeInterval(String timeInterval){
        this.timeInterval = timeInterval;
    }
    
    public String getAppLabel(){
        return this.appLabel;
    }
    
    public String getTimeInterval(){
        return this.timeInterval;
    }
}