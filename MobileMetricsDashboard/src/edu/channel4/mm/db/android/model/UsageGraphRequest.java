package edu.channel4.mm.db.android.model;

public enum UsageGraphRequest implements GraphRequest {
	SESSION_LENGTH_OVER_TIME("Session Length over Time"), NUMBER_OF_SESSIONS_OVER_TIME(
			"Number of Sesssions over Time"), EVENT_COUNT_OVER_TIME(
			"Event Count over Time"), ACTIVE_USERS("Active Users"), TIME_OF_DAY_DIST(
			"Time of Day Distribution");

	private final String displayName;

	private UsageGraphRequest(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
