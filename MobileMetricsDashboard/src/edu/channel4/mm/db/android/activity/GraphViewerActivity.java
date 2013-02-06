package edu.channel4.mm.db.android.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.AppDescription;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.GraphType;
import edu.channel4.mm.db.android.util.Keys;

public class GraphViewerActivity extends Activity {
	
	//TODO(mlerner): Make this use a WebActivity as its main form of display. See VertProto for example.
	private GraphType graphType;
	private AppDescription appDescription;
	private String attributeName;
	private SalesforceConn sfConn;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph_viewer);
		
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // Get intent and accompanying data
		Intent intent = getIntent();
		appDescription = intent.getParcelableExtra(Keys.PREFS_NS + Keys.APP_DESC);
		graphType = (GraphType) intent.getSerializableExtra(Keys.PREFS_NS + Keys.GRAPH_TYPE);
		attributeName = intent.getStringExtra(Keys.PREFS_NS + Keys.ATTRIB_NAME);
		
		// Instantiate SalesforceConn
		sfConn = SalesforceConn.getInstance(getApplicationContext());
		
		// Construct Graph Request JSON Object
		//TODO(mlerner): Finish building graph type. Make it dependent on graphType (for number of attribs)
		JSONObject graphRequest = new JSONObject();
		try {
			graphRequest.put("type",graphType.name());
			graphRequest.put("attrib1", attributeName);
		} catch (JSONException e) {
			// TODO(mlerner): Make this a real error message
			Log.e("BadStuff", "JSONException when making GraphType", e);
		}
		
		// Send Graph Request to Salesforce.com
		// TODO(mlerner): Put code in SalesforceConn to send graphrequest to APEX
		// Apply an interface to this class that lets sfConn change where the webview points to
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_graph_viewer, menu);
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

}
