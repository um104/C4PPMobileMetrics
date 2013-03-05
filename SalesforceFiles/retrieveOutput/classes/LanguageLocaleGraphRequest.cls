public class LanguageLocaleGraphRequest extends GraphRequest {
	
	private String appLabel;
	
	public override GraphRequest.RequestType getRequestType() {
        return GraphRequest.RequestType.LANGUAGE_LOCALE;
    }
    
    public LanguageLocaleGraphRequest() {}
    
    public void setAppLabel(String appLabel) 
    {
        this.appLabel = appLabel;
    }
    
    public String getAppLabel() 
    {
        return appLabel;
    }
}