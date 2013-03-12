package edu.channel4.mm.db.android.activity;

import java.util.List;

import edu.channel4.mm.db.android.model.description.EventDescription;

public interface OnEventDescriptionChangedListener {

   /**
    * Called when an Event Description in the database is changed.
    */
   public void onEventDescriptionChanged(List<EventDescription> newEventDescriptions);
}
