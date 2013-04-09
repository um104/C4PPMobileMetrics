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
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.util.Keys;

public class SessionLengthGraphRequest implements GraphRequest {

   private final static String REQUEST_TYPE = "SESSION_LENGTH";

   public SessionLengthGraphRequest() {

   }

   public String toString() {
      return "Session Length";
   }

   @Override
   public int getIconId() {
      return R.drawable.ic_launcher;
   }

   // @Override
   // public URI getUri(RestClientAccess restClientManager, Context context) {
   // URI uri = null;
   //
   // // get some of the basic information we'll need to make the URI
   // String instanceURL = restClientManager.getInstanceURL().toString();
   //
   // // Don't use getSharedPreferences(String, int) anymore.
   // // Instead, use PreferenceManager.getDefaultSharedPreferences(Context)
   // // String appLabel = getApplicationContext().getSharedPreferences(
   // // Keys.PREFS_NS, 0).getString(Keys.APP_LABEL, null);
   // String appLabel = PreferenceManager.getDefaultSharedPreferences(context)
   // .getString(Keys.APP_LABEL, null);
   //
   // // make the URI String
   // String uriString = "";
   //
   // // add in all non-parameterized information onto the request
   // uriString += String.format(SalesforceConn.CHANNEL4_REST_URL,
   // instanceURL, GraphRequestAsyncTask.GRAPH_REQUEST_URL_SUFFIX);
   //
   // // create a list for the URL parameters to add
   // List<NameValuePair> params = new ArrayList<NameValuePair>();
   //
   // params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, this.REQUEST_TYPE));
   // params.add(new BasicNameValuePair(Keys.APP_LABEL,
   // appLabel));
   //
   // // add the parameters to the uriString
   // String paramString = URLEncodedUtils.format(params, "utf-8");
   // uriString += "?" + paramString;
   //
   // // turn the string into a URI
   // try {
   // uri = new URI(uriString);
   // }
   // catch (URISyntaxException e) {
   // Log.e(e.getMessage());
   // }
   //
   // return uri;
   // }

   @Override
   public Intent constructGraphRequestIntent(Context context) {
      Intent intent = new Intent(context, GraphViewerActivity.class);
      intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, this);

      return intent;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel arg0, int arg1) {
      // No variables to write
   }

   public static final Parcelable.Creator<SessionLengthGraphRequest> CREATOR = new Parcelable.Creator<SessionLengthGraphRequest>() {
      public SessionLengthGraphRequest createFromParcel(Parcel in) {
         return new SessionLengthGraphRequest(in);
      }

      @Override
      public SessionLengthGraphRequest[] newArray(int size) {
         return new SessionLengthGraphRequest[size];
      }
   };

   private SessionLengthGraphRequest(Parcel in) {
      // no variables to set
   }

   @Override
   public List<NameValuePair> getAdditionalUriParameters() {
      // create a list for the URL parameters to add
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, REQUEST_TYPE));

      return params;
   }

}
