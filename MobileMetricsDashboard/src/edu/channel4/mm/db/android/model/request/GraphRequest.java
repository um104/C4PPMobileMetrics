package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import edu.channel4.mm.db.android.activity.EventPickerActivity;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;

public interface GraphRequest {

   /**
    * Must implement toString() for display purposes.
    * 
    * @return The display name of the GraphRequest.
    */
   @Override
   public String toString();

   /**
    * Read-only getter for the REST request type that the APEX server expects.
    * 
    * @return
    */
   public String getRestRequestType();

   /**
    * Make a List<NameValuePair>, store BasicNameValuePair objects with
    * key-param pairs, and then use URLEncodedUtils.format(params, "utf-8") to
    * generate this return string. Put the String into Intent as a String extra
    * under Keys.REQUEST_URL_PARAMETERS
    * 
    * @param context
    *           The application context, used to gather some parameters stored
    *           in shared preferences
    * @return The string representation of the parameters held in this
    *         GraphRequest.
    */
   public String getUrlParameterString(Context context);

   /**
    * Constructs the correct Intent for a given GraphRequest. The Intent will
    * open the correct next Activity (e.g. Nationality Breakdown goes straight
    * to {@link GraphViewerActivity} whereas Sessions over Time requires you to
    * choose the duration in {@link EditGraphRequestActivity}.
    * 
    * If the Intent is directed towards the {@link GraphViewerActivity}, then
    * the UrlParameterString should be included as a String extra within the
    * Intent. If the Intent is directed elsewhere, such as
    * {@link EditGraphRequestActivity}, then the GraphRequest itself must
    * implement Parcelable and be included in the Intent as an extra.
    * 
    * @param context
    * @param graphRequest
    * @return
    */
   public Intent constructGraphRequestIntent(Context context);
   
   public int getIconId();

   /**
    * Enum for any events that implement HasOverTimeParameter.
    * 
    * @author mlerner
    */
   public enum TimeInterval {
      DAY("From the last day"), WEEK("From the last week"), MONTH(
               "From the last month"), YEAR("From the last year");

      private String displayName;

      private TimeInterval(String displayName) {
         this.displayName = displayName;
      }

      @Override
      public String toString() {
         return displayName;
      }

      public static List<String> getStringArray() {
         List<String> startTimes = new ArrayList<String>(
                  GraphRequest.TimeInterval.values().length);
         for (GraphRequest.TimeInterval startTime : GraphRequest.TimeInterval
                  .values()) {
            startTimes.add(startTime.toString());
         }

         return startTimes;
      }
   }
}
