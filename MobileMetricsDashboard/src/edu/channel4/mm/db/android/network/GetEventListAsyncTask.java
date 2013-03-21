package edu.channel4.mm.db.android.network;

import java.util.HashMap;
import java.util.List;

import roboguice.RoboGuice;
import android.content.Context;
import android.net.Uri;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.callback.EventDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

public class GetEventListAsyncTask extends
         BaseGetRequestAsyncTask<List<EventDescription>> {
   // BaseAsyncTask<Void, Void, List<EventDescription>> {

   private final static String ATTRIBS_URL_SUFFIX = "channel4_attributes/";
   private EventDescriptionCallback callback;

   @Inject private TempoDatabase tempoDatabase;
   @Inject private RestClientAccess restClientAccess;

   @SuppressWarnings("serial")
   public GetEventListAsyncTask(Context context, final String appLabel,
                                EventDescriptionCallback callback) {
      super(context, ATTRIBS_URL_SUFFIX, new HashMap<String, String>() {
         {
            put(Keys.APP_LABEL_URL_PARAMETER_NAME, Uri.encode(appLabel));
         }
      });
      this.callback = callback;

      // Inject the fields of this POJO
      RoboGuice.getInjector(context).injectMembers(this);
   }

   // @Override
   // protected List<EventDescription> doInBackground(Void... params) {
   // Log.i("Sending GET request to get event list");
   //
   // String accessToken = restClientAccess.getAccessToken();
   // String instanceUrl = restClientAccess.getInstanceURL().toString();
   //
   // if (accessToken == null) {
   // Log.e("No access token currently saved");
   // return null;
   // }
   //
   // // Put together the HTTP Request to be sent to Salesforce for the
   // // Event list
   //
   // // Don't use getSharedPreferences(String, int) anymore.
   // // Instead, use PreferenceManager.getDefaultSharedPreferences(Context)
   // // String appLabel = getApplicationContext().getSharedPreferences(
   // // Keys.PREFS_NS, 0).getString(Keys.APP_LABEL, null);
   // String appLabel = PreferenceManager.getDefaultSharedPreferences(
   // getContext()).getString(Keys.APP_LABEL, null);
   //
   // appLabel = Uri.encode(appLabel);
   //
   // HttpClient client = new DefaultHttpClient();
   // HttpGet eventRequest = new HttpGet(String.format(
   // SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl,
   // ATTRIBS_URL_SUFFIX)
   // + "?appLabel=" + appLabel);
   //
   // eventRequest.setHeader("Authorization", "Bearer " + accessToken);
   //
   // // Add AppLabel parameter
   // // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
   // // nameValuePairs.add(new BasicNameValuePair("appLabel", appLabel));
   //
   // try {
   // // eventRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   // // Get the response string, the Attribute List in JSON form
   // responseString = EntityUtils.toString(client.execute(eventRequest)
   // .getEntity());
   // }
   // catch (ClientProtocolException e) {
   // Log.e(e.getMessage());
   // }
   // catch (IOException e) {
   // Log.e(e.getMessage());
   // }
   //
   // Log.d("Got JSON result: " + responseString);
   //
   // if (responseString == null) {
   // final String errorMessage = "ERROR: Attempted to parse null Event list.";
   // // TODO: Removed for testing
   // // Toast.makeText(getContext(), errorMessage,
   // // Toast.LENGTH_SHORT).show();
   // Log.e(errorMessage);
   // return null;
   // }
   //
   // // Try to parse the resulting JSON
   // List<EventDescription> eventList = null;
   // try {
   // eventList = EventDescription.parseList(responseString);
   //
   // }
   // catch (JSONException e) {
   // Log.e(e.getMessage());
   // }
   // return eventList;
   // }

   // @Override
   // protected void onPostExecute(List<EventDescription> result) {
   // super.onPostExecute(result);
   // tempoDatabase.setEventDescriptions(result);
   //
   // callback.onEventDescriptionChanged(result);
   // }

   @Override
   public List<EventDescription> call() throws Exception {

      // Execute the GET request
      super.call();

      // Attempt to parse the resulting string
      List<EventDescription> eventList = EventDescription
               .parseList(responseString);

      return eventList;
   }

   @Override
   protected void onSuccess(List<EventDescription> result) {
      tempoDatabase.setEventDescriptions(result);

      callback.onEventDescriptionChanged(result);
   }

   @Override
   protected void onException(Exception e) {
      Log.toastE(context, e.getMessage());
   }
}
