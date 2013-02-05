package edu.channel4.mm.db.android.util;

/**
 * This class should strictly contain Keys used for data exchange formats (e.g.
 * JSON, XML, REST requests, SharedPreferences, etc.)
 * 
 * @author girum
 * 
 */
public class Keys {

	public static final String ACCESS_TOKEN = "access_token";

	public static final String REFRESH_TOKEN = "refresh_token";

	public static final String INSTANCE_URL = "instance_url";

	public static final String APP_LABEL = "AppLabel__c";

	public static final String VERSION = "Version__c";
	
	public static final String PACKAGE_NAME = "PackageName__c";

	/**
	 * The {@link SharedPreferences} namespace that we should use throughout the
	 * app.
	 */
	public static final String PREFS_NS = "edu.channel4.mm.db.android";
}
