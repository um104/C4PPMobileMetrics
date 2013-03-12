package edu.channel4.mm.db.android.activity;

import java.util.List;

import edu.channel4.mm.db.android.model.description.EventNameDescription;

public interface OnEventNameDescriptionChangedListener {

   /**
    * Called when an Event Name Description in the database is changed.
    */
   public void onEventNameDescriptionChanged(List<EventNameDescription> newEventNameDescriptions);
   
}
