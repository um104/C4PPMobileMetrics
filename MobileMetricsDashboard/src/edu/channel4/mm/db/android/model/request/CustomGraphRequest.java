package edu.channel4.mm.db.android.model.request;

import java.net.URI;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.EditGraphRequestActivity;
import edu.channel4.mm.db.android.network.RestClientAccess;
import edu.channel4.mm.db.android.util.Keys;

public class CustomGraphRequest implements GraphRequest, HasAttributeParameter,
		Parcelable {

	private String appLabel;
	private String eventName1;
	private String attribName1;

	public CustomGraphRequest() {

	}

	public Intent constructGraphRequestIntent(Context context) {
		Intent intent = new Intent(context, EditGraphRequestActivity.class);

		intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, this);

		return intent;
	}

//	@Override
//	public String getRestRequestType() {
//		return "CUSTOM";
//	}
//
//	@Override
//	public String getUrlParameterString(Context context) {
//		appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0).getString(
//				Keys.APP_LABEL, null);
//
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//		// Should be "custom" "SessionOverTime", "EventOverTime", etc.
//		params.add(new BasicNameValuePair(Keys.REQUEST_TYPE,
//				getRestRequestType()));
//		params.add(new BasicNameValuePair(Keys.APP_LABEL_URL_PARAMETER_NAME,
//				appLabel));
//		params.add(new BasicNameValuePair(Keys.EVENT_NAME_1, eventName1));
//		params.add(new BasicNameValuePair(Keys.ATTRIB_NAME_1, attribName1));
//
//		String paramString = URLEncodedUtils.format(params, "utf-8");
//
//		return paramString;
//	}

	@Override
	public void setEventName(String eventName) {
		this.eventName1 = eventName;
	}

	@Override
	public String getEventName() {
		return eventName1;
	}

	@Override
	public void setAttributeName(String attribName) {
		this.attribName1 = attribName;
	}

	@Override
	public String getAttributeName() {
		return attribName1;
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
		dest.writeString(eventName1);
		dest.writeString(attribName1);
	}

	public static final Parcelable.Creator<CustomGraphRequest> CREATOR = new Parcelable.Creator<CustomGraphRequest>() {
		public CustomGraphRequest createFromParcel(Parcel in) {
			return new CustomGraphRequest(in);
		}

		@Override
		public CustomGraphRequest[] newArray(int size) {
			return new CustomGraphRequest[size];
		}
	};

	private CustomGraphRequest(Parcel in) {
		// Note: Parcel data is read in a FIFO manner.
		this.appLabel = in.readString();
		this.eventName1 = in.readString();
		this.attribName1 = in.readString();
	}

	@Override
	public int getIconId() {
	   return R.drawable.ic_launcher;
	}

	@Override
	public URI getUri(RestClientAccess restClientManager, Context context) {
		return null;
	}

}
