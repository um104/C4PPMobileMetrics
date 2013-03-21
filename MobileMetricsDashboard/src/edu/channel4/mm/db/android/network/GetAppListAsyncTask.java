package edu.channel4.mm.db.android.network;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import roboguice.RoboGuice;
import android.content.Context;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.util.BaseAsyncTask2;
import edu.channel4.mm.db.android.util.Log;

// TODO: Abstract common GET request logic into a single class.
class GetAppListAsyncTask extends BaseAsyncTask2<List<AppDescription>> {
   protected static final String APPS_URL_SUFFIX = "channel4_apps";
   private AppDescriptionCallback callback;

   @Inject TempoDatabase tempoDatabase;
   @Inject RestClientAccess restClientAccess;

   public GetAppListAsyncTask(Context context, AppDescriptionCallback callback) {
      super(context);
      this.callback = callback;

      // Inject the fields of this POJO
      RoboGuice.getInjector(context).injectMembers(this);
   }

   // @Override
   // protected List<AppDescription> doInBackground(Void... params) {
   // Log.i("Sending GET request to get app list");
   //
   // String accessToken = restClientAccess.getAccessToken();
   // String instanceUrl = restClientAccess.getInstanceURL().toString();
   //
   // if (accessToken == null) {
   // Log.e("No access token currently saved");
   // return null;
   // }
   //
   // Log.d("Access token: " + accessToken);
   // Log.d("Instance URL: " + instanceUrl);
   //
   // HttpClient client = new DefaultHttpClient();
   // HttpUriRequest getRequest = new HttpGet(String.format(
   // SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl,
   // APPS_URL_SUFFIX));
   // getRequest.setHeader("Authorization", "Bearer " + accessToken);
   //
   // String responseString = null;
   //
   // try {
   // responseString = EntityUtils.toString(client.execute(getRequest)
   // .getEntity());
   // }
   // catch (Exception e) {
   // Log.e(e.getMessage());
   // }
   //
   // Log.d("Got JSON result: " + responseString);
   //
   // if (responseString == null) {
   // Log.e("ERROR: Attempted to parse null App list.");
   // return null;
   // }
   //
   // // Try to parse the resulting JSON
   // List<AppDescription> appList = null;
   // try {
   // appList = AppDescription.parseList(responseString);
   //
   // }
   // catch (JSONException e) {
   // Log.e(e.getMessage());
   // }
   // return appList;
   // }

   @Override
   // protected void onPostExecute(List<AppDescription> result) {
            protected
            void onSuccess(List<AppDescription> result) {
      // super.onPostExecute(result);

      tempoDatabase.setAppDescriptions(result);

      // Message the listener that you're done.
      callback.onAppDescriptionChanged(result);
   }

   @Override
   public List<AppDescription> call() throws Exception {
      Log.i("Sending GET request to get app list");

      String accessToken = restClientAccess.getAccessToken();
      String instanceUrl = restClientAccess.getInstanceURL().toString();

      if (accessToken == null) {
         throw new Exception(
                  "Error in GetAppList: No access token currently saved");
         // Log.e("No access token currently saved");
         // return null;
      }

      Log.d("Access token: " + accessToken);
      Log.d("Instance URL: " + instanceUrl);

      HttpClient client = new DefaultHttpClient();
      HttpUriRequest getRequest = new HttpGet(String.format(
               SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl,
               APPS_URL_SUFFIX));
      getRequest.setHeader("Authorization", "Bearer " + accessToken);

      String responseString = null;

//      try {
         responseString = EntityUtils.toString(client.execute(getRequest)
                  .getEntity());
//      }
//      catch (Exception e) {
//         Log.e(e.getMessage());
//      }

      Log.d("Got JSON result: " + responseString);

      if (responseString == null) {
         throw new Exception("ERROR: Attempted to parse null App list.");
//         Log.e("ERROR: Attempted to parse null App list.");
//         return null;
      }

      // Try to parse the resulting JSON
      List<AppDescription> appList = null;
//      try {
         appList = AppDescription.parseList(responseString);
//
//      }
//      catch (JSONException e) {
//         Log.e(e.getMessage());
//      }
      return appList;
   }

   @Override
   protected void onException(Exception e) {
      Log.toastE(context, e.getMessage());
   }

}