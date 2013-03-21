package edu.channel4.mm.db.android.network;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.callback.EventDescriptionCallback;
import edu.channel4.mm.db.android.callback.EventNameDescriptionCallback;
import edu.channel4.mm.db.android.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.Keys;

@ContextSingleton
public class SalesforceConn {
   public static final String SALESFORCE_BASE_REST_URL = "%s/services/apexrest/C4PPMM/%s";

   @Inject private Context context;
   @Inject private SharedPreferences prefs;

   /**
    * Call this when you want to update your list of "attributes"! It will
    * update the list of EventDescriptions, which contains the list of
    * AttributeDescriptions.
    */
   public void getEventListViaNetwork(EventDescriptionCallback callback) {

      // Grab the most up-to-date appLabel
      String appLabel = prefs.getString(Keys.APP_LABEL, null);
      
      new GetEventListAsyncTask(context, appLabel, callback).execute();
   }

   /**
    * Call this when you want to update your list of "event names"! It will
    * update the list of EventNameDescriptions.
    */
   public void
            getEventNameListViaNetwork(EventNameDescriptionCallback callback) {

      // Grab the most up-to-date appLabel
      String appLabel = prefs.getString(Keys.APP_LABEL, null);
      
      new GetEventNameListAsyncTask(context, appLabel, callback).execute();
   }

   /**
    * Gets the app list from Salesforce. Don't call this method directly.
    * Instead, call the GraphFactory.
    */
   public void getGraphViaNetwork(GraphRequest graphRequest,
            GraphLoadCallback callback) {
      new GraphRequestAsyncTask(context, graphRequest, callback).execute();
   }

   public void getAppListViaNetwork(AppDescriptionCallback callback) {
      new GetAppListAsyncTask(context, callback).execute();
   }
}
