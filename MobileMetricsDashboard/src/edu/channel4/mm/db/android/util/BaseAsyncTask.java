package edu.channel4.mm.db.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	/**
	 * Application context passed in from the constructor.
	 */
	private Context context;

	/**
	 * Require all AsyncTasks to pass in a {@link Context} in their constructor.
	 * 
	 * @param context
	 */
	public BaseAsyncTask(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}
	
	/**
	 * Before every AsyncTask runs, we should verify that we're actually
	 * connected to the Internet.
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if (!phoneConnectedToInternet()) {
			Toast.makeText(context, "Error: Not connected to Internet.",
					Toast.LENGTH_SHORT).show();
			cancel(true);
		}
	}

	@Override
	protected abstract Result doInBackground(Params... params);

	/**
	 * Helper method to determine if this phone is connected to the Internet or
	 * not.
	 * 
	 * @return
	 */
	private boolean phoneConnectedToInternet() {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnected();
	}

}
