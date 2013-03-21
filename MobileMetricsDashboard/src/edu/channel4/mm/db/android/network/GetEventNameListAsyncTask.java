package edu.channel4.mm.db.android.network;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import roboguice.RoboGuice;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.callback.EventNameDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.EventNameDescription;
import edu.channel4.mm.db.android.util.BaseAsyncTask;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

public class GetEventNameListAsyncTask extends
         BaseAsyncTask<Void, Void, List<EventNameDescription>> {

   private String responseString;
   private EventNameDescriptionCallback callback;

   @Inject private TempoDatabase tempoDatabase;
   @Inject private RestClientAccess restClientAccess;

   public GetEventNameListAsyncTask(Context context,
                                    EventNameDescriptionCallback callback) {
      super(context);
      this.callback = callback;

      // Inject the fields of this POJO
      RoboGuice.getInjector(context).injectMembers(this);
   }

   @Override
   protected List<EventNameDescription> doInBackground(Void... params) {
      Log.i("Sending GET request to get event list");

      String accessToken = restClientAccess.getAccessToken();
      String instanceUrl = restClientAccess.getInstanceURL().toString();

      // Don't use getSharedPreferences(String, int) anymore.
      // Instead, use PreferenceManager.getDefaultSharedPreferences(Context)
      // String appLabel = getApplicationContext().getSharedPreferences(
      // Keys.PREFS_NS, 0).getString(Keys.APP_LABEL, null);
      String appLabel = PreferenceManager.getDefaultSharedPreferences(
               getContext()).getString(Keys.APP_LABEL, null);

      if (accessToken == null) {
         Log.e("No access token currently saved");
         return null;
      }

      // Put together the HTTP Request to be sent to Salesforce for the
      // Event list
      String url = String.format(SalesforceConn.SALESFORCE_BASE_REST_URL,
               instanceUrl, SalesforceConn.EVENTS_URL_SUFFIX);

      appLabel = Uri.encode(appLabel);

      url += "?appLabel=" + appLabel;

      // add parameters
      HttpClient client = new DefaultHttpClient();
      HttpGet eventNameRequest = new HttpGet(url);
      eventNameRequest.setHeader("Authorization", "Bearer " + accessToken);

      // Add AppLabel parameter
      // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
      // nameValuePairs.add(new BasicNameValuePair("appLabel", appLabel));

      try {
         // eventNameRequest.setEntity(new
         // UrlEncodedFormEntity(nameValuePairs));
         // Get the response string, the Attribute List in JSON form
         responseString = EntityUtils.toString(client.execute(eventNameRequest)
                  .getEntity());
      }
      catch (ClientProtocolException e) {
         Log.e(e.getMessage());
      }
      catch (IOException e) {
         Log.e(e.getMessage());
      }

      Log.d("Got JSON result: " + responseString);

      if (responseString == null) {
         final String errorMessage = "ERROR: Attempted to parse null Event list.";
         Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
         Log.e(errorMessage);
         return null;
      }

      // Try to parse the resulting JSON
      List<EventNameDescription> eventNameList = null;
      try {
         eventNameList = EventNameDescription.parseList(responseString);
      }
      catch (JSONException e) {
         Log.e(e.getMessage());
      }

      return eventNameList;
   }

   @Override
   protected void onPostExecute(List<EventNameDescription> result) {
      super.onPostExecute(result);

      tempoDatabase.setEventNameDescriptions(result);
      callback.onEventNameDescriptionChanged(result);
   }
}
