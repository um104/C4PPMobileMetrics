/**
 * GraphRequestHandler
 * An Apex REST resource that handles graph requests
 */
@RestResource(urlMapping='/channel4_handleGraphRequest/*')

global with sharing class GraphRequestHandler{
    
    /**
     * returns a js Datatable representing the requested graph
     */
    @HttpPost
        @RemoteAction
        global static String handleRequest(jGraphRequest jRequest){
          
            GraphRequest request = GraphRequest.fromJGraphRequest(jRequest);
            /*request.setGraphType(GraphRequest.GraphType.PIE);
            request.setFirstAttrib('key02');
            request.setFirstAttribEvent('n01');
            request.setAppName('an01');*/
            
            
            GraphCreator creator = GraphCreator.getGraphCreator(request.getGraphType());
            Datatable table = creator.createJSDatatable(request);
            System.debug(JSON.serialize(table));
            return JSON.serialize(table);
        }
    
    global class jGraphRequest {
    
        /* the type of graph requested */
        public String graphType;
    
        /* the name of the first attribute (either an event attribute or session attribute)
           to be considered in this graph */
        public String firstAttrib;
    
        /* the name of the event that firstAttrib belongs to (or null if firstAttrib is a
           session attribute */
        public String firstAttribEvent;
    
        /* the name of the app that this request is requesting a graph for */
        public String appName;
    }
}