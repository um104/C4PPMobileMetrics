package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.Collections;
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
import edu.channel4.mm.db.android.model.AppDescription;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.Keys;

public class AppListActivity extends Activity implements IAppListObserver {
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
		sfConn = new SalesforceConn(getApplicationContext());

		// Fill up appList with fakes
		appList = new ArrayList<AppDescription>();

		// Setup ListView
		listViewAppList = (ListView) findViewById(R.id.listViewAppList);
		arrayAdapter = new AppDataArrayAdapater(this, R.id.cellAppList);
		listViewAppList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String appName = ((AppDescription)parent.getAdapter().getItem(position)).getAppName();
				String packageName = ((AppDescription)parent.getAdapter().getItem(position)).getPackageName();
				String version = ((AppDescription)parent.getAdapter().getItem(position)).getVersionNumber();
				
				Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
				intent.putExtra(Keys.APP_LABEL, appName);
				intent.putExtra(Keys.PACKAGE_NAME, packageName);
				intent.putExtra(Keys.VERSION, version);
				
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

		// Retrieve the app list from Salesforce
		getAppList(null);
	}

	public void getAppList(View v) {
		Toast.makeText(getApplicationContext(),
				"Retrieving app list from Salesforce", Toast.LENGTH_SHORT)
				.show();

		// Construct a list of classes that care about the getAppList callback
		List<IAppListObserver> appListObservers = new ArrayList<IAppListObserver>();

		// Add this activity to that list
		appListObservers.add(this);

		// Execute the getAppList method asynchronously
		sfConn.getAppList(appListObservers);
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

			// If it's the first time loading this cell
			if (convertView == null) {
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
			} else {
				// If it's not the first time loading this cell, attempt
				// to reuse the cell.
			}

			return convertView;
		}
	}

	@Override
	public void updateAppList(final List<AppDescription> appList) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AppListActivity.this.appList.clear();

				for (AppDescription appData : appList) {
					AppListActivity.this.appList.add(appData);
				}

				Collections.sort(AppListActivity.this.appList);
				arrayAdapter.notifyDataSetChanged();
			}
		});
	}

}
