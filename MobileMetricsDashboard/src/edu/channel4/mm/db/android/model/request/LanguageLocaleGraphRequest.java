package edu.channel4.mm.db.android.model.request;

import java.net.URI;
import java.net.URISyntaxException;

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

   }

   @Override
   public Intent constructGraphRequestIntent(Context context) {
      Intent intent = new Intent(context, GraphViewerActivity.class);
      intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, this);

      return intent;
   }

   @Override
   public int getIcon() {
      return R.drawable.language_locale;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
   }

   private LanguageLocaleGraphRequest(Parcel in) {

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
