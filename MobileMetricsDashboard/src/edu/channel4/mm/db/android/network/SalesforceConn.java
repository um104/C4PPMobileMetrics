package edu.channel4.mm.db.android.network;

import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import edu.channel4.mm.db.android.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.callback.EventDescriptionCallback;
import edu.channel4.mm.db.android.callback.EventNameDescriptionCallback;
import edu.channel4.mm.db.android.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.model.request.GraphRequest;

@ContextSingleton
public class SalesforceConn {
   public static final String SALESFORCE_BASE_REST_URL = "%s/services/apexrest/C4PPMM/%s";
   public static final String EVENTS_URL_SUFFIX = "channel4_events";

   @Inject private Context context;

   /**
    * Call this when you want to update your list of "attributes"! It will
    * update the list of EventDescriptions, which contains the list of
    * AttributeDescriptions.
    */
   public void getEventListViaNetwork(EventDescriptionCallback callback) {
      new GetEventListAsyncTask(context, callback).execute();
   }

   /**
    * Call this when you want to update your list of "event names"! It will
    * update the list of EventNameDescriptions.
    */
   public void
            getEventNameListViaNetwork(EventNameDescriptionCallback callback) {
      new GetEventNameListAsyncTask(context, callback).execute();
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
