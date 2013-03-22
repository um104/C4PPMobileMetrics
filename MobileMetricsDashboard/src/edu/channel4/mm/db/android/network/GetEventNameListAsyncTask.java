package edu.channel4.mm.db.android.network;

import java.util.HashMap;
import java.util.List;

import roboguice.RoboGuice;
import android.content.Context;
import android.net.Uri;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.callback.EventNameDescriptionCallback;
import edu.channel4.mm.db.android.model.description.EventNameDescription;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

public class GetEventNameListAsyncTask extends
         BaseGetRequestAsyncTask<List<EventNameDescription>> {

   public static final String EVENTS_URL_SUFFIX = "channel4_events";
   private EventNameDescriptionCallback callback;

   @Inject private TempoDatabase tempoDatabase;

   @SuppressWarnings("serial")
   public GetEventNameListAsyncTask(Context context, String baseUri,
                                    String accessToken, final String appLabel,
                                    EventNameDescriptionCallback callback) {
      super(context, baseUri, accessToken, new HashMap<String, String>() {
         {
            put(Keys.APP_LABEL, Uri.encode(appLabel));
         }
      });
      this.callback = callback;

      // Inject the fields of this POJO
      RoboGuice.getInjector(context).injectMembers(this);
   }

   @Override
   public List<EventNameDescription> call() throws Exception {
      // Execute the GET request
      super.call();

      // Attempt to parse the HTTP response string
      List<EventNameDescription> eventNameList = EventNameDescription
               .parseList(responseString);

      // Save the EventNameList into the database.
      tempoDatabase.setEventNameDescriptions(eventNameList);

      return eventNameList;
   }

   @Override
   protected void onSuccess(List<EventNameDescription> result) {
      // Message the listener that you're done.
      callback.onEventNameDescriptionChanged(result);
   }

   @Override
   protected void onException(Exception e) {
      Log.toastE(context, e.getMessage());
   }

   @Override
   protected String getResourceUrl() {
      return "channel4_events";
   }
}
