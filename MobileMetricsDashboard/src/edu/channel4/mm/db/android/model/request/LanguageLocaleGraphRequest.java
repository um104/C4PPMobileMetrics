package edu.channel4.mm.db.android.model.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.network.GraphRequestAsyncTask;
import edu.channel4.mm.db.android.network.RestClientAccess;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

// TODO: Write correct Parcelable methods.
public class LanguageLocaleGraphRequest implements GraphRequest {
   
   private final static String REQUEST_TYPE = "CUSTOM";
   private final static String EVENT1 = ""; // empty b/c session attr
   private final static String ATTRIBUTE1 = "LanguageLocal__c";

   public LanguageLocaleGraphRequest() {
   }

   @Override
   public String toString() {
      return "Language Locale";
   }

   /**
    * TODO: Maybe pass the app label as a String into this method? First we
    * should figure out where (in code) the app label should actually be
    * retireved from SharedPrefs.
    */
   @Override
   public URI getUri(RestClientAccess restClientManager, Context context) {
      URI uri = null;
      
      // get some of the basic information we'll need to make the URI
      String instanceURL = restClientManager.getInstanceURL().toString();
      String appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.APP_LABEL, null);

      // make the URI String
      String uriString = "";

      // add in all non-parameterized information onto the request
      uriString += String.format(SalesforceConn.SALESFORCE_BASE_REST_URL,
               instanceURL, GraphRequestAsyncTask.GRAPH_REQUEST_URL_SUFFIX);

      // create a list for the URL parameters to add
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, this.REQUEST_TYPE));
      params.add(new BasicNameValuePair(Keys.APP_LABEL_URL_PARAMETER_NAME,
               appLabel));
      params.add(new BasicNameValuePair(Keys.ATTRIB_NAME_1, this.ATTRIBUTE1));
      params.add(new BasicNameValuePair(Keys.EVENT_NAME_1, this.EVENT1));

      // add the parameters to the uriString
      String paramString = URLEncodedUtils.format(params, "utf-8");
      uriString += "?" + paramString;

      // turn the string into a URI
      try {
         uri = new URI(uriString);
      }
      catch (URISyntaxException e) {
         Log.e(e.getMessage());
      }

      return uri;
      
      
      
/*
      // TODO: Un-hack this hardcoded code.
      String instanceURL = restClientManager.getInstanceURL().toString();
      String appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.APP_LABEL, null);

      String uriString = String.format(SalesforceConn.SALESFORCE_BASE_REST_URL,
               instanceURL, GraphRequestAsyncTask.GRAPH_REQUEST_URL_SUFFIX);
      uriString += "?requestAsJSON=";
      JSONObject requestAsJSONObject = new JSONObject();
      try {
         requestAsJSONObject.put(Keys.APP_LABEL_URL_PARAMETER_NAME, appLabel);
      }
      catch (JSONException e) {
         Log.e(e.getMessage());
      }
      uriString += Uri.encode(requestAsJSONObject.toString());

      uriString += "&requestType=LANGUAGE_LOCALE";

      URI uri = null;
      try {
         uri = new URI(uriString);
      }
      catch (URISyntaxException e) {
         Log.e(e.getMessage());
      }
      return uri;
      */

   }

   @Override
   public Intent constructGraphRequestIntent(Context context) {
      Intent intent = new Intent(context, GraphViewerActivity.class);
      intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, this);

      return intent;
   }

   @Override
   public int getIconId() {
      return R.drawable.language_locale;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      // no fields to write
   }

   private LanguageLocaleGraphRequest(Parcel in) {
      // no fields to set
   }

   public static final Parcelable.Creator<LanguageLocaleGraphRequest> CREATOR = new Parcelable.Creator<LanguageLocaleGraphRequest>(){
      public LanguageLocaleGraphRequest createFromParcel(Parcel in) {
         return new LanguageLocaleGraphRequest(in);
      }

      @Override
      public LanguageLocaleGraphRequest[] newArray(int size) {
         return new LanguageLocaleGraphRequest[size];
      }
   };


}
