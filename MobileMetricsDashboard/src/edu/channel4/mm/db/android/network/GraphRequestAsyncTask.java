package edu.channel4.mm.db.android.network;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;

import com.google.visualization.datasource.base.TypeMismatchException;

import edu.channel4.mm.db.android.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.graph.Graph;
import edu.channel4.mm.db.android.model.graph.GraphFactory;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.BaseAsyncTask;
import edu.channel4.mm.db.android.util.Log;

public class GraphRequestAsyncTask extends BaseAsyncTask<Void, Void, Graph> {
   public static final String GRAPH_REQUEST_URL_SUFFIX = "channel4_handleGraphRequest";
   private String responseString = null;
   private GraphRequest graphRequest;
   private GraphLoadCallback listener;

   public GraphRequestAsyncTask(Context context, GraphRequest graphRequest,
                                GraphLoadCallback listener) {
      super(context);
      this.graphRequest = graphRequest;
      this.listener = listener;
   }

   @Override
   protected Graph doInBackground(Void... params) {
      Log.i("Sending GET request to execute graph request");

      RestClientAccess restClientManager = RestClientAccess.getInstance();

      URI uri = graphRequest.getUri(restClientManager, getContext());

      Log.d("URI: " + uri);

      HttpClient client = new DefaultHttpClient();
      HttpGet getRequest = new HttpGet(uri);
      getRequest.setHeader("Authorization",
               "Bearer " + restClientManager.getAccessToken());

      try {
         // Get the response string, the Attribute List in JSON form
         responseString = EntityUtils.toString(client.execute(getRequest)
                  .getEntity());
      }
      catch (ClientProtocolException e) {
         Log.e(e.getMessage());
      }
      catch (IOException e) {
         Log.e(e.getMessage());
      }

      Log.d("Got JSON result: " + responseString);

      // Try to parse the resulting JSON
      Graph graph = null;

      try {
         graph = GraphFactory.parseGraph(responseString);

         // Save the parsed graph into the local database.
         TempoDatabase tempoDatabase = TempoDatabase.getInstance();
         tempoDatabase.setGraph(graph);
      }
      catch (JSONException e) {
         Log.e(e.getMessage());
      }
      catch (TypeMismatchException e) {
         Log.e(e.getMessageToUser());
      }

      return graph;
   }

   @Override
   protected void onPostExecute(Graph result) {
      super.onPostExecute(result);

      if (result != null) {
         listener.onGraphLoaded(result);
      }
   }
}