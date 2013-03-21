package edu.channel4.mm.db.android.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.GraphRequestArrayAdapter;
import edu.channel4.mm.db.android.util.Log;

@ContentView(R.layout.activity_custom_graph)
public class CustomGraphActivity extends RoboActivity {

   @InjectView(R.id.listViewCustomGraphActivity) private ListView listView;
   private GraphRequestArrayAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Setup the ListView
      listView.setEmptyView(findViewById(R.id.textViewNoCustomGraphs));
      listView.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view,
                  int position, long id) {

            // TODO: rather than making a new CustomGraphRequest, grab the
            // actual selected one from the adapter

            // Grab the CustomGraphRequest for the selected item.
            GraphRequest graphRequest = new CustomGraphRequest();

            // Construct the correct Intent for the selected
            // CustomGraphRequest
            Intent intent = graphRequest
                     .constructGraphRequestIntent(getApplicationContext());

            // Try to start the activity
            if (intent != null) {
               startActivity(intent);
            }
            else {
               Log.toastD(getApplicationContext(), graphRequest
                        + " not yet implemented.");
            }
         }
      });

      // TODO: Fill this adapter with a list of CustomGraphRequests from TempoDB
      adapter = new GraphRequestArrayAdapter(getApplicationContext(),
               new CustomGraphRequest[] {});
      listView.setAdapter(adapter);
   }

   public void createNewCustomGraph(View v) {
      GraphRequest graphRequest = new CustomGraphRequest();
      Intent intent = graphRequest
               .constructGraphRequestIntent(getApplicationContext());
      startActivity(intent);
   }
}
