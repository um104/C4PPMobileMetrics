package edu.channel4.mm.db.android.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.channel4.mm.db.android.util.Keys;

public class AppData implements Comparable<AppData> {
	String appName;
	String packageName;
	Integer versionNumber;

	public AppData(String appName, String packageName, Integer versionNumber) {
		super();
		this.appName = appName;
		this.packageName = packageName;
		this.versionNumber = versionNumber;
	}

	public String getAppName() {
		return appName;
	}

	public String getPackageName() {
		return packageName;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	/**
	 * Parses a JSON List of AppData into a proper List<AppData>
	 * 
	 * @param appDataListString
	 * @return
	 * @throws JSONException
	 */
	public static List<AppData> parseList(String appDataListString)
			throws JSONException {
		List<AppData> appDataList = new ArrayList<AppData>();

		JSONArray appDataArray = new JSONArray(appDataListString);

		for (int i = 0; i < appDataArray.length(); i++) {
			JSONObject appDataObject = appDataArray.getJSONObject(i);

			Integer appID = appDataObject.getInt(Keys.APP_ID);
			String appName = appDataObject.getString(Keys.APP_NAME);

			AppData appData = new AppData(appName, null, appID);
			appDataList.add(appData);
		}

		return appDataList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
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
		AppData other = (AppData) obj;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
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
	public int compareTo(AppData another) {
		if (!this.appName.equals(another.appName)) {
			return this.appName.compareTo(another.appName);
		} else if (!this.versionNumber.equals(another.versionNumber)) {
			return this.versionNumber.compareTo(another.versionNumber);
		} else {
			return 0;
		}
	}

}
