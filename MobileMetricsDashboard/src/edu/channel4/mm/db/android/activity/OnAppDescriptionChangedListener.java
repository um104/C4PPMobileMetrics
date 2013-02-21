package edu.channel4.mm.db.android.activity;

import java.util.List;

import edu.channel4.mm.db.android.model.AppDescription;

public interface OnAppDescriptionChangedListener {

	/**
	 * Called when an App Description in the database is changed.
	 */
	public void onAppDescriptionChanged(List<AppDescription> newAppDescriptions);
}
