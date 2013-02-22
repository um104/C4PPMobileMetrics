package edu.channel4.mm.db.android.model.request;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Logger;

// TODO: Don't use string literals:
// http://stackoverflow.com/questions/9742050/is-there-an-enum-string-resource-lookup-pattern-for-android
public enum AudienceGraphRequest implements GraphRequest {
	GEOGRAHPIC("Geographic"), DEVICE_TYPE(
			"Device Type"), OS_VERSION(
			"OS Version"), LANGUAGE_LOCALE("Language Locale"), NETWORK_CARRIER(
			"Network Carrier");

	private final String displayName;
	private Map<String, String> fields;

	private AudienceGraphRequest(String displayName) {
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
		AudienceGraphRequest audienceGraphRequest = null;

		// Try and cast the GraphRequest into a concrete UsageGraphRequest,
		// returning null if it fails.
		try {
			audienceGraphRequest = (AudienceGraphRequest) this;
		} catch (ClassCastException classCastException) {
			Logger.e(classCastException.getMessage()
					+ ", will not construct Intent for "
					+ this.toString());
			return null;
		}

		// Construct the fields and the correct Activity to go to next based on
		// the UsageGraphRequest you got.
		switch (audienceGraphRequest) {
		case LANGUAGE_LOCALE:
			// NYI
			break;
		case OS_VERSION:
			// NYI
			break;
		case DEVICE_TYPE:
			// NYI
			break;
		case GEOGRAHPIC:
			// NYI
			break;
		case NETWORK_CARRIER:
			// NYI
			break;
		default:
			Logger.e("Unknown UsageGraphRequest: " + audienceGraphRequest);
			return null;
		}

		// Sanity check to make sure we're using an implemented
		// UsageGraphRequest
		if (intent == null) {
			Logger.e("Unimplemented UsageGraphRequest selected");
			return null;
		}

		// Always put the GraphRequest type itself into the intent
		intent.putExtra(Keys.GRAPH_REQUEST_TYPE, audienceGraphRequest);

		return intent;
	}
}
