package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.model.graph.Graph;
import edu.channel4.mm.db.android.model.graph.GraphFactory;
import edu.channel4.mm.db.android.model.graph.GraphType;
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
   private Graph graph = null;

   private List<GraphType> validGraphTypes = new ArrayList<GraphType>();
   private Spinner spinner;
   private ArrayAdapter<GraphType> adapter;

   private ProgressBar progressBar;
   private TextView textViewTitle;
   private Button buttonOK;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_graph_viewer);

      // Reference all the Views
      progressBar = (ProgressBar) findViewById(R.id.progressBarGraphViewer);
      textViewTitle = (TextView) findViewById(R.id.textViewGraphViewerTitle);
      buttonOK = (Button) findViewById(R.id.buttonOK);

      // Setup the validGraphTypes Spinner.
      spinner = (Spinner) findViewById(R.id.spinnerGraphViewer);
      adapter = new ArrayAdapter<GraphType>(getApplicationContext(),
               R.layout.cell_dropdown_item, validGraphTypes);
      spinner.setAdapter(adapter);

      // Setup the GraphFactory and pull in the GraphRequest
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
      this.graph = graph;

      // Fill up the spinner
      validGraphTypes.clear();
      validGraphTypes.addAll(graph.getValidGraphTypes());
      adapter.notifyDataSetChanged();

      // Hide the ProgressBar
      progressBar.setVisibility(View.GONE);

      // Show the other Views
      textViewTitle.setVisibility(View.VISIBLE);
      spinner.setVisibility(View.VISIBLE);
      buttonOK.setVisibility(View.VISIBLE);
   }

   public void loadGraph(View v) {
      Log.i("Loading graph: " + graph.getTitle());

      GraphType graphType = adapter.getItem(spinner.getSelectedItemPosition());

      finish();
      startActivity(graph.getIntent(graphType, getApplicationContext()));
   }

}
