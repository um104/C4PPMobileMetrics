/**
 * GraphCreator
 * Capable of creating a javascript datatable based on information in the database
 * from a GraphRequest. 
 */
public abstract class GraphCreator{
    
    /**
     * An exception indicating that a graph type passed to getGraphCreator
     * is not currently supported
     */
    public class UnsupportedGraphException extends Exception {}
    
    /**
     * Returns a GraphCreator that can handle a request for a graph of type graphType.
     * This is typically a subclass of GraphCreator specific to the graphType requested.
     */
    public static GraphCreator getGraphCreator(GraphRequest.GraphType graphType)
    {    
        //no switch statements in Apex :'(
        if(graphType == GraphRequest.GraphType.PIE)
        {
            return new PieGraphCreator();
        }
        else if(graphType == GraphRequest.GraphType.LINE)
        {
        	return new LineGraphCreator();    
        }

        else
        {
            throw new UnsupportedGraphException();
        }   
    }
    
    /**
     * Creates a datatable representing the requested graph. This datatable can be used to construct
     * a new google visualization datatable, which in turn can be used to draw a graph.
     */
    public abstract Datatable createJSDataTable(GraphRequest requestedGraph);
    
}