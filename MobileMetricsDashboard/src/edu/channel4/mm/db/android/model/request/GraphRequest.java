package edu.channel4.mm.db.android.model.request;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import edu.channel4.mm.db.android.activity.EditGraphRequestActivity;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.network.RestClientAccess;

public interface GraphRequest extends Parcelable {

   /**
    * Pretty print.
    */
   @Override
   public String toString();

   /**
    * @return Android resource id for the icon
    */
   public int getIconId();

   /**
    * Consolidate GraphRequest URI logic into a single method. Sanity-check it
    * by putting it through {@link URI} for validity.
    * 
    * @param context
    * @return
    */
   public URI getUri(RestClientAccess restClientManager, Context context);

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
