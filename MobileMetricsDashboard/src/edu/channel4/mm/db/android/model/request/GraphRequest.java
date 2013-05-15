package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import edu.channel4.mm.db.android.activity.EditGraphRequestActivity;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;

public abstract class GraphRequest implements Parcelable {
   
   protected TimeScope timeScope = TimeScope.DAY;
   protected boolean rangedTime = false;
   protected int timeRangeStart;
   protected int timeRangeStop;

   /**
    * Pretty print.
    */
   @Override
   public abstract String toString();

   /**
    * @return Android resource id for the icon
    */
   public abstract int getIconId();
   
   public abstract List<NameValuePair> getAdditionalUriParameters();

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
   public abstract Intent constructGraphRequestIntent(Context context);

   /**
    * Enum for any events that implement HasOverTimeParameter.
    * 
    * @author mlerner
    */
   public enum TimeScope {
      DAY("Since Midnight"),
      /*WEEK("Since Sunday"),*/
      MONTH("Since 1st of Month"),
      YEAR("Since Jan 1st"),
      DECADE("Since Jan 1st, 2013");

      private String displayName;

      private TimeScope(String displayName) {
         this.displayName = displayName;
      }

      @Override
      public String toString() {
         return displayName;
      }

      public static List<String> getStringArray() {
         List<String> startTimes = new ArrayList<String>(
                  GraphRequest.TimeScope.values().length);
         for (GraphRequest.TimeScope startTime : GraphRequest.TimeScope
                  .values()) {
            startTimes.add(startTime.toString());
         }

         return startTimes;
      }
   }
   
   /**
    * Set this to true if the graph request is using a ranged time interval
    * as opposed to just querying the entire timeScope table
    * @param rangedTime
    */
   public void setRangedTime(boolean rangedTime) {
      this.rangedTime = rangedTime;
   }
   
   public void setTimeRangeStart(int rangeStart) {
      this.timeRangeStart = rangeStart;
   }

   public void setTimeRangeStop(int rangeStop) {
      this.timeRangeStop = rangeStop;
   }

   public int getTimeRangeStart() {
      return timeRangeStart;
   }

   public int getTimeRangeStop() {
      return timeRangeStop;
   }
   
   public void setTimeScope(TimeScope scope) {
      this.timeScope = scope;
   }
   
   public TimeScope getTimeScope() {
      return this.timeScope;
   }

}
