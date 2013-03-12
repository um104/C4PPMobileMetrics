package edu.channel4.mm.db.android.callback;

import java.util.List;

import edu.channel4.mm.db.android.model.description.EventNameDescription;

public interface EventNameDescriptionCallback {
   public void onEventNameDescriptionChanged(List<EventNameDescription> newEventNameDescriptions);
}
