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

   private final static String REQUEST_TYPE = "EVENT_OVER_TIME";
   private String timeInterval;
   private String eventName;

   public EventOverTimeGraphRequest() {
   }

   public String toString() {
      return "Event over Time";
   }

   @Override
   public int getIconId() {
      return R.drawable.sessions_over_time;
   }

//   @Override
//   public URI getUri(RestClientAccess restClientManager, Context context) {
//      URI uri = null;
//
//      // get some of the basic information we'll need to make the URI
//      String instanceURL = restClientManager.getInstanceURL().toString();
//      // Don't use getSharedPreferences(String, int) anymore.
//      // Instead, use PreferenceManager.getDefaultSharedPreferences(Context)
//      // String appLabel = getApplicationContext().getSharedPreferences(
//      // Keys.PREFS_NS, 0).getString(Keys.APP_LABEL, null);
//      String appLabel = PreferenceManager.getDefaultSharedPreferences(context)
//               .getString(Keys.APP_LABEL, null);
//
//      // make the URI String
//      String uriString = "";
//
//      // add in all non-parameterized information onto the request
//      uriString += String.format(SalesforceConn.CHANNEL4_REST_URL,
//               instanceURL, GraphRequestAsyncTask.GRAPH_REQUEST_URL_SUFFIX);
//
//      // create a list for the URL parameters to add
//      List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, this.REQUEST_TYPE));
//      params.add(new BasicNameValuePair(Keys.APP_LABEL,
//               appLabel));
//      params.add(new BasicNameValuePair(Keys.TIME_INTERVAL, timeInterval));
//      params.add(new BasicNameValuePair(Keys.EVENT_NAME, eventName));
//
//      // add the parameters to the uriString
//      String paramString = URLEncodedUtils.format(params, "utf-8");
//      uriString += "?" + paramString;
//
//      // turn the string into a URI
//      try {
//         uri = new URI(uriString);
//      }
//      catch (URISyntaxException e) {
//         Log.e(e.getMessage());
//      }
//
//      return uri;
//   }

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
   public void setTimeInterval(String timeInterval) {
      this.timeInterval = timeInterval;
   }

   @Override
   public String getTimeInterval() {
      return timeInterval;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      // Note: Parcel data is read in a FIFO manner
      dest.writeString(timeInterval);
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
      this.timeInterval = in.readString();
      this.eventName = in.readString();
   }

   @Override
   public List<NameValuePair> getAdditionalUriParameters() {
      // Create a list for the URL parameters to add
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, REQUEST_TYPE));
      params.add(new BasicNameValuePair(Keys.TIME_INTERVAL, timeInterval));
      params.add(new BasicNameValuePair(Keys.EVENT_NAME, eventName));
      
      return params;
   }

}
