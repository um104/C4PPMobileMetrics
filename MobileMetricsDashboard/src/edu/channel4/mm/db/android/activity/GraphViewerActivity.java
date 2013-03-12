package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.os.Bundle;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.model.graph.Graph;
import edu.channel4.mm.db.android.model.graph.GraphFactory;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

/**
 * This Activity is now a glorified loading screen.
 * 
 * @author girum
 * 
 */
public class GraphViewerActivity extends Activity implements GraphLoadCallback {

   private GraphFactory graphFactory = null;
   private GraphRequest graphRequest = null;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_graph_viewer);

      graphFactory = new GraphFactory(getApplicationContext());
      graphRequest = getIntent().getParcelableExtra(Keys.GRAPH_REQUEST_EXTRA);
   }

   @Override
   protected void onResume() {
      super.onResume();
      graphFactory.getGraph(graphRequest, this);
   }

   @Override
   public void onGraphLoaded(Graph graph) {
      Log.i("Graph loaded: " + graph.getTitle());

      finish();
      startActivity(graph.getRandomIntent(getApplicationContext()));
   }
}
