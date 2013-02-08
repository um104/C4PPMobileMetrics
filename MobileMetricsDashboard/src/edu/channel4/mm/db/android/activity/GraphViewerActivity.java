package edu.channel4.mm.db.android.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.AppDescription;
import edu.channel4.mm.db.android.model.AttribDescription;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.GraphType;
import edu.channel4.mm.db.android.util.Keys;

public class GraphViewerActivity extends Activity {
	
	//TODO(mlerner): Make this use a WebActivity as its main form of display. See VertProto for example.
	private GraphType graphType;
	private AppDescription appDescription; //TODO(mlerner): Change this to be appId
	private AttribDescription attribDescription;


	@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
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
		attribDescription = intent.getParcelableExtra(Keys.PREFS_NS + Keys.ATTRIB_DESC);
		
		// get WebView object and set settings
        WebView wView = (WebView) findViewById(R.id.webView1);
        wView.setWebViewClient(new WebViewClient());
        wView.getSettings().setJavaScriptEnabled(true);
        
        // construct URL parameters
        Map<String,String> urlParams = new HashMap<String,String>();
        urlParams.put(Keys.GRAPH_TYPE, graphType.name());
        urlParams.put(Keys.ATTRIB_NAME, attribDescription.getAttribName());
        urlParams.put(Keys.EVENT_NAME, attribDescription.getAttribEventName());
        //TODO(mlerner): urlParams.put(Keys.APP_ID, appId);
        
        // get graph URL
        SalesforceConn sfConn = SalesforceConn.getInstance(getApplicationContext());
        String url = sfConn.getGraphViewingURL();

        // point WebView to Graph URL with correct params
        wView.loadUrl(url, urlParams);
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
