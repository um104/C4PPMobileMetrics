package edu.channel4.mm.db.android.network;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import edu.channel4.mm.db.android.activity.IAppListObserver;
import edu.channel4.mm.db.android.model.AppDescription;
import edu.channel4.mm.db.android.util.BaseAsyncTask;
import edu.channel4.mm.db.android.util.GraphTypes;
import edu.channel4.mm.db.android.util.Keys;

public class SalesforceConn {
	static private SalesforceConn instance;
	protected static final String SALESFORCE_URL = "%s/services/apexrest/channel4_apps/";
	private Context context;
	protected HttpClient client;
	protected List<IAppListObserver> appListObservers;

	private SalesforceConn(Context context) {
		this.context = context;

		client = new DefaultHttpClient();
	}
	
	/**
	 * Public instantiator for a SalesforceConn object. Ensures no more than one object exists.
	 * @param context The application-level context.
	 * @return A SalesforceConn operating within the given context.
	 */
	public static SalesforceConn getInstance(Context context) {
		if(instance == null)
			instance = new SalesforceConn(context);
		else
			instance.context = context;
		return instance;
	}
	
	/**
	 * Gets the list of attributes valid for this particular type of graph
	 * 
	 * @param appDescription The description of the app we're getting attributes from
	 * @param graph The graph that we're retrieving attributes for
	 */
	public void getAttribList(AppDescription appDescription, GraphTypes graph) {
		
	}

	/**
	 * Gets the app list from Salesforce.
	 * 
	 * @param appListObservers
	 *            The list of Observers that you want to update themselves when
	 *            this method is done retrieving the list of apps.
	 */
	public void getAppList(List<IAppListObserver> appListObservers) {
		this.appListObservers = appListObservers;

		new GetAppListTask(context).execute();
	}

	protected class GetAppListTask extends BaseAsyncTask {
		private final String TAG = GetAppListTask.class.getSimpleName();
		private String responseString = null;

		public GetAppListTask(Context context) {
			super(context);
		}

		@Override
		protected String doInBackground(Void... params) {
			Log.i(TAG, "Sending GET request to get app list");

			String accessToken = context.getSharedPreferences(Keys.PREFS_NS, 0)
					.getString(Keys.ACCESS_TOKEN, null);
			String instanceUrl = context.getSharedPreferences(Keys.PREFS_NS, 0).getString(Keys.INSTANCE_URL, null);

			if (accessToken == null) {
				Log.e(TAG, "No access token currently saved");
				return null;
			}

			HttpGet get = new HttpGet(String.format(SALESFORCE_URL, instanceUrl));
			get.setHeader("Authorization", "Bearer " + accessToken);

			try {
				responseString = EntityUtils.toString(client.execute(get)
						.getEntity());
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			
			Log.d(TAG, "Got JSON result: " + responseString);

			// Try to parse the resulting JSON
			List<AppDescription> appList = null;
			try {
				appList = AppDescription.parseList(responseString);
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
				return null;
			}

			// Tell each of the "observers" of the app list to update.
			for (IAppListObserver appListObserver : appListObservers) {
				appListObserver.updateAppList(appList);
			}

			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			
		}
	}

}
