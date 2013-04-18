package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.callback.GraphLoadCallback;
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
 */
@ContentView(R.layout.activity_graph_viewer)
public class GraphViewerActivity extends RoboActivity implements
         GraphLoadCallback {

   @InjectExtra(Keys.GRAPH_REQUEST_EXTRA) private GraphRequest graphRequest;
   @Inject private GraphFactory graphFactory;
   private Graph graph = null;

   @InjectView(R.id.spinnerGraphViewer) private Spinner spinner;
   @InjectView(R.id.progressBarGraphViewer) private ProgressBar progressBar;
   @InjectView(R.id.textViewGraphViewerTitle) private TextView textViewTitle;
   @InjectView(R.id.buttonOK) private Button buttonOK;

   @Inject private ArrayList<GraphType> validGraphTypes;
   private ArrayAdapter<GraphType> adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Setup the validGraphTypes Spinner.
      adapter = new ArrayAdapter<GraphType>(getApplicationContext(),
               R.layout.cell_dropdown_item, validGraphTypes);
      spinner.setAdapter(adapter);
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
