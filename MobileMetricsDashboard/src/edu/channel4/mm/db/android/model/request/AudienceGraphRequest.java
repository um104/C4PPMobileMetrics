package edu.channel4.mm.db.android.model.request;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Logger;

// TODO: Remove this class, it is unused

// TODO: Don't use string literals:
// http://stackoverflow.com/questions/9742050/is-there-an-enum-string-resource-lookup-pattern-for-android
public enum AudienceGraphRequest  {
	GEOGRAHPIC("Geographic", Keys.GEOCHART), DEVICE_TYPE("Device Type", null), OS_VERSION(
			"OS Version", null), LANGUAGE_LOCALE("Language Locale", null), NETWORK_CARRIER(
			"Network Carrier", null);

	private final String displayName;
	private final String restRequestType;
	private Map<String, String> fields;

	private AudienceGraphRequest(String displayName, String restRequestType) {
		this.displayName = displayName;
		this.restRequestType = restRequestType;
	}

	@Override
	public final String toString() {
		return displayName;
	}
	
	public String getRestRequestType() {
		return restRequestType;
	}


	public final Map<String, String> getFields() {
		return fields;
	}


	public String addField(String key, String value) {
		return fields.put(key, value);
	}


	public Intent constructGraphRequestIntent(Context context) {
		Intent intent = null;
		AudienceGraphRequest audienceGraphRequest = null;

		// Try and cast the GraphRequest into a concrete UsageGraphRequest,
		// returning null if it fails.
		try {
			audienceGraphRequest = (AudienceGraphRequest) this;
		} catch (ClassCastException classCastException) {
			Logger.e(classCastException.getMessage()
					+ ", will not construct Intent for " + this.toString());
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
			intent = new Intent(context, GraphViewerActivity.class);
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
