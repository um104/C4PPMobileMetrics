package edu.channel4.mm.db.android.network;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.widget.Toast;
import edu.channel4.mm.db.android.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.util.BaseAsyncTask;
import edu.channel4.mm.db.android.util.Log;

class GetAppListAsyncTask extends
         BaseAsyncTask<Void, Void, List<AppDescription>> {
   protected static final String APPS_URL_SUFFIX = "channel4_apps";
   private String responseString = null;
   private AppDescriptionCallback callback;

   public GetAppListAsyncTask(Context context, AppDescriptionCallback callback) {
      super(context);
      this.callback = callback;
   }

   @Override
   protected List<AppDescription> doInBackground(Void... params) {
      Log.i("Sending GET request to get app list");

      RestClientAccess restClientAccess = RestClientAccess.getInstance();

      String accessToken = restClientAccess.getAccessToken();
      String instanceUrl = restClientAccess.getInstanceURL().toString();

      if (accessToken == null) {
         Log.e("No access token currently saved");
         return null;
      }

      Log.d("Access token: " + accessToken);
      Log.d("Instance URL: " + instanceUrl);

      HttpClient client = new DefaultHttpClient();
      HttpUriRequest getRequest = new HttpGet(String.format(
               SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl,
               APPS_URL_SUFFIX));
      getRequest.setHeader("Authorization", "Bearer " + accessToken);

      try {
         responseString = EntityUtils.toString(client.execute(getRequest)
                  .getEntity());
      }
      catch (Exception e) {
         Log.e(e.getMessage());
      }

      Log.d("Got JSON result: " + responseString);

      if (responseString == null) {
         final String errorMessage = "ERROR: Attempted to parse null App list.";
         // TODO: commented out for testing purposes
         // Toast.makeText(getContext(), errorMessage,
         // Toast.LENGTH_SHORT).show();
         Log.e(errorMessage);
         return null;
      }

      // Try to parse the resulting JSON
      List<AppDescription> appList = null;
      try {
         appList = AppDescription.parseList(responseString);

      }
      catch (JSONException e) {
         Log.e(e.getMessage());
      }
      return appList;
   }

   @Override
   protected void onPostExecute(List<AppDescription> result) {
      super.onPostExecute(result);

      TempoDatabase tempoDatabase = TempoDatabase.getInstance();
      tempoDatabase.setAppDescriptions(result);

      // Message the listener that you're done.
      callback.onAppDescriptionChanged(result);
   }
}