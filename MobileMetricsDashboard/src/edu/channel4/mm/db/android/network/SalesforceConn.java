package edu.channel4.mm.db.android.network;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import edu.channel4.mm.db.android.activity.AppListObserver;
import edu.channel4.mm.db.android.model.AppData;
import edu.channel4.mm.db.android.util.BaseAsyncTask;

public class SalesforceConn {
	private static final String TAG = SalesforceConn.class.getSimpleName();
	protected static final String SALESFORCE_URL = "https://na9.salesforce.com/services/apexrest/channel4_apps/";
	private Context context;
	protected HttpClient client;
	protected List<AppListObserver> appListObservers;

	public SalesforceConn(Context context) {
		this.context = context;

		client = new DefaultHttpClient();
	}

	/**
	 * Exposed method to getAppList().
	 * 
	 * @param The
	 *            list of AppListObservers that you want to callback
	 */
	public void getAppList() {
		new GetAppListTask(context).execute();
	}

	protected class GetAppListTask extends BaseAsyncTask {

		public GetAppListTask(Context context) {
			super(context);
		}

		@Override
		protected String doInBackground(Void... params) {
			HttpPost post = new HttpPost(SALESFORCE_URL);

			
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			List<AppData> appList = null;

			// TODO: Parse the JSON string result into a List<AppData>

			// Tell each of the "observers" of the app list to update.
			for (AppListObserver appListObserver : appListObservers) {
				appListObserver.updateAppList(appList);
			}
		}
	}

}
