package edu.channel4.mm.db.android.network;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.widget.Toast;
import edu.channel4.mm.db.android.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.util.BaseAsyncTask;
import edu.channel4.mm.db.android.util.Log;

class GetAppListAsyncTask extends BaseAsyncTask<Void, Void, String> {
	protected static final String APPS_URL_SUFFIX = "channel4_apps";
	private String responseString = null;
	private AppDescriptionCallback listener;

	public GetAppListAsyncTask(Context context, HttpClient client,
			AppDescriptionCallback listener) {
		super(context, client);
		this.listener = listener;
	}

	@Override
	protected String doInBackground(Void... params) {
		Log.i("Sending GET request to get app list");

		RestClientAccess restClientAccess = RestClientAccess.getInstance();

		String accessToken = restClientAccess.getAccessToken();
		String instanceUrl = restClientAccess.getInstanceURL().toString();

		if (accessToken == null) {
			Log.e("No access token currently saved");
			return null;
		}

		Log.d("Access token: " + accessToken);
		Log.d("Instance URL: " + instanceUrl);

		HttpUriRequest getRequest = new HttpGet(String.format(
				SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl,
				APPS_URL_SUFFIX));
		getRequest.setHeader("Authorization", "Bearer " + accessToken);

		try {
			responseString = EntityUtils.toString(getClient().execute(
					getRequest).getEntity());
		} catch (Exception e) {
			Log.e(e.getMessage());
		}

		Log.d("Got JSON result: " + responseString);

		return responseString;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if (responseString == null) {
			final String errorMessage = "ERROR: Attempted to parse null App list.";
			Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT)
					.show();
			Log.e(errorMessage);
			return;
		}

		// Try to parse the resulting JSON
		List<AppDescription> appList = null;
		try {
			appList = AppDescription.parseList(responseString);

			TempoDatabase tempoDatabase = TempoDatabase.getInstance();
			tempoDatabase.setAppDescriptions(appList);
		} catch (JSONException e) {
			Log.e(e.getMessage());
			return;
		}

		// Message the listener that you're done.
		listener.onAppDescriptionChanged(appList);
	}
}