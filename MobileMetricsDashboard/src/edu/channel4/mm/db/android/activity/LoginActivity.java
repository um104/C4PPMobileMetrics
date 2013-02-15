package edu.channel4.mm.db.android.activity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.util.Keys;

/**
 * This class is based on the GraphRender activity from a prior prototype
 */
public class LoginActivity extends Activity {
	private static final String TAG = LoginActivity.class.getSimpleName();
	//TODO(mlerner): CLIENT_ID changes based on what ORG is hosting the app! Fix this so that it doesn't use CLIENT_ID!
	private static final String CLIENT_ID = "3MVG9y6x0357HlecylRTsJx8y_qIjGh9Z7CQEA0bTx5xHsmQRBBXZaOldH3._q.NTUYlX1A4JdiewYx5qMvU4";
	private static final String REDIRECT_URI = "mobilemetrics://callback";
	private static final String STATE = "mystate";
	private static final String RESPONSE_TYPE = "token";
	private static final String SALESFORCE_LOGIN_URL = "https://login.salesforce.com/services/oauth2/authorize?response_type="
			+ RESPONSE_TYPE
			+ "&client_id="
			+ CLIENT_ID
			+ "&redirect_uri="
			+ REDIRECT_URI + "&state" + STATE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph_render);

		WebView wView = (WebView) findViewById(R.id.webView1);
		wView.setWebViewClient(new SFLoginWebViewClient());
		wView.getSettings().setJavaScriptEnabled(true);
		wView.loadUrl(SALESFORCE_LOGIN_URL);
	}

	private class SFLoginWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String uriString) {
			Log.v(TAG, "WebViewClient intercepted URL: " + uriString);

			// After intercepting each URL, check to verify that we were passed
			// back the "successful login" callback URI
			if (uriString.contains(REDIRECT_URI)) {

				// Store all cookies received by the WebView
				CookieManager cookieManager = CookieManager.getInstance();
				String cookie = cookieManager.getCookie(uriString);
				String cookie2 = cookieManager.getCookie(SALESFORCE_LOGIN_URL);
				String cookie3 = cookieManager.getCookie("https://login.salesforce.com/services/oauth2/authorize");
				
				// Parse the access token and refresh token from the URI
				List<NameValuePair> uriParams = null;
				try {
					uriParams = URLEncodedUtils.parse(
							new URI(uriString.replace('#', '?')), null);
				} catch (URISyntaxException e) {
					Log.e(TAG, e.getMessage());
				}

				SharedPreferences.Editor editor = getSharedPreferences(
						Keys.PREFS_NS, 0).edit();

				for (NameValuePair pair : uriParams) {
					Log.d(TAG, "Key: " + pair.getName());
					Log.d(TAG, "Value: " + pair.getValue());
					editor.putString(pair.getName(), pair.getValue());
				}

				editor.commit();

				// Close this LoginActivity and open the AppListActivity
				finish();
				startActivity(new Intent(getApplicationContext(),
						AppListActivity.class));
			}

			return super.shouldOverrideUrlLoading(view, uriString);
		}
	}
}
