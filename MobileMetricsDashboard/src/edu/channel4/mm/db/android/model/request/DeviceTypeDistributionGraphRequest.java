package edu.channel4.mm.db.android.model.request;

import java.net.URI;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.network.RestClientAccess;
import edu.channel4.mm.db.android.util.Keys;

public class DeviceTypeDistributionGraphRequest implements GraphRequest {

	@Override
	public String toString() {
		return "Device Type Distribution";
	}

	// @Override
	// public String getRestRequestType() {
	// return "DEVICE_TYPE_DIST";
	// }
	//
	// @Override
	// public String getUrlParameterString(Context context) {
	// String appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
	// .getString(Keys.APP_LABEL, null);
	//
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	//
	// params.add(new BasicNameValuePair(Keys.REQUEST_TYPE,
	// getRestRequestType()));
	// params.add(new BasicNameValuePair(Keys.APP_LABEL_URL_PARAMETER_NAME,
	// appLabel));
	//
	// String paramString = URLEncodedUtils.format(params, "utf-8");
	//
	// return paramString;
	// }

	@Override
	public Intent constructGraphRequestIntent(Context context) {
		Intent intent = new Intent(context, GraphViewerActivity.class);

		RestClientAccess restClientManager = RestClientAccess.getInstance();

		intent.putExtra(Keys.REQUEST_URL_PARAMETERS,
				getUri(restClientManager, context).toString());

		return intent;
	}

	@Override
	public int getIcon() {
		return R.drawable.device_type_dist;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

	@Override
	public URI getUri(RestClientAccess restClientManager, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
