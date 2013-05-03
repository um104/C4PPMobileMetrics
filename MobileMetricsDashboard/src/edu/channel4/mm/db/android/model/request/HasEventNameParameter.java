package edu.channel4.mm.db.android.model.request;

import edu.channel4.mm.db.android.activity.EditGraphRequestActivity;

/**
 * This interface describes a GraphRequest that has a parameter that needs to be
 * set by the user before it can be sent. In this case, the parameter is an
 * Event Name. {@link EditGraphRequestActivity} will use reflection to detect
 * what parameters a certain GraphRequest has.
 * 
 * @author mlerner
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
