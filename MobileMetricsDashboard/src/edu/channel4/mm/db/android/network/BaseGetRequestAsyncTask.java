package edu.channel4.mm.db.android.network;

import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.util.BaseAsyncTask2;
import edu.channel4.mm.db.android.util.Log;

public abstract class BaseGetRequestAsyncTask<ResultT> extends
         BaseAsyncTask2<ResultT> {

   @Inject private RestClientAccess restClientAccess; // singleton
   @Inject protected String responseString;
   private String url = null;
   protected Map<String, String> params = null;

   protected BaseGetRequestAsyncTask(Context context, String url) {
      super(context);
      this.url = url;
   }

   protected BaseGetRequestAsyncTask(Context context, String url,
                                     Map<String, String> params) {
      this(context, url);
      this.params = params;
   }

   @Override
   public ResultT call() throws Exception {
      Log.i("Sending GET request");

      String accessToken = restClientAccess.getAccessToken();
      String instanceUrl = restClientAccess.getInstanceURL().toString();

      if (accessToken == null) {
         throw new Exception(
                  "Error in GetAppList: No access token currently saved");
      }

      Log.d("Access token: " + accessToken);
      Log.d("Instance URL: " + instanceUrl);

      HttpClient client = new DefaultHttpClient();

      // HttpUriRequest getRequest = new HttpGet(String.format(
      // SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl,
      // APPS_URL_SUFFIX));

      String getRequestURI = String.format(
               SalesforceConn.SALESFORCE_BASE_REST_URL, instanceUrl, url);

      if (params != null) {
         getRequestURI += "?";
         for (String key : params.keySet()) {
            // final String parameterURLString = key + "=" + params.get(key) + "&";
            getRequestURI += key + "=" + params.get(key) + "&";
         }
      }

      HttpUriRequest getRequest = new HttpGet(getRequestURI);

      getRequest.setHeader("Authorization", "Bearer " + accessToken);

      String responseString = EntityUtils.toString(client.execute(getRequest)
               .getEntity());

      if (responseString != null) {
         Log.i("Got HTTP result: " + responseString);
      }
      else {
         throw new Exception(
                  "ERROR: GET request receieved null response string.");
      }

      // Save the responseString internally, for inheriting classes to use
      // (parse).
      this.responseString = responseString;

      return null;
   }

   // public String getResponseString() {
   // return responseString;
   // }

   // @Override
   // protected void onSuccess(String result) {
   //
   // }
   //
   // @Override
   // protected void onException(Exception e) {
   // Log.toastE(context, e.getMessage());
   // }
   //
   // @Override
   // protected void onInterrupted(Exception e) {
   // Log.toastE(context, e.getMessage());
   // }
   //
   // @Override
   // protected void onFinally() {
   // }

}
