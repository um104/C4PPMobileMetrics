package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.EditGraphRequestActivity;
import edu.channel4.mm.db.android.util.Keys;

public class SessionOverTimeGraphRequest implements GraphRequest, HasOverTimeParameter, Parcelable {
   
   private String appLabel;
   private String timeInterval;
   
   public SessionOverTimeGraphRequest() {
      super();
   }
   
   public String toString() {
      return "Sessions over Time";
   }

   @Override
   public String getRestRequestType() {
      return "SESSION_OVER_TIME";
   }

   @Override
   public String getUrlParameterString(Context context) {

      appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.APP_LABEL, null);
      
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      //Should be "custom" "SessionOverTime", "EventOverTime", etc.
      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, getRestRequestType()));
      params.add(new BasicNameValuePair(Keys.APP_LABEL_URL_PARAMETER_NAME, appLabel));
      params.add(new BasicNameValuePair(Keys.TIME_INTERVAL, timeInterval));

      String paramString = URLEncodedUtils.format(params, "utf-8");

      return paramString;
   }

   @Override
   public Intent constructGraphRequestIntent(Context context) {
      Intent intent = new Intent(context, EditGraphRequestActivity.class);
      
      intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, this);

      return intent;
   }

   @Override
   public void setTimeDuration(String time) {
      this.timeInterval = time;
   }

   @Override
   public String getTimeDuration() {
      return timeInterval;
   }
   
   /* Everything from here down is for implementing the Parcelable interface */
   @Override
   public int describeContents() {
      // Auto-generated method stub
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      // Note: Parcel data is read in a FIFO manner.
      dest.writeString(appLabel);
      dest.writeString(timeInterval);
   }

   public static final Parcelable.Creator<SessionOverTimeGraphRequest> CREATOR = new Parcelable.Creator<SessionOverTimeGraphRequest>() {
      public SessionOverTimeGraphRequest createFromParcel(Parcel in) {
         return new SessionOverTimeGraphRequest(in);
      }

      @Override
      public SessionOverTimeGraphRequest[] newArray(int size) {
         return new SessionOverTimeGraphRequest[size];
      }
   };

   private SessionOverTimeGraphRequest(Parcel in) {
      // Note: Parcel data is read in a FIFO manner.
      this.appLabel = in.readString();
      this.timeInterval = in.readString();
   }

   @Override
   public int getIconId() {
      return R.drawable.sessions_over_time;
   }

}
