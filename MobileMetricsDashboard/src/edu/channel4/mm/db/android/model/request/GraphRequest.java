package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import edu.channel4.mm.db.android.activity.EventPickerActivity;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;

public interface GraphRequest {

   @Override
   public String toString();

   /**
    * Read-only getter for the REST request type that the server expects.
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
    * open the correct next Activity (e.g. Number of Sessions over Time goes
    * straight to {@link GraphViewerActivity} whereas Events over Time requires
    * you to choose the Event first in {@link EventPickerActivity}.
    * 
    * The Intent will add fields to the GraphRequest as it is passed along the
    * Activities.
    * 
    * @param context
    * @param graphRequest
    * @return
    */
   public Intent constructGraphRequestIntent(Context context);

   public enum TimeInterval {
      DAY("From the last day"), 
      WEEK("From the last week"), 
      MONTH("From the last month"),
      YEAR("From the last year");

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
