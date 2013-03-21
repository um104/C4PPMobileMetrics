package edu.channel4.mm.db.android.network;

import java.net.URI;
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

   private String accessToken = null;
   private String uri = null;
   @Inject protected String responseString;

   protected BaseGetRequestAsyncTask(Context context, String baseUri,
                                     String accessToken) {
      super(context);
      this.uri = baseUri + getResourceUrl();
      this.accessToken = accessToken;
   }

   protected BaseGetRequestAsyncTask(Context context, String baseUri,
                                     String accessToken,
                                     Map<String, String> params) {
      this(context, baseUri, accessToken);

      // Attempt to append parameters to the URI
      if (params != null) {
         this.uri += "?";
         for (String key : params.keySet()) {
            this.uri += key + "=" + params.get(key) + "&";
         }
      }
      else {
         Log.e("Params passed in was null. Ignored it.");
      }
   }

   protected abstract String getResourceUrl();

   protected BaseGetRequestAsyncTask(Context context, URI baseUri,
                                     String accessToken) {
      super(context);
      this.accessToken = accessToken;
      this.uri = baseUri.toString();
   }

   @Override
   public ResultT call() throws Exception {
      Log.i("Sending GET request...");

      if (accessToken == null) {
         throw new Exception(
                  "GET request failed: No access token currently saved");
      }

      Log.v("Access token: " + accessToken);

      HttpClient client = new DefaultHttpClient();

      HttpUriRequest getRequest = new HttpGet(uri);
      getRequest.setHeader("Authorization", "Bearer " + accessToken);

      Log.v("Sending GET request with URI: " + uri);
      String responseString = EntityUtils.toString(client.execute(getRequest)
               .getEntity());

      if (responseString != null) {
         Log.i("Got HTTP result: " + responseString);
      }
      else {
         throw new Exception("GET request receieved null response string.");
      }

      // Save the responseString internally, for inheriting classes to use
      // (e.g. most classes will parse this string).
      this.responseString = responseString;

      return null;
   }

}
