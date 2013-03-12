package edu.channel4.mm.db.android.callback;

import java.util.List;

import edu.channel4.mm.db.android.model.description.AppDescription;

public interface AppDescriptionCallback {

	/**
	 * Called when an App Description in the database is changed.
	 */
	public void onAppDescriptionChanged(List<AppDescription> newAppDescriptions);
}
