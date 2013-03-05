/**
 * GraphRequestHandler
 * An Apex REST resource that handles graph requests
 */
@RestResource(urlMapping='/channel4_handleGraphRequest/*')

global with sharing class GraphRequestHandler{
    
    global class UnsupportedGraphTypeException extends Exception{}
    
    /**
     * returns a js Datatable representing the requested graph
     */
    @HttpPost
    @RemoteAction
    global static String handleRequest(String requestType, String requestAsJSON){
       String jsonDatatable;
       try{
           jsonDatatable = JSON.serialize(getDatatable(requestType, requestAsJSON));
       }
       catch(Exception ex){
           return 'Failure ' + ex.getMessage();
       }
       return jsonDatatable;
    }
            
    global static Datatable getDatatable(String requestType, String requestAsJSON){
        GraphRequest request;
        Datatable table;
 
        request = GraphRequest.fromJSON(requestType, requestAsJSON);

        if (request == null){
           throw new UnsupportedGraphTypeException('Graph type not supported');
        }
           
        GraphCreator creator = GraphCreator.getGraphCreator(request);
        return creator.createJSDatatable();
    }
}