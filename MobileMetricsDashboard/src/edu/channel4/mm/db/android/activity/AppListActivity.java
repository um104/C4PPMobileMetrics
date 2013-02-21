package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.AppDescription;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.Keys;

public class AppListActivity extends Activity implements
		OnAppDescriptionChangedListener {

	private static final String TAG = AppListActivity.class.getSimpleName();
	protected List<AppDescription> appList;
	protected ListView listViewAppList;
	protected AppDataArrayAdapater arrayAdapter;
	protected SalesforceConn sfConn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);

		// Initialization
		sfConn = SalesforceConn.getInstance(getApplicationContext());

		// Fill up appList
		appList = new ArrayList<AppDescription>();

		// Setup ListView
		listViewAppList = (ListView) findViewById(R.id.listViewAppList);
		arrayAdapter = new AppDataArrayAdapater(this, R.id.cellAppList);
		listViewAppList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					// When app clicked, start Dashboard for that app.
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getApplicationContext(),
								DashboardActivity.class);
						// Change this to get appId from appList rather than
						// parent.get... ?
						intent.putExtra(
								Keys.PREFS_NS + Keys.APP_ID,
								((AppDescription) parent.getAdapter().getItem(
										position)).getAppId());

						startActivity(intent);
					}
				});

		// Hook up the array adapter to the ListView
		listViewAppList.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_app_list, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		// "Subscribe" this activity to the TempoDatabase for AppDescription
		// changes.
		TempoDatabase.getInstance().addOnAppDescriptionChangedListener(this);

		// Retrieve the app list from Salesforce
		getAppList(null);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// "Unsubscribe" this activity from the TempoDatabase for AppDescription
		// changes.
		TempoDatabase.getInstance().removeOnAppDescriptionChangedListener(this);
	}

	public void getAppList(View v) {
		Toast.makeText(getApplicationContext(),
				"Retrieving app list from Salesforce", Toast.LENGTH_SHORT)
				.show();

		sfConn.getAppList();
	}

	private class AppDataArrayAdapater extends ArrayAdapter<AppDescription> {

		public AppDataArrayAdapater(Context context, int textViewResourceId) {
			super(context, textViewResourceId, appList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (appList == null) {
				Log.e(TAG, "appList List is null: will not draw cell");
				return convertView;
			}

			// Grab the pertinent AppData object
			AppDescription appData = appList.get(position);

			LayoutInflater inflater = (LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.cell_app_list, null);

			TextView appName = (TextView) convertView
					.findViewById(R.id.textViewAppName);
			TextView packageName = (TextView) convertView
					.findViewById(R.id.textViewPackageName);

			appName.setText(appData.getAppName() + ", version "
					+ appData.getVersionNumber());
			packageName.setText(appData.getPackageName());

			return convertView;
		}
	}

	public void about(View v) {
		startActivity(new Intent(getApplicationContext(), AboutActivity.class));
	}

	public void logout(View v) {
		Toast.makeText(getApplicationContext(), "Logout not implemented yet",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onAppDescriptionChanged(List<AppDescription> newAppDescriptions) {
		appList.clear();
		appList.addAll(newAppDescriptions);
		arrayAdapter.notifyDataSetChanged();
	}
}
