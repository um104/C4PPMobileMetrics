package edu.channel4.mm.db.android.activity;

import java.util.List;

import edu.channel4.mm.db.android.model.AppDescription;

public interface IAppListObserver {

	/**
	 * Updates the internal state of the object with a new List<AppData>
	 * 
	 * @param appList
	 */
	public void updateAppList(List<AppDescription> appList);
}
