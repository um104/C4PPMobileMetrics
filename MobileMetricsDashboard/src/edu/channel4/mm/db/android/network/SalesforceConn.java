package edu.channel4.mm.db.android.network;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import edu.channel4.mm.db.android.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.callback.AttributeDescriptionCallback;
import edu.channel4.mm.db.android.callback.EventDescriptionCallback;
import edu.channel4.mm.db.android.callback.EventNameDescriptionCallback;
import edu.channel4.mm.db.android.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.model.request.GraphRequest;

public class SalesforceConn {
	public static final String SALESFORCE_BASE_REST_URL = "%s/services/apexrest/C4PPMM/%s";

	public static final String EVENTS_URL_SUFFIX = "channel4_events";

	private Context context;
	protected HttpClient client;

	/**
	 * Fixed(Girum): Removed singleton. We shouldn't statically refer to a
	 * Context object, since the Context could potentially be different after a
	 * Force Close. This actually broke for us when we tried to pull the
	 * access_token from SharedPrefs (the context was bad)
	 * 
	 * http://stackoverflow.com/questions/137975/what-is-so-bad-about-singletons
	 * http://blogs.msdn.com/b/scottdensmore/archive/2004/05/25/140827.aspx
	 * 
	 */
	public SalesforceConn(Context context) {
		this.context = context;

		client = new DefaultHttpClient();
	}

	/**
	 * Gets the list of attributes valid for this particular type of graph.
	 * Don't call this method directly. There will be a factory for this later?
	 */
	public void getAttribListViaNetwork(AttributeDescriptionCallback callback) {
		new GetAttribListAsyncTask(context, client, callback).execute();
	}

   public String getGraphViewingURL() {
      //TODO FIXME PLEASE KILL ME NOW
      /*String accessToken = context.getSharedPreferences(Keys.PREFS_NS, 0)
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

      return url;*/
      return null;
   }

   /**
    * Call this when you want to update your list of "attributes"! It will
    * update the list of EventDescriptions, which contains the list of
    * AttributeDescriptions.
    */
   public void getEventListViaNetwork(EventDescriptionCallback callback) {
      new GetEventListAsyncTask(context, client, callback).execute();
   }

   /**
    * Call this when you want to update your list of "event names"! It will
    * update the list of EventNameDescriptions.
    */
   public void getEventNameListViaNetwork(EventNameDescriptionCallback callback) {
      new GetEventNameListAsyncTask(context, client, callback).execute();
   }

   /**
    * Gets the list of attributes valid for this particular type of graph TODO:
    * Remove this. It's replaced by getEventList()
    */
   /*
   public void getAttribList() {
      new GetAttribListTask(context).execute();
   }
   */
/*
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
   */

	/**
	 * Gets the app list from Salesforce. Don't call this method directly.
	 * Instead, call the GraphFactory.
	 */
	public void getGraphViaNetwork(GraphRequest graphRequest,
			GraphLoadCallback callback) {
		new GraphRequestAsyncTask(context, client, graphRequest, callback).execute();
	}

	public void getAppListViaNetwork(AppDescriptionCallback callback) {
	   new GetAppListAsyncTask(context, client, callback).execute();
	}
}
