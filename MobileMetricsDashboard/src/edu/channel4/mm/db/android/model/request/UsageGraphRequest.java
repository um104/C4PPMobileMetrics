package edu.channel4.mm.db.android.model.request;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import edu.channel4.mm.db.android.activity.EventPickerActivity;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Logger;

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

	@Override
	public Intent constructGraphRequestIntent(Context context) {
		Intent intent = null;
		UsageGraphRequest usageGraphRequest = null;

		// Try and cast the GraphRequest into a concrete UsageGraphRequest,
		// returning null if it fails.
		try {
			usageGraphRequest = (UsageGraphRequest) this;
		} catch (ClassCastException classCastException) {
			Logger.e(classCastException.getMessage()
					+ ", will not construct Intent for "
					+ this.toString());
			return null;
		}

		// Construct the fields and the correct Activity to go to next based on
		// the UsageGraphRequest you got.
		switch (usageGraphRequest) {
		case ACTIVE_USERS:
			// NYI
			break;
		case EVENT_COUNT_OVER_TIME:
			intent = new Intent(context, EventPickerActivity.class);
			break;
		case NUMBER_OF_SESSIONS_OVER_TIME:
			// NYI
			break;
		case SESSION_LENGTH_OVER_TIME:
			// NYI
			break;
		case TIME_OF_DAY_DIST:
			// NYI
			break;
		default:
			Logger.e("Unknown UsageGraphRequest: " + usageGraphRequest);
			return null;
		}

		// Sanity check to make sure we're using an implemented
		// UsageGraphRequest
		if (intent == null) {
			Logger.e("Unimplemented UsageGraphRequest selected");
			return null;
		}

		// Always put the GraphRequest type itself into the intent
		intent.putExtra(Keys.GRAPH_REQUEST_TYPE, usageGraphRequest);

		return intent;
	}

	@Override
	public String getRestRequestType() {
		// TODO Auto-generated method stub
		return null;
	}
}
