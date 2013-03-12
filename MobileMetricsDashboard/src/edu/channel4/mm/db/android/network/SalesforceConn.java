package edu.channel4.mm.db.android.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.model.description.EventNameDescription;
import edu.channel4.mm.db.android.util.BaseAsyncTask;
import edu.channel4.mm.db.android.util.Keys;

public class SalesforceConn {
   static private SalesforceConn instance;
   protected static final String SALESFORCE_BASE_REST_URL = "%s/services/apexrest/C4PPMM/%s/";
   protected static final String APPS_URL_SUFFIX = "channel4_apps";
   protected static final String ATTRIBS_URL_SUFFIX = "channel4_attributes";
   protected static final String EVENTS_URL_SUFFIX = "channel4_events";
   protected static final String GRAPH_VIEW_BASE_URL = "%s/apex/graphView?oauth_token=%s";

   private Context context;
   protected HttpClient client;

   private SalesforceConn(Context context) {
      this.context = context;

      client = new DefaultHttpClient();
   }

   /**
    * Public instantiator for a SalesforceConn object. Ensures no more than one
    * object exists.
    * 
    * @param context
    *           The application-level context.
    * @return A SalesforceConn operating within the given context.
    */
   // TODO(Girum): Remove singletons. We shouldn't statically refer to a
   // Context object, since the Context could potentially be different after a
   // Force Close.
   // http://stackoverflow.com/questions/137975/what-is-so-bad-about-singletons
   // http://blogs.msdn.com/b/scottdensmore/archive/2004/05/25/140827.aspx
   public static SalesforceConn getInstance(Context context) {
      if (instance == null)
         instance = new SalesforceConn(context);
      else
         instance.context = context;
      return instance;
   }

   public String getGraphViewingURL() {
      String accessToken = context.getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.ACCESS_TOKEN, null);
      String instanceUrl = context.getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.INSTANCE_URL, null);

      // TODO(mlerner): Tried this to fix the double login error. May have made
      // it worse. Test.
      try {
         accessToken = URLEncoder.encode(accessToken, "utf-8");
      }
      catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }

      // TODO(mlerner): place accessToken as the final parameter
      String url = String.format(GRAPH_VIEW_BASE_URL, instanceUrl, "h");

      return url;
   }

   /**
    * Call this when you want to update your list of "attributes"! It will
    * update the list of EventDescriptions, which contains the list of
    * AttributeDescriptions.
    */
   public void getEventList() {
      new GetEventListTask(context).execute();
   }

   protected class GetEventListTask extends BaseAsyncTask {
      private String responseString = null;
      private final String TAG = GetEventListTask.class.getSimpleName();

      public GetEventListTask(Context context) {
         super(context);
      }

      @Override
      protected String doInBackground(Void... params) {
         Log.i(TAG, "Sending GET request to get event list");

         String accessToken = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.ACCESS_TOKEN, null);
         String instanceUrl = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.INSTANCE_URL, null);

         if (accessToken == null) {
            Log.e(TAG, "No access token currently saved");
            return null;
         }

         // Put together the HTTP Request to be sent to Salesforce for the
         // Event list
         String appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.APP_LABEL, null);
         
         appLabel = Uri.encode(appLabel);
         
         HttpGet eventRequest = new HttpGet(String.format(
                  SALESFORCE_BASE_REST_URL, instanceUrl, ATTRIBS_URL_SUFFIX)
                           + "?appLabel=" + appLabel);
         // HttpPost eventRequest = new
         // HttpPost(String.format(SALESFORCE_BASE_REST_URL, instanceUrl,
         // ATTRIBS_URL_SUFFIX));
         eventRequest.setHeader("Authorization", "Bearer " + accessToken);

         // Add AppLabel parameter
         //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
         //nameValuePairs.add(new BasicNameValuePair("appLabel", appLabel));

         try {
            //eventRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Get the response string, the Attribute List in JSON form
            responseString = EntityUtils.toString(client.execute(eventRequest)
                     .getEntity());
         }
         catch (ClientProtocolException e) {
            Log.e(TAG, e.getMessage());
         }
         catch (IOException e) {
            Log.e(TAG, e.getMessage());
         }

         Log.d(TAG, "Got JSON result: " + responseString);

         return responseString;
      }

      @Override
      protected void onPostExecute(String result) {
         super.onPostExecute(result);

         if (responseString == null) {
            final String errorMessage = "ERROR: Attempted to parse null Event list.";
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            Log.e(TAG, errorMessage);
            return;
         }

         // Try to parse the resulting JSON
         List<EventDescription> eventList = null;
         try {
            eventList = EventDescription.parseList(responseString);

            TempoDatabase tempoDatabase = TempoDatabase.getInstance();
            tempoDatabase.setEventDescriptions(eventList);
         }
         catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return;
         }
      }
   }

   /**
    * Call this when you want to update your list of "event names"! It will
    * update the list of EventNameDescriptions.
    */
   public void getEventNameList() {
      new GetEventNameListTask(context).execute();
   }

   protected class GetEventNameListTask extends BaseAsyncTask {
      private String responseString = null;
      private final String TAG = GetEventListTask.class.getSimpleName();

      public GetEventNameListTask(Context context) {
         super(context);
      }

      @Override
      protected String doInBackground(Void... params) {
         Log.i(TAG, "Sending GET request to get event list");

         String accessToken = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.ACCESS_TOKEN, null);
         String instanceUrl = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.INSTANCE_URL, null);

         if (accessToken == null) {
            Log.e(TAG, "No access token currently saved");
            return null;
         }

         // Put together the HTTP Request to be sent to Salesforce for the
         // Event list
         HttpPost eventNameRequest = new HttpPost(String.format(
                  SALESFORCE_BASE_REST_URL, instanceUrl, EVENTS_URL_SUFFIX));
         eventNameRequest.setHeader("Authorization", "Bearer " + accessToken);

         // Add AppLabel parameter
         String appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.APP_LABEL, null);
         List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
         nameValuePairs.add(new BasicNameValuePair("appLabel", appLabel));

         try {
            eventNameRequest
                     .setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Get the response string, the Attribute List in JSON form
            responseString = EntityUtils.toString(client.execute(
                     eventNameRequest).getEntity());
         }
         catch (ClientProtocolException e) {
            Log.e(TAG, e.getMessage());
         }
         catch (IOException e) {
            Log.e(TAG, e.getMessage());
         }

         Log.d(TAG, "Got JSON result: " + responseString);

         return responseString;
      }

      @Override
      protected void onPostExecute(String result) {
         super.onPostExecute(result);

         if (responseString == null) {
            final String errorMessage = "ERROR: Attempted to parse null Event list.";
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            Log.e(TAG, errorMessage);
            return;
         }

         // Try to parse the resulting JSON
         List<EventNameDescription> eventNameList = null;
         try {
            eventNameList = EventNameDescription.parseList(responseString);

            TempoDatabase tempoDatabase = TempoDatabase.getInstance();
            tempoDatabase.setEventNameDescriptions(eventNameList);
         }
         catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return;
         }
      }
   }

   /**
    * Gets the list of attributes valid for this particular type of graph TODO:
    * Remove this. It's replaced by getEventList()
    */
   public void getAttribList() {
      new GetAttribListTask(context).execute();
   }

   protected class GetAttribListTask extends BaseAsyncTask {
      private String responseString = null;
      private final String TAG = GetAppListTask.class.getSimpleName();

      public GetAttribListTask(Context context) {
         super(context);
      }

      @Override
      protected String doInBackground(Void... params) {
         Log.i(TAG, "Sending GET request to get app list");

         String accessToken = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.ACCESS_TOKEN, null);
         String instanceUrl = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.INSTANCE_URL, null);

         if (accessToken == null) {
            Log.e(TAG, "No access token currently saved");
            return null;
         }

         // Put together the HTTP Request to be sent to Salesforce for the
         // Attibute list
         HttpPost attribRequest = new HttpPost(String.format(
                  SALESFORCE_BASE_REST_URL, instanceUrl, ATTRIBS_URL_SUFFIX));
         attribRequest.setHeader("Authorization", "Bearer " + accessToken);

         // Add AppLabel parameter
         String appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.APP_LABEL, null);
         List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
         nameValuePairs.add(new BasicNameValuePair("appLabel", appLabel));

         try {
            attribRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Get the response string, the Attribute List in JSON form
            responseString = EntityUtils.toString(client.execute(attribRequest)
                     .getEntity());
         }
         catch (ClientProtocolException e) {
            Log.e(TAG, e.getMessage());
         }
         catch (IOException e) {
            Log.e(TAG, e.getMessage());
         }

         Log.d(TAG, "Got JSON result: " + responseString);

         return responseString;
      }

      @Override
      protected void onPostExecute(String result) {
         super.onPostExecute(result);

         if (responseString == null) {
            final String errorMessage = "ERROR: Attempted to parse null App list.";
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            Log.e(TAG, errorMessage);
            return;
         }

         // Try to parse the resulting JSON
         List<EventDescription> eventList = null;
         try {
            eventList = EventDescription.parseList(responseString);

            TempoDatabase tempoDatabase = TempoDatabase.getInstance();
            tempoDatabase.setEventDescriptions(eventList);
         }
         catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return;
         }
      }
   }

   /**
    * Gets the app list from Salesforce.
    */
   public void getAppList() {
      new GetAppListTask(context).execute();
   }

   protected class GetAppListTask extends BaseAsyncTask {
      private final String TAG = GetAppListTask.class.getSimpleName();
      private String responseString = null;

      public GetAppListTask(Context context) {
         super(context);
      }

      @Override
      protected String doInBackground(Void... params) {
         Log.i(TAG, "Sending GET request to get app list");

         String accessToken = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.ACCESS_TOKEN, null);
         String instanceUrl = context.getSharedPreferences(Keys.PREFS_NS, 0)
                  .getString(Keys.INSTANCE_URL, null);

         if (accessToken == null) {
            Log.e(TAG, "No access token currently saved");
            return null;
         }

         Log.d(TAG, "Access token: " + accessToken);
         Log.d(TAG, "Instance URL: " + instanceUrl);

         HttpUriRequest getRequest = new HttpGet(String.format(
                  SALESFORCE_BASE_REST_URL, instanceUrl, APPS_URL_SUFFIX));
         getRequest.setHeader("Authorization", "Bearer " + accessToken);

         try {
            responseString = EntityUtils.toString(client.execute(getRequest)
                     .getEntity());
         }
         catch (Exception e) {
            Log.e(TAG, e.getMessage());
         }

         Log.d(TAG, "Got JSON result: " + responseString);

         return responseString;
      }

      @Override
      protected void onPostExecute(String result) {
         super.onPostExecute(result);

         if (responseString == null) {
            final String errorMessage = "ERROR: Attempted to parse null App list.";
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            Log.e(TAG, errorMessage);
            return;
         }

         // Try to parse the resulting JSON
         List<AppDescription> appList = null;
         try {
            appList = AppDescription.parseList(responseString);

            TempoDatabase tempoDatabase = TempoDatabase.getInstance();
            tempoDatabase.setAppDescriptions(appList);
         }
         catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return;
         }

      }
   }

}
