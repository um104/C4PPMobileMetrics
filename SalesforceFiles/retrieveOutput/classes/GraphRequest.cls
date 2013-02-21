/* GraphRequest
 * A data structure which describes a graph request
 * @author Brian Middaugh
 */
global class GraphRequest {
    
    /**
     * GraphType contains an enum
     * for each currently supported graph type
     */
    public enum GraphType {PIE, LINE}
    
    /* the type of graph requested */
    private GraphType graphType;
    
    /* the name of the first attribute (either an event attribute or session attribute)
       to be considered in this graph */
    private String firstAttrib;
    
    /* the name of the event that firstAttrib belongs to (or null if firstAttrib is a
       session attribute */
    private String firstAttribEvent;
    
    /* the name of the app that this request is requesting a graph for */
    private String appName;
    
    public static GraphRequest fromJGraphRequest(GraphRequestHandler.jGraphRequest jRequest){
        System.debug('jRequest firstAttrib: ' + jRequest.firstAttrib);
        System.debug('jRequest firstAttribEvent: ' + jRequest.firstAttribEvent);
        System.debug('jRequest appName: ' + jRequest.appName);
        GraphRequest request = new GraphRequest();
        if(jRequest.graphType == 'PIE')
        {
            request.setGraphType(GraphRequest.GraphType.PIE);
        }
        else if(jRequest.graphType == 'LINE')
        {
        	request.setGraphType(GraphRequest.GraphType.LINE);    
        }
        request.setFirstAttrib(jRequest.firstAttrib);
        request.setFirstAttribEvent(jRequest.firstAttribEvent);
        request.setAppName(jRequest.appName);
        
        return request;
    }
    /**
     * returns the type of graph requested 
     */
    public GraphType getGraphType(){
        return this.graphType;
    }
    
    public void setGraphType(GraphType type){
        this.graphType = type;
    }
    
    /**
     * returns the first attribute to be considered for this graph
     */
    public String getFirstAttrib(){
        return this.firstAttrib;
    }
    
    public void setFirstAttrib(String name){
        this.firstAttrib = name;
    }
    
    /**
     * returns the event that the first attribute belongs to
     */
    public String getFirstAttribEvent(){
        return this.firstAttribEvent;
    }
    
    public void setFirstAttribEvent(String name){
        this.firstAttribEvent = name;
    }
    
    /**
     * returns the name of the app that this request is for
     */
    public String getAppName(){
        return this.appName;
    }
    
    public void setAppName(String name){
        this.appName = name;
    }
}