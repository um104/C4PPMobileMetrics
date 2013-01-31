package edu.channel4.mm.db.android.model;

public class AppData {
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
}
