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
    * Use URLEncodedUtils.format(params, "utf-8") to generate this return string.
    * Put the String into Intent.
    * 
    * @return The string representation of the parameters held in this
    *         GraphRequest.
    */
   public String getUrlParameterString();

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

   public enum StartTime {
      LAST_24_HOURS("From the last 24 hours"), LAST_WEEK("From the last week"), LAST_MONTH(
               "From the last month");

      private String displayName;

      private StartTime(String displayName) {
         this.displayName = displayName;
      }

      @Override
      public String toString() {
         return displayName;
      }

      public static List<String> getStringArray() {
         List<String> startTimes = new ArrayList<String>(
                  GraphRequest.StartTime.values().length);
         for (GraphRequest.StartTime startTime : GraphRequest.StartTime
                  .values()) {
            startTimes.add(startTime.toString());
         }

         return startTimes;
      }
   }
}
