package edu.channel4.mm.db.android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.AppDescription;
import edu.channel4.mm.db.android.util.Keys;

public class DashboardActivity extends Activity {

	private String appId;
	private GridView gridView;
	private GridViewAdapter adapter;
	private TextView appLabel;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		// Setup the GridView
		gridView = (GridView) findViewById(R.id.gridViewDashboard);
		adapter = new GridViewAdapter();
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Toast.makeText(
				// getApplicationContext(),
				// "NOT starting activity: "
				// + adapter.subactivityDetails[position].displayName,
				// Toast.LENGTH_SHORT).show();
				Class newActivityClass = adapter.subactivityDetails[position].activityClass;
				startActivity(new Intent(getApplicationContext(),
						newActivityClass));
			}
		});

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		Intent intent = getIntent();
		AppDescription appDescription = intent
				.getParcelableExtra(Keys.APP_DESC);

		appLabel = (TextView) findViewById(R.id.textViewDashboardTitle);
		appLabel.setText(appDescription.getAppName() + " "
				+ appDescription.getVersionNumber());
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// TODO(mlerner): Make call to APEX to get favorite graphs, recent
		// graphs, other info to display.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dashboard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void genGraph(View view) {
		// // Start up an Attribute Activity with the correct info on the app
		// and
		// // the graph type
		// Intent intent = new Intent(getApplicationContext(),
		// AttributeListActivity.class);
		//
		// intent.putExtra(Keys.PREFS_NS + Keys.APP_ID, appId);
		//
		// switch (view.getId()) {
		// case R.id.genPieGraph:
		// intent.putExtra(Keys.PREFS_NS + Keys.GRAPH_TYPE, GraphType.Pie);
		// }
		//
		// startActivity(intent);
	}

	/**
	 * Helper class that aggregates details on the Activities you want to enter.
	 * These are presented to the user by the GridViewAdapter
	 */
	static class SubactivityDetails {
		private Integer iconID;
		private String displayName;
		private Class activityClass;

		public SubactivityDetails(Integer iconID, String displayName,
				Class activityClass) {
			this.iconID = iconID;
			this.displayName = displayName;
			this.activityClass = activityClass;
		}
	}

	/**
	 * Helper adapter for the GridView.
	 */
	class GridViewAdapter extends BaseAdapter {
		protected final SubactivityDetails[] subactivityDetails = new SubactivityDetails[] {
				// TODO: Put the actual Activity class objects in here once we
				// make them.
				new SubactivityDetails(R.drawable.ic_launcher, getResources()
						.getString(R.string.usage), UsageActivity.class),
				new SubactivityDetails(R.drawable.ic_launcher, getResources()
						.getString(R.string.audience), AudienceActivity.class),
				new SubactivityDetails(R.drawable.ic_launcher, getResources()
						.getString(R.string.event_funnels),
						EventFunnelsActivity.class),
				new SubactivityDetails(R.drawable.ic_launcher, getResources()
						.getString(R.string.custom_graphs),
						CustomGraphActivity.class) };

		public GridViewAdapter() {
		}

		public int getCount() {
			return subactivityDetails.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			View gridViewCell;
			ImageView icon;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				LayoutInflater inflater = getLayoutInflater();
				gridViewCell = inflater.inflate(
						R.layout.cell_dashboard_gridview_piece, parent, false);

				// gridViewCell = new ImageView(getApplicationContext());
				icon = (ImageView) gridViewCell
						.findViewById(R.id.imageViewDashboardCellIcon);

				// icon.setLayoutParams(new GridView.LayoutParams(85, 85));
				icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
				icon.setPadding(8, 8, 8, 8);
			} else {
				gridViewCell = (View) convertView;
				icon = (ImageView) gridViewCell
						.findViewById(R.id.imageViewDashboardCellIcon);
			}

			// Set the icon to be the correct icon
			icon.setImageResource(subactivityDetails[position].iconID);

			// Set the label to have the right text
			TextView label = (TextView) gridViewCell
					.findViewById(R.id.textViewDashboardCellLabel);
			label.setText(subactivityDetails[position].displayName);

			return gridViewCell;
		}
	}

}
