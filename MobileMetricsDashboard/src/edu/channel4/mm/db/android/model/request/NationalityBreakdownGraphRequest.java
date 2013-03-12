package edu.channel4.mm.db.android.model.request;

import java.net.URI;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.model.graph.Graph;
import edu.channel4.mm.db.android.network.RestClientAccess;
import edu.channel4.mm.db.android.util.Keys;

/**
 * Nationality Breakdown, aka Geochart.
 * 
 * @author girum
 * 
 */
public class NationalityBreakdownGraphRequest implements GraphRequest {
   
   public NationalityBreakdownGraphRequest() {
      
   }

	@Override
	public String toString() {
		return "Nationality Breakdown";
	}

	// @Override
	// public String getRestRequestType() {
	// return "GEOCHART";
	// }
	//
	// @Override
	// public String getUrlParameterString(Context context) {
	//
	// appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0).getString(
	// Keys.APP_LABEL, null);
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
	public int getIconId() {
		return R.drawable.nationality_breakdown;
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
	
   public static final Parcelable.Creator<NationalityBreakdownGraphRequest> CREATOR = new Parcelable.Creator<NationalityBreakdownGraphRequest>(){
      public NationalityBreakdownGraphRequest createFromParcel(Parcel in) {
         return new NationalityBreakdownGraphRequest(in);
      }

      @Override
      public NationalityBreakdownGraphRequest[] newArray(int size) {
         return new NationalityBreakdownGraphRequest[size];
      }
   };
   
   public NationalityBreakdownGraphRequest(Parcel in) {
      
   }

	@Override
	public URI getUri(RestClientAccess restClientManager, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}