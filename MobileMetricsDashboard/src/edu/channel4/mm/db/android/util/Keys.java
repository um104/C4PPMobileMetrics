package edu.channel4.mm.db.android.util;

/**
 * This class should strictly contain Keys used for data exchange formats (e.g.;
 * JSON, XML, REST requests, SharedPreferences, etc.)
 * 
 * @author girum
 * 
 */
public class Keys {

	public static final String ACCESS_TOKEN = "access_token";

	public static final String REFRESH_TOKEN = "refresh_token";

	public static final String INSTANCE_URL = "instance_url";

	public static final String C4PPMM_APP_LABEL = "C4PPMM__Label__c";

	public static final String VERSION = "Version__c";

	public static final String C4PPMM_PACKAGE_NAME = "C4PPMM__PackageName__c";

	public static final String GRAPH_TYPE = "graph_type";

	public static final String GRAPH_REQUEST_TYPE = "graph_request_type";

	public static final String REQUEST_TYPE = "requestType";

	public static final String APP_DESC = "app_description";

	public static final String ATTRIB_DESC = "attrib_description";

	public static final String ATTRIB_NAME = "attrib_name";

	public static final String SESSION_ATTRIBS = "session_attribs";

	public static final String APP_ID = "Id";

	// Don't use getSharedPreferences(String, int) anymore.
	// Instead, use PreferenceManager.getDefaultSharedPreferences(Context)
//	/**
//	 * The {@link SharedPreferences} namespace that we should use throughout the
//	 * app.
//	 */
//	public static final String PREFS_NS = "edu.channel4.mm.db.android";

	public static final String SESSION_ATTRIBUTE_DESCRIPTIONS = "sAttrs";

	public static final String EVENT_DESCRIPTIONS = "eDescs";

	public static final String NAME = "name";

	public static final String EVENT_ATTRIBUTES = "eAttrs";
	
	public static final String GEOCHART = "GEOCHART";
	
	public static final String APP_NAME = "appName";
	
	public static final String REQUEST_URL_PARAMETERS = "request_url_parameters";
	
	public static final String APP_LABEL = "appLabel";
	
	public static final String GRAPH_REQUEST_EXTRA = "graph_request_extra";
	
	public static final String TIME_INTERVAL = "timeInterval";
	
	/**
	 * Used for creating custom graphs
	 */
	public static final String EVENT_NAME_1 = "eventName1";
	
	/**
	 * Used for creating custom graphs
	 */
	public static final String ATTRIB_NAME_1 = "attribName1";
	
	public static final String LINE = "line";
	
	public static final String PIE = "pie";
	
	public static final String BAR = "bar";
	
	public static final String REQUEST_AS_JSON = "requestAsJSON";
	
	public static final String C4PPMM_EVENT_NAME = "C4PPMM__EventName__c";
	
	public static final String EVENT_NAME = "eventName";
}
