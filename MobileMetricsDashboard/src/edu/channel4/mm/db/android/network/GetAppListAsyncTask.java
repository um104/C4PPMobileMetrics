package edu.channel4.mm.db.android.network;

import java.util.List;

import roboguice.RoboGuice;
import android.content.Context;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.util.Log;

class GetAppListAsyncTask extends BaseGetRequestAsyncTask<List<AppDescription>> {
   protected static final String APPS_URL_SUFFIX = "channel4_apps";
   private AppDescriptionCallback callback;

   @Inject TempoDatabase tempoDatabase;

   public GetAppListAsyncTask(Context context, AppDescriptionCallback callback) {
      super(context, APPS_URL_SUFFIX);
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