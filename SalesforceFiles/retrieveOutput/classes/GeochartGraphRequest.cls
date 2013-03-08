public class GeochartGraphRequest extends GraphRequest {
    /** I don't think these instance variables are ever used,
      * so we should remove them to make the url simpler */
    //private String country;
    //private Integer popularity;
    private String appLabel;
    
    public override GraphRequest.RequestType getRequestType() {
        return GraphRequest.RequestType.GEOCHART;
    }
    
    public GeochartGraphRequest() {
    }
    
    public void setAppLabel(String appLabel){
        this.appLabel = appLabel;
    }
    
    public String getAppLabel(){
        return this.appLabel;
    }
    
    /*
    public void setCountry(String country) {
        this.country = country;
    }
    
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public Integer getPopularity() {
        return this.popularity;
    }
*/
}