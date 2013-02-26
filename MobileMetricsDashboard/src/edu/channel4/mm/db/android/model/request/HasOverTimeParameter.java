package edu.channel4.mm.db.android.model.request;

/**
 * Interface for GraphRequests to implement. A concrete GraphRequest class
 * should implement this when it has a variable parameter for time duration.
 * 
 * @author mlerner
 * 
 */
public interface HasOverTimeParameter {

   /**
    * Set the duration parameter for this GraphRequest. The duration is set as a
    * string that represents time relative to 'now'. For instance, 'last week',
    * 'last month', and other time values.
    * 
    * @param time
    */
   public void setTimeDuration(String time);

   public String getTimeDuration();

}
