package edu.channel4.mm.db.android.model.request;

/**
 * This interface describes a GraphRequest that has a parameter that needs to be
 * set by the user before it can be sent. In this case, the parameter is an
 * Event Name. {@link EditGraphRequestActivity} will use reflection to detect
 * what parameters a certain GraphRequest has.
 * 
 * @author mlerner
 */
public interface HasOverTimeParameter {

   /**
    * Set the duration parameter for this GraphRequest. The duration is set as a
    * string that represents time relative to 'now'. For instance, 'last week',
    * 'last month', and other time values.
    * 
    * @param time
    */
   public void setTimeInterval(String time);

   public String getTimeInterval();

}
