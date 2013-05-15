package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.EditGraphRequestActivity;
import edu.channel4.mm.db.android.util.Keys;

public class EventOverTimeGraphRequest implements GraphRequest,
         HasEventNameParameter, HasOverTimeParameter {

   // DO NOT CHANGE THIS STRING. APEX code relies on it!
   private final static String REQUEST_TYPE = "Line";
   private TimeScope timeScope = TimeScope.DAY;
   private String eventName;
   private int timeRangeStart;
   private int timeRangeStop;

   public EventOverTimeGraphRequest() {
   }

   public String toString() {
      return "Event over Time";
   }

   @Override
   public int getIconId() {
      return R.drawable.sessions_over_time;
   }

   @Override
   public Intent constructGraphRequestIntent(Context context) {
      Intent intent = new Intent(context, EditGraphRequestActivity.class);
      intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, this);

      return intent;
   }

   @Override
   public void setEventName(String eventName) {
      this.eventName = eventName;
   }

   @Override
   public String getEventName() {
      return eventName;
   }

   @Override
   public void setTimeScope(TimeScope timeScope) {
      this.timeScope = timeScope;
   }

   @Override
   public TimeScope getTimeScope() {
      return timeScope;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      // Note: Parcel data is read in a FIFO manner
      dest.writeString(timeScope.name());
      dest.writeString(eventName);
   }

   public static final Parcelable.Creator<EventOverTimeGraphRequest> CREATOR = new Parcelable.Creator<EventOverTimeGraphRequest>() {
      public EventOverTimeGraphRequest createFromParcel(Parcel in) {
         return new EventOverTimeGraphRequest(in);
      }

      @Override
      public EventOverTimeGraphRequest[] newArray(int size) {
         return new EventOverTimeGraphRequest[size];
      }
   };

   private EventOverTimeGraphRequest(Parcel in) {
      // Note: Parcel data is read in a fifo manner
      this.timeScope = TimeScope.valueOf(in.readString());
      this.eventName = in.readString();
   }

   @Override
   public List<NameValuePair> getAdditionalUriParameters() {
      // Create a list for the URL parameters to add
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, REQUEST_TYPE));
      params.add(new BasicNameValuePair(Keys.TIME_SCOPE, timeScope.name()));
      params.add(new BasicNameValuePair(Keys.EVENT_NAME, eventName));
      
      return params;
   }

   @Override
   public void setTimeRangeStart(int rangeStart) {
      this.timeRangeStart = rangeStart;
   }

   @Override
   public void setTimeRangeStop(int rangeStop) {
      this.timeRangeStop = rangeStop;
   }

   @Override
   public int getTimeRangeStart() {
      return timeRangeStart;
   }

   @Override
   public int getTimeRangeEnd() {
      return timeRangeStop;
   }

}
