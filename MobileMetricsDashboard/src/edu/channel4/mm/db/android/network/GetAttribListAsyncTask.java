package edu.channel4.mm.db.android.network;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import edu.channel4.mm.db.android.callback.AttributeDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AttributeDescription;
import edu.channel4.mm.db.android.util.BaseAsyncTask;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

class GetAttribListAsyncTask extends BaseAsyncTask<Void, Void, String> {
	protected static final String ATTRIBS_URL_SUFFIX = "channel4_attributes";
	private String responseString = null;
	private AttributeDescriptionCallback listener;

	public GetAttribListAsyncTask(Context context, HttpClient client,
			AttributeDescriptionCallback listener) {
		super(context, client);
		this.listener = listener;
	}

	@Override
	protected String doInBackground(Void... params) {
		Log.i("Sending GET request to get attribute list");

		RestClientAccess restClientAccess = RestClientAccess.getInstance();

		String accessToken = restClientAccess.getAccessToken();
		String instanceUrl = restClientAccess.getInstanceURL().toString();

		if (accessToken == null) {
			Log.e("No access token currently saved");
			return null;
		}

		// Put together the HTTP Request to be sent to Salesforce for the
		// Attibute list

		String url = String.format(SalesforceConn.SALESFORCE_BASE_REST_URL,
				instanceUrl, ATTRIBS_URL_SUFFIX);
		// Add AppLabel parameter
		String appLabel = getContext().getSharedPreferences(Keys.PREFS_NS, 0)
				.getString(Keys.APP_LABEL, null);

		url += "?appLabel=";
		url += Uri.encode(appLabel);

		HttpGet attribRequest = new HttpGet(url);

		attribRequest.setHeader("Authorization", "Bearer " + accessToken);

		try {
			// Get the response string, the Attribute List in JSON form
			responseString = EntityUtils.toString(getClient().execute(
					attribRequest).getEntity());
		} catch (ClientProtocolException e) {
			Log.e(e.getMessage());
		} catch (IOException e) {
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
		List<AttributeDescription> attributeList = null;
		try {
			attributeList = AttributeDescription.parseList(responseString);

			TempoDatabase tempoDatabase = TempoDatabase.getInstance();
			tempoDatabase.setAttributeDescriptions(attributeList);
		} catch (JSONException e) {
			Log.e(e.getMessage());
			return;
		}

		listener.onAttributeDescriptionChanged(attributeList);
	}
}