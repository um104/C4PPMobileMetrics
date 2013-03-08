/**
 * GraphCreator
 * Capable of creating a javascript datatable based on information in the database
 * from a GraphRequest. 
 */
public abstract class GraphCreator{
    
    /**
     * Returns a GraphCreator that can handle a request for a graph of type graphType.
     * This is typically a subclass of GraphCreator specific to the graphType requested.
     */
    public static GraphCreator getGraphCreator(GraphRequest request)
    {   
        GraphCreator gc;
        
        //no switch statements in Apex :'(
        if (request.getRequestType() == GraphRequest.RequestType.CUSTOM)
        {
            gc = new CustomGraphCreator((CustomGraphRequest)request);
        }
        else if (request.getRequestType() == GraphRequest.RequestType.EVENT_OVER_TIME)
        {
        	gc = new EventOverTimeGraphCreator((EventOverTimeGraphRequest)request);
        }
        else if (request.getRequestType() == GraphRequest.RequestType.GEOCHART)
        {
            gc = new GeochartGraphCreator((GeochartGraphRequest)request);
        }
        else if (request.getRequestType() == GraphRequest.RequestType.SESSION_OVER_TIME)
        {
            gc = new SessionOverTimeGraphCreator((SessionOverTimeGraphRequest)request);
        }
        else if (request.getRequestType() == GraphRequest.RequestType.DEVICE_TYPE_DIST)
        {
            gc = new DeviceTypeDistGraphCreator((DeviceTypeDistGraphRequest)request);
        }
        else if (request.getRequestType() == GraphRequest.RequestType.LANGUAGE_LOCALE)
        {
        	gc = new LanguageLocaleGraphCreator((LanguageLocaleGraphRequest) request);
        }
        else if (request.getRequestType() == GraphRequest.RequestType.NET_CARRIER)
        {
        	gc = new NetworkCarrierGraphCreator((NetworkCarrierGraphRequest) request);    
        }
        
        return gc;
        
    }
    
    /**
     * Creates a datatable representing the requested graph. This datatable can be used to construct
     * a new google visualization datatable, which in turn can be used to draw a graph.
     */
    public abstract Datatable createJSDataTable();
    
}