package edu.channel4.mm.db.android.network;

import java.util.HashMap;

import org.apache.http.NameValuePair;

import roboguice.RoboGuice;
import android.content.Context;

import com.google.inject.Inject;
import com.google.visualization.datasource.base.TypeMismatchException;

import edu.channel4.mm.db.android.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.graph.Graph;
import edu.channel4.mm.db.android.model.graph.GraphFactory;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

public class GraphRequestAsyncTask extends BaseGetRequestAsyncTask<Graph> {
   private GraphRequest graphRequest;
   private GraphLoadCallback callback;

   @Inject private TempoDatabase tempoDatabase;
   @Inject private RestClientAccess restClientAccess;

   @SuppressWarnings("serial")
   public GraphRequestAsyncTask(Context context, String baseUrl,
                                String accessToken, final String appLabel,
                                final GraphRequest graphRequest,
                                GraphLoadCallback callback) {
      super(context, baseUrl, accessToken, new HashMap<String, String>() {
         {
            put(Keys.APP_LABEL, appLabel);
            for (NameValuePair nameValuePair : graphRequest
                     .getAdditionalUriParameters()) {
               put(nameValuePair.getName(), nameValuePair.getValue());
            }
         }
      });
      this.graphRequest = graphRequest;
      this.callback = callback;

      // Inject the fields of this POJO
      RoboGuice.getInjector(context).injectMembers(this);
   }

   // @Override
   // protected Graph doInBackground(Void... params) {
   // Log.i("Sending GET request to execute graph request");
   //
   // URI uri = graphRequest.getUri(restClientAccess, getContext());
   //
   // Log.d("URI: " + uri);
   //
   // HttpClient client = new DefaultHttpClient();
   // HttpGet getRequest = new HttpGet(uri);
   // getRequest.setHeader("Authorization",
   // "Bearer " + restClientAccess.getAccessToken());
   //
   // try {
   // // Get the response string, the Attribute List in JSON form
   // responseString = EntityUtils.toString(client.execute(getRequest)
   // .getEntity());
   // }
   // catch (ClientProtocolException e) {
   // Log.e(e.getMessage());
   // }
   // catch (IOException e) {
   // Log.e(e.getMessage());
   // }
   //
   // Log.d("Got JSON result: " + responseString);
   //
   // // Try to parse the resulting JSON
   // Graph graph = null;
   //
   // try {
   // graph = GraphFactory.parseGraph(responseString);
   //
   // // Save the parsed graph into the local database.
   // tempoDatabase.setGraph(graph);
   // }
   // catch (JSONException e) {
   // Log.e(e.getMessage());
   // }
   // catch (TypeMismatchException e) {
   // Log.e(e.getMessageToUser());
   // }
   //
   // return graph;
   // }

   //
   // @Override
   // protected void onPostExecute(Graph result) {
   // super.onPostExecute(result);
   //
   // if (result != null) {
   // listener.onGraphLoaded(result);
   // }
   // }

   @Override
   public Graph call() throws Exception {
      // Execute the GET request
      super.call();

      // Try to parse the resulting JSON
      Graph graph = GraphFactory.parseGraph(responseString);

      // Save the parsed graph into the local database
      tempoDatabase.setGraph(graph);

      return graph;
   }

   @Override
   protected void onSuccess(Graph result) {
      if (result != null) {
         callback.onGraphLoaded(result);
      }
   }

   @Override
   protected void onException(Exception e) {
      if (e instanceof TypeMismatchException)
         Log.toastE(context, ((TypeMismatchException) e).getMessageToUser());
      else
         Log.toastE(context, e);
   }

   @Override
   protected String getResourceUrl() {
      return "channel4_handleGraphRequest";
   }
}