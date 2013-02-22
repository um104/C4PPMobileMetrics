package edu.channel4.mm.db.android.model.request;

import java.util.Map;

// TODO: Don't use string literals:
// http://stackoverflow.com/questions/9742050/is-there-an-enum-string-resource-lookup-pattern-for-android
public enum UsageGraphRequest implements GraphRequest {
	SESSION_LENGTH_OVER_TIME("Session Length over Time"), NUMBER_OF_SESSIONS_OVER_TIME(
			"Number of Sesssions over Time"), EVENT_COUNT_OVER_TIME(
			"Event Count over Time"), ACTIVE_USERS("Active Users"), TIME_OF_DAY_DIST(
			"Time of Day Distribution");

	private final String displayName;
	private Map<String, String> fields;

	private UsageGraphRequest(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public final String toString() {
		return displayName;
	}

	@Override
	public final Map<String, String> getFields() {
		return fields;
	}

	@Override
	public String addField(String key, String value) {
		return fields.put(key, value);
	}
}
