package edu.channel4.mm.db.android.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.channel4.mm.db.android.util.Keys;

public class AppDescription implements Comparable<AppDescription> {
	String appLabel;
	String packageName;
	String versionNumber;

	public AppDescription(String appLabel, String packageName, String version) {
		super();
		this.appLabel = appLabel;
		this.packageName = packageName;
		this.versionNumber = version;
	}

	public String getAppName() {
		return appLabel;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * Parses a JSON List of AppData into a proper List<AppData>
	 * 
	 * @param appDataListString
	 * @return
	 * @throws JSONException
	 */
	public static List<AppDescription> parseList(String appDataListString)
			throws JSONException {
		List<AppDescription> appDataList = new ArrayList<AppDescription>();

		JSONArray appDataArray = new JSONArray(appDataListString);

		for (int i = 0; i < appDataArray.length(); i++) {
			JSONObject appDataObject = appDataArray.getJSONObject(i);

			String appLabel = appDataObject.getString(Keys.APP_LABEL);
			String packageName = appDataObject.getString(Keys.PACKAGE_NAME);
			String version = appDataObject.getString(Keys.VERSION);

			AppDescription appData = new AppDescription(appLabel, packageName, version);
			appDataList.add(appData);
		}

		return appDataList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appLabel == null) ? 0 : appLabel.hashCode());
		result = prime * result
				+ ((packageName == null) ? 0 : packageName.hashCode());
		result = prime * result
				+ ((versionNumber == null) ? 0 : versionNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppDescription other = (AppDescription) obj;
		if (appLabel == null) {
			if (other.appLabel != null)
				return false;
		} else if (!appLabel.equals(other.appLabel))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		if (versionNumber == null) {
			if (other.versionNumber != null)
				return false;
		} else if (!versionNumber.equals(other.versionNumber))
			return false;
		return true;
	}

	@Override
	public int compareTo(AppDescription another) {
		if (!this.appLabel.equals(another.appLabel)) {
			return this.appLabel.compareTo(another.appLabel);
		} else if (!this.versionNumber.equals(another.versionNumber)) {
			return this.versionNumber.compareTo(another.versionNumber);
		} else {
			return 0;
		}
	}

}
