package edu.channel4.mm.db.android.network;

import java.util.List;

import roboguice.RoboGuice;
import android.content.Context;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.database.Database;
import edu.channel4.mm.db.android.model.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.util.Log;

class GetAppListAsyncTask extends BaseGetRequestAsyncTask<List<AppDescription>> {
   private AppDescriptionCallback callback;

   @Inject Database tempoDatabase;

   public GetAppListAsyncTask(Context context, String baseUri,
                              String accessToken,
                              AppDescriptionCallback callback) {
      super(context, baseUri, accessToken);
      this.callback = callback;

      // Inject the fields of this POJO
      RoboGuice.getInjector(context).injectMembers(this);
   }

   @Override
   public List<AppDescription> call() throws Exception {
      // Execute the GET request.
      super.call();

      // Try to parse the resulting JSON
      List<AppDescription> appList = AppDescription.parseList(responseString);

      // Save the app descriptions in the database
      tempoDatabase.setAppDescriptions(appList);

      return appList;
   }

   @Override
   protected void onSuccess(List<AppDescription> result) {
      // Message the listener that you're done.
      callback.onAppDescriptionChanged(result);
   }

   @Override
   protected void onException(Exception e) {
      Log.toastE(context, e.getMessage());
      
      callback.onAppDescriptionChanged(null);
   }

   @Override
   protected String getResourceUrl() {
      return "channel4_apps";
   }
}