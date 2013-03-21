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

   private EventDescriptionCallback callback;

   @Inject private TempoDatabase tempoDatabase;
   @Inject private RestClientAccess restClientAccess;

   @SuppressWarnings("serial")
   public GetEventListAsyncTask(Context context, String baseUri,
                                String accessToken, final String appLabel,
                                EventDescriptionCallback callback) {
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
   public List<EventDescription> call() throws Exception {
      // Execute the GET request
      super.call();

      // Attempt to parse the resulting string
      List<EventDescription> eventList = EventDescription
               .parseList(responseString);

      // Save the parsed EventList into the database
      tempoDatabase.setEventDescriptions(eventList);

      return eventList;
   }

   @Override
   protected void onSuccess(List<EventDescription> result) {
      // Message the listener that you're done.
      callback.onEventDescriptionChanged(result);
   }

   @Override
   protected void onException(Exception e) {
      Log.toastE(context, e.getMessage());
   }

   @Override
   protected String getResourceUrl() {
      return "channel4_attributes";
   }
}
