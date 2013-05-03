package edu.channel4.mm.db.android.model.description;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import edu.channel4.mm.db.android.util.Keys;

public class AppDescription implements Comparable<AppDescription>, Parcelable {

	private long id;
	protected String appLabel;
	protected String packageName;
	protected String appId;

	public AppDescription(String appLabel, String packageName, String version,
			String appId) {
		this.appLabel = appLabel;
		this.packageName = packageName;
		this.appId = appId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appLabel;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getAppId() {
		return appId;
	}

	/**
	 * Parses a JSON List of AppDescription into a proper List<
	 * {@code AppDescription}>
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

			String appLabel = appDataObject.getString(Keys.C4PPMM_APP_LABEL);
			String packageName = appDataObject.getString(Keys.C4PPMM_PACKAGE_NAME);
			String appId = appDataObject.getString(Keys.ID);

			AppDescription appData = new AppDescription(appLabel, packageName,
					"", appId);
			appDataList.add(appData);
		}

		return appDataList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appLabel == null) ? 0 : appLabel.hashCode());
		result = prime * result
				+ ((packageName == null) ? 0 : packageName.hashCode());
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
		if (appId == null && other.appId == null)
			return false;
		else if (!appId.equals(other.appId))
			return false;
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
		return true;
	}

	@Override
	public int compareTo(AppDescription another) {
		if (!this.appLabel.equals(another.appLabel)) {
			return this.appLabel.compareTo(another.appLabel);
		} else {
			return 0;
		}
	}

	/* Everything from here down is for implementing the Parcelable interface */
	
	@Override
	public int describeContents() {
		// Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// Note: Parcel data is read in a FIFO manner.
		dest.writeString(appLabel);
		dest.writeString(packageName);
		dest.writeString(appId);
	}

	public static final Parcelable.Creator<AppDescription> CREATOR = new Parcelable.Creator<AppDescription>() {
		public AppDescription createFromParcel(Parcel in) {
			return new AppDescription(in);
		}

		@Override
		public AppDescription[] newArray(int size) {
			return new AppDescription[size];
		}
	};

	private AppDescription(Parcel in) {
		// Note: Parcel data is read in a FIFO manner.
		this.appLabel = in.readString();
		this.packageName = in.readString();
		this.appId = in.readString();
	}
}
