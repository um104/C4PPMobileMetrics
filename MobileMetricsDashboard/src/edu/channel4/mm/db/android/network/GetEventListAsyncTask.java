package edu.channel4.mm.db.android.network;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import edu.channel4.mm.db.android.callback.EventDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.util.BaseAsyncTask;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

public class GetEventListAsyncTask extends
         BaseAsyncTask<Void, Void, List<EventDescription>> {
   
   private final static String ATTRIBS_URL_SUFFIX = "channel4_attributes/";
   private String responseString;
   private EventDescriptionCallback callback;

   public GetEventListAsyncTask(Context context, HttpClient client, EventDescriptionCallback callback) {
      super(context, client);
      this.callback = callback;
   }

   @Override
   protected List<EventDescription> doInBackground(Void... params) {
      Log.i("Sending GET request to get event list");

      String accessToken = getContext().getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.ACCESS_TOKEN, null);
      String instanceUrl = getContext().getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.INSTANCE_URL, null);

      if (accessToken == null) {
         Log.e("No access token currently saved");
         return null;
      }

      // Put together the HTTP Request to be sent to Salesforce for the
      // Event list
      String appLabel = getContext().getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.APP_LABEL, null);
      
      appLabel = Uri.encode(appLabel);
      
      HttpGet eventRequest = new HttpGet(String.format(
               SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl, ATTRIBS_URL_SUFFIX)
                        + "?appLabel=" + appLabel);
      
      eventRequest.setHeader("Authorization", "Bearer " + accessToken);

      // Add AppLabel parameter
      //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
      //nameValuePairs.add(new BasicNameValuePair("appLabel", appLabel));

      try {
         //eventRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         // Get the response string, the Attribute List in JSON form
         responseString = EntityUtils.toString(getClient().execute(eventRequest)
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
      List<EventDescription> eventList = null;
      try {
         eventList = EventDescription.parseList(responseString);

      }
      catch (JSONException e) {
         Log.e(e.getMessage());
      }
      return eventList;
   }

   @Override
   protected void onPostExecute(List<EventDescription> result) {
      super.onPostExecute(result);
      TempoDatabase tempoDatabase = TempoDatabase.getInstance();
      tempoDatabase.setEventDescriptions(result);
      
      callback.onEventDescriptionChanged(result);
   }
}
