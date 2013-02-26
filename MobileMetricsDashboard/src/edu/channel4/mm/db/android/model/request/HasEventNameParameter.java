package edu.channel4.mm.db.android.model.request;

/**
 * Interface for GraphRequests to implement. A concrete GraphRequest class
 * should implement this when it has a variable parameter for EventNames.
 * 
 * @author mlerner
 * 
 */
public interface HasEventNameParameter {

   /**
    * Set the name of the Event that this GraphRequest is focusing on. Event
    * names are unique, so this should be enough to determine what event is
    * being requested.
    * 
    * @param eventName
    */
   public void setEventName(String eventName);

   public String getEventName();

}
