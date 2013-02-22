package edu.channel4.mm.db.android.database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.channel4.mm.db.android.activity.OnAppDescriptionChangedListener;
import edu.channel4.mm.db.android.model.description.AppDescription;

/**
 * BS temp database. This is just a singleton containing a bunch of ArrayLists
 * of models.
 * 
 * @author girum
 * 
 */
public class TempoDatabase {

	private static TempoDatabase instance = new TempoDatabase();
	private List<AppDescription> appDescriptions = new ArrayList<AppDescription>();
	private Set<OnAppDescriptionChangedListener> onAppDescriptionChangedListeners = new HashSet<OnAppDescriptionChangedListener>();

	private TempoDatabase() {
		// singleton (hide constructor)
	}

	public static TempoDatabase getInstance() {
		return instance;
	}

	/**
	 * Sets the tempo DB to have a new list of AppDescriptions, notifying all
	 * listeners in the process.
	 * 
	 * @param appDescriptions
	 */
	public void setAppDescriptions(List<AppDescription> appDescriptions) {
		this.appDescriptions = appDescriptions;

		for (OnAppDescriptionChangedListener onAppDescriptionChangedListener : onAppDescriptionChangedListeners) {
			onAppDescriptionChangedListener
					.onAppDescriptionChanged(appDescriptions);
		}
	}

	/**
	 * @param onAppDescriptionChangedListener
	 * @return true if this set did not already contain the specified element
	 */
	public boolean addOnAppDescriptionChangedListener(
			OnAppDescriptionChangedListener onAppDescriptionChangedListener) {
		return onAppDescriptionChangedListeners
				.add(onAppDescriptionChangedListener);
	}

	/**
	 * @param onAppDescriptionChangedListener
	 * @return true if this set contained the specified element
	 */
	public boolean removeOnAppDescriptionChangedListener(
			OnAppDescriptionChangedListener onAppDescriptionChangedListener) {
		return onAppDescriptionChangedListeners
				.remove(onAppDescriptionChangedListener);
	}
}
