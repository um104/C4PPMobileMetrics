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

import com.google.inject.Inject;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.GraphRequestArrayAdapter;
import edu.channel4.mm.db.android.util.Log;

@ContentView(R.layout.activity_custom_graph)
public class CustomGraphActivity extends RoboActivity {

   @Inject TempoDatabase tempoDatabase; // singleton
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

            // Grab the CustomGraphRequest for the selected item.
            GraphRequest graphRequest = tempoDatabase.getCustomGraphRequests().get(position);

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

      // Fill this adapter with a list of CustomGraphRequests from TempoDB
      adapter = new GraphRequestArrayAdapter(getApplicationContext(),
               tempoDatabase.getCustomGraphRequests());
      listView.setAdapter(adapter);
   }
   
   @Override
   protected void onResume() {
      super.onResume();
      adapter.notifyDataSetChanged();
   }

   public void createNewCustomGraph(View v) {
      GraphRequest graphRequest = new CustomGraphRequest();
      Intent intent = graphRequest
               .constructGraphRequestIntent(getApplicationContext());
      startActivity(intent);
   }
}
