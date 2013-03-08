/* GraphRequest
 * A data structure which describes a graph request
 * @author Brian Middaugh
 */
public abstract class GraphRequest {
    
    /**
     * GraphType contains an enum
     * for each currently supported graph type
     */
    public enum RequestType {CUSTOM, EVENT_OVER_TIME, GEOCHART, SESSION_OVER_TIME, DEVICE_TYPE_DIST, LANGUAGE_LOCALE, NET_CARRIER}
    
    /**
     * returns the type of graph requested 
     */
    public abstract RequestType getRequestType();
    
    public static GraphRequest fromJSON(String requestType, String requestAsJSON){
        GraphRequest theRequest = null;
        
        if (requestType == 'CUSTOM') {
            theRequest = (CustomGraphRequest) JSON.deserializeStrict(requestAsJSON, CustomGraphRequest.class);
        } else if (requestType == 'EVENT_OVER_TIME') {
            theRequest = (EventOverTimeGraphRequest) JSON.deserializeStrict(requestAsJSON, EventOverTimeGraphRequest.class);
        } else if (requestType == 'GEOCHART') {
            theRequest = (GeochartGraphRequest) JSON.deserializeStrict(requestAsJSON, GeochartGraphRequest.class);
        } else if (requestType == 'SESSION_OVER_TIME') {
            theRequest = (SessionOverTimeGraphRequest) JSON.deserializeStrict(requestAsJSON, SessionOverTimeGraphRequest.class);
        } else if (requestType == 'DEVICE_TYPE_DIST') {
            theRequest = (DeviceTypeDistGraphRequest) JSON.deserializeStrict(requestAsJSON, DeviceTypeDistGraphRequest.class);
        } else if (requestType == 'LANGUAGE_LOCALE') {
        	theRequest = (LanguageLocaleGraphRequest) JSON.deserializeStrict(requestAsJSON, LanguageLocaleGraphRequest.class);
        }else if (requestType == 'NET_CARRIER') {
         	theRequest = (NetworkCarrierGraphRequest) JSON.deserializeStrict(requestAsJSON, NetworkCarrierGraphRequest.class);   
        }

        return theRequest;
    }
}