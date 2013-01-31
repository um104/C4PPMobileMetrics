package edu.channel4.mm.db.android.activity;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.AppData;

public class AppListActivity extends Activity implements AppListObserver {

	List<AppData> appList;
	ListView listViewAppList;
	AppDataArrayAdapater arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);

		listViewAppList = (ListView) findViewById(R.id.listViewAppList);

		// Fill up appList with fakes
		appList = Arrays.asList(new AppData("test app", "com.example", 1),
				new AppData("Hang", "com.hangapp.android", 4), new AppData(
						"Polytalk", "com.polytalk.ios", 2));
		arrayAdapter = new AppDataArrayAdapater(this, R.id.cellAppList);
		listViewAppList.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_app_list, menu);
		return true;
	}

	private class AppDataArrayAdapater extends ArrayAdapter<AppData> {

		public AppDataArrayAdapater(Context context, int textViewResourceId) {
			super(context, textViewResourceId, appList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// Grab the pertinent AppData object
			AppData appData = appList.get(position);

			// If it's the first time loading this cell
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) getApplicationContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.cell_app_list, null);

				TextView appName = (TextView) convertView
						.findViewById(R.id.textViewAppName);
				TextView packageName = (TextView) convertView
						.findViewById(R.id.textViewPackageName);

				appName.setText(appData.getAppName() + ", version"
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
	public void updateAppList(List<AppData> appList) {
		this.appList = appList;
		arrayAdapter.notifyDataSetChanged();
	}

}
