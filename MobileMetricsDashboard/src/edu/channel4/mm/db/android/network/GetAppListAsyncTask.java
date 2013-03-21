package edu.channel4.mm.db.android.network;

import java.util.List;

import roboguice.RoboGuice;
import android.content.Context;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.util.Log;

// TODO: Abstract common GET request logic into a single class.
class GetAppListAsyncTask extends BaseGetRequestAsyncTask<List<AppDescription>> {
   protected static final String APPS_URL_SUFFIX = "channel4_apps";
   private AppDescriptionCallback callback;

   @Inject TempoDatabase tempoDatabase;
//   @Inject RestClientAccess restClientAccess;

   public GetAppListAsyncTask(Context context, AppDescriptionCallback callback) {
      super(context, APPS_URL_SUFFIX);
      this.callback = callback;

      // Inject the fields of this POJO
      RoboGuice.getInjector(context).injectMembers(this);
   }

   @Override
   public List<AppDescription> call() throws Exception {
//      Log.i("Sending GET request");
//
//      String accessToken = restClientAccess.getAccessToken();
//      String instanceUrl = restClientAccess.getInstanceURL().toString();
//
//      if (accessToken == null) {
//         throw new Exception(
//                  "Error in GetAppList: No access token currently saved");
//      }
//
//      Log.d("Access token: " + accessToken);
//      Log.d("Instance URL: " + instanceUrl);
//
//      HttpClient client = new DefaultHttpClient();
//      HttpUriRequest getRequest = new HttpGet(String.format(
//               SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl,
//               APPS_URL_SUFFIX));
//      getRequest.setHeader("Authorization", "Bearer " + accessToken);
//
//      String responseString = EntityUtils.toString(client.execute(getRequest)
//               .getEntity());
//
//      if (responseString != null) {
//         Log.d("Got HTTP result: " + responseString);
//      }
//      else {
//         throw new Exception(
//                  "ERROR: GET request receieved null response string.");
//      }

      // Execute the GET request.
      super.call();
      
      // Try to parse the resulting JSON
      List<AppDescription> appList = AppDescription.parseList(responseString);

      return appList;
   }

   @Override
   protected void onSuccess(List<AppDescription> result) {
      // Save the app descriptions in the database
      tempoDatabase.setAppDescriptions(result);

      // Message the listener that you're done.
      callback.onAppDescriptionChanged(result);
   }

   @Override
   protected void onException(Exception e) {
      Log.toastE(context, e.getMessage());
   }


}