package edu.channel4.mm.db.android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.fragments.AttributeListFragment;
import edu.channel4.mm.db.android.util.Keys;


public class DashboardActivity extends Activity {
	
	private String appLabel;
	private String packageName;
	private String version;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

		Intent intent = getIntent();
		this.appLabel = intent.getStringExtra(Keys.APP_LABEL);
		this.packageName = intent.getStringExtra(Keys.PACKAGE_NAME);
		this.version = intent.getStringExtra(Keys.VERSION);
		
		//TODO(mlerner): Make call to APEX to get favorite graphs, recent graphs, other info to display
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
	
	public void genPieGraph(View view) {
		AttributeListFragment attribList = new AttributeListFragment();
		String[] attribArr;
		// Make Salesforce call to get attrib list
		// parse attrib list into String[] array
		
		Bundle args = new Bundle();
		args.putStringArray("attribs", attribArr);
		attribList.setArguments(args);
	}

}
