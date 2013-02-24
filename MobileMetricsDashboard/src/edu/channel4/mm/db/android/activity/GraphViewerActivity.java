package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Logger;

public class GraphViewerActivity extends Activity {

	private WebView webView;
	private String requestUrl;
	private GraphRequest graphRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph_viewer);

		// Retrieve the GraphRequest from the Intent
		graphRequest = (GraphRequest) getIntent().getExtras().getSerializable(
				Keys.GRAPH_REQUEST_TYPE);
		
		// TODO(mlerner): Uncomment the following once graph requests put the URL in the Intent they create.
		// requestUrl = getIntent().getStringExtra(Keys.REQUEST_URL);

		// Setup the WebView
		webView = (WebView) findViewById(R.id.webViewGraphViewer);
		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient() {
			public boolean onConsoleMessage(ConsoleMessage cm) {
				Log.d("MyApplication",
						cm.message() + " -- From line " + cm.lineNumber()
								+ " of " + cm.sourceId());
				return true;
			}
		});

		Logger.i("Sending graphRequest: " + graphRequest);
	}

	@Override
	protected void onResume() {
		super.onResume();

		sendGraphRequest();
	}

	private void sendGraphRequest() {
		// get graph URL
		SalesforceConn sfConn = SalesforceConn.getInstance(getApplicationContext());
		String url = sfConn.getGraphViewingURL();

		// construct URL parameters
		url = addParamsToUrl(url);
		
		// TODO(mlerner): Uncomment the following once graph requests put the URL in the Intent they create.
		// url = requestUrl;

		// point WebView to Graph URL with correct params
		webView.loadUrl(url);
	}

	protected String addParamsToUrl(String url) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		//Should be "custom" "SessionOverTime", "EventOverTime", etc.
		params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, graphRequest.getRestRequestType()));
		
		//TOOD(mlerner): Get the URL parameters from the GraphRequest and add those to the url
		
		
		// TODO FIXME: App Name is hardcoded right now
		params.add(new BasicNameValuePair(Keys.APP_NAME, "a04E00000034dlH"));

		String paramString = URLEncodedUtils.format(params, "utf-8");

		url += paramString;
		return url;
	}
}

//
// private GraphType graphType;
// private String appId;
// private AttributeDescription attribDescription;
//
// @SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_graph_viewer);
//
// // Make sure we're running on Honeycomb or higher to use ActionBar APIs
// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
// // Show the Up button in the action bar.
// getActionBar().setDisplayHomeAsUpEnabled(true);
// }
//
// // Get intent and accompanying data
// Intent intent = getIntent();
// appId = intent.getStringExtra(Keys.PREFS_NS + Keys.APP_ID);
// graphType = (GraphType) intent.getSerializableExtra(Keys.PREFS_NS
// + Keys.GRAPH_TYPE);
// attribDescription = intent.getParcelableExtra(Keys.PREFS_NS
// + Keys.ATTRIB_DESC);
//
// // get WebView object and set settings
// WebView wView = (WebView) findViewById(R.id.webView1);
// wView.setWebViewClient(new WebViewClient());
// wView.getSettings().setJavaScriptEnabled(true);
//
// wView.setWebChromeClient(new WebChromeClient() {
// public boolean onConsoleMessage(ConsoleMessage cm) {
// Log.d("MyApplication",
// cm.message() + " -- From line " + cm.lineNumber()
// + " of " + cm.sourceId());
// return true;
// }
// });
//
//
// // get graph URL
// SalesforceConn sfConn = SalesforceConn
// .getInstance(getApplicationContext());
// String url = sfConn.getGraphViewingURL();
//
// // construct URL parameters
// url = addParamsToUrl(url);
//
// // point WebView to Graph URL with correct params
// wView.loadUrl(url);
// }
//
// protected String addParamsToUrl(String url) {
// if (!url.endsWith("?"))
// url += "?";
//
// List<NameValuePair> params = new ArrayList<NameValuePair>();
//
// params.add(new BasicNameValuePair(Keys.GRAPH_TYPE, graphType.name()));
// params.add(new BasicNameValuePair(Keys.ATTRIB_NAME, attribDescription
// .getAttribName()));
// params.add(new BasicNameValuePair(Keys.EVENT_NAME, attribDescription
// .getAttribEventName()));
// params.add(new BasicNameValuePair(Keys.APP_ID, appId));
// // TODO(mlerner): urlParams.put(Keys.START_TIME, startTime);
// // TODO(mlerner): urlParams.put(Keys.STOP_TIME, stopTime);
//
// String paramString = URLEncodedUtils.format(params, "utf-8");
//
// url += paramString;
// return url;
// }
//
//
// }
