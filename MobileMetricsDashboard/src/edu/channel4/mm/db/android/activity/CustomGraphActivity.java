package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.database.Database;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;
import edu.channel4.mm.db.android.view.GraphRequestArrayAdapter;

@ContentView(R.layout.activity_custom_graph)
public class CustomGraphActivity extends RoboActivity {

   @InjectView(R.id.listViewCustomGraphActivity) private ListView listView;
   @Inject private Database database; // singleton
   @Inject private SharedPreferences prefs;
   private GraphRequestArrayAdapter adapter = null;
   private List<GraphRequest> customGraphRequests = new ArrayList<GraphRequest>();

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
            GraphRequest graphRequest = customGraphRequests.get(position);

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
               customGraphRequests);
      listView.setAdapter(adapter);
   }

   @Override
   protected void onResume() {
      super.onResume();

      // Re-fetch the saved CustomGraphRequests for this AppId from SQLite.
      String appId = prefs.getString(Keys.ID, null);
      customGraphRequests.clear();
      customGraphRequests.addAll(database.getCustomGraphRequests(appId));

      adapter.notifyDataSetChanged();
   }

   public void createNewCustomGraph(View v) {
      GraphRequest graphRequest = new CustomGraphRequest();
      Intent intent = graphRequest
               .constructGraphRequestIntent(getApplicationContext());
      startActivity(intent);
   }
}
