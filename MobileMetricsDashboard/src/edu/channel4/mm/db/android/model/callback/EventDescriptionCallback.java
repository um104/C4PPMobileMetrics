package edu.channel4.mm.db.android.model.callback;

import java.util.List;

import edu.channel4.mm.db.android.model.description.EventDescription;

public interface EventDescriptionCallback {

   /**
    * Called when an Event Description in the database is changed.
    */
   public void onEventDescriptionChanged(List<EventDescription> newEventDescriptions);
}
