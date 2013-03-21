package edu.channel4.mm.db.android.network;

import java.net.URI;

import com.google.inject.Singleton;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.ui.NativeMainActivity;

import edu.channel4.mm.db.android.activity.AppListActivity;

/**
 * I suffer from Singletonitis:
 * http://www.antonioshome.net/blog/2006/20060906-1.php
 * 
 * But we need a way to statically persist the {@link RestClient} that the
 * Salesforce Native Android SDK gives us the once during
 * {@link AppListActivity}'s second, overridden onResume() method.
 * 
 * @author girum
 */
@Singleton
public class RestClientAccess {

	/**
	 * According to the <a
	 * href="http://en.wikipedia.org/wiki/Law_of_Demeter">Law of Demeter</a>, we
	 * shouldn't expose access to the actual {@link RestClient}. Instead, we
	 * should expose the fields from it that we need.
	 */
	private RestClient restClient = null;
	
	/**
	 * Use this method exactly once, when Salesforce gives you it in their
	 * {@link NativeMainActivity}.
	 * 
	 * @param restClient
	 */
	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public String getAccessToken() {
		return restClient.getAuthToken();
	}

	public String getRefreshToken() {
		return restClient.getRefreshToken();
	}

	public URI getInstanceURL() {
		return restClient.getClientInfo().instanceUrl;
	}
}
