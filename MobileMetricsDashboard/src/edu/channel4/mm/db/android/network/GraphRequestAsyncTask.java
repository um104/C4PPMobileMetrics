package edu.channel4.mm.db.android.network;

import java.util.HashMap;

import org.apache.http.NameValuePair;

import roboguice.RoboGuice;
import android.content.Context;

import com.google.inject.Inject;
import com.google.visualization.datasource.base.TypeMismatchException;

import edu.channel4.mm.db.android.database.Database;
import edu.channel4.mm.db.android.model.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.model.graph.Graph;
import edu.channel4.mm.db.android.model.graph.GraphFactory;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

public class GraphRequestAsyncTask extends BaseGetRequestAsyncTask<Graph> {
   private GraphLoadCallback callback;

   @Inject private Database tempoDatabase;
   @Inject private RestClientAccess restClientAccess;

   private GraphRequest graphRequest;

   @SuppressWarnings("serial")
   public GraphRequestAsyncTask(Context context, String baseUrl,
                                String accessToken, final String appId,
                                final GraphRequest graphRequest,
                                GraphLoadCallback callback) {
      super(context, baseUrl, accessToken, new HashMap<String, String>() {
         {
            put(Keys.APP_ID, appId);
            for (NameValuePair nameValuePair : graphRequest
                     .getAdditionalUriParameters()) {
               put(nameValuePair.getName(), nameValuePair.getValue());
            }
         }
      });
      this.callback = callback;
      this.graphRequest = graphRequest;

      // Inject the fields of this POJO
      RoboGuice.getInjector(context).injectMembers(this);
   }

   @Override
   public Graph call() throws Exception {
      // Execute the GET request
      super.call();

      // Try to parse the resulting JSON
      Graph graph = GraphFactory.parseGraph(responseString);
      graph.setTitle(graphRequest.toString());

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