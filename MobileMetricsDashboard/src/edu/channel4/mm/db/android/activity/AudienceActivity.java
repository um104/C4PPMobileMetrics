package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;

import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
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
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.BaseActivity;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;
import edu.channel4.mm.db.android.view.GraphRequestArrayAdapter;

@ContentView(R.layout.activity_audience)
public class AudienceActivity extends BaseActivity {

   @InjectView(R.id.listviewAudienceActivity) private ListView listView;
   @InjectResource(R.string.audience) private String activityTitle;
   @Inject private SharedPreferences prefs;
   @Inject private ArrayList<GraphRequest> graphRequests;
   private GraphRequestArrayAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Enable the "Up" button.
      getActionBar().setDisplayHomeAsUpEnabled(true);

      // Set the Activity's titles
      String appLabel = prefs.getString(Keys.APP_LABEL, null);
      setTitle(appLabel + ": " + activityTitle);

      // Set the Adapter to use the array of GraphRequests
      if (graphRequests.isEmpty()) {
         fillGraphRequests();
      }
      adapter = new GraphRequestArrayAdapter(getApplicationContext(),
               graphRequests.toArray(new GraphRequest[0]));

      // Configure the ListView to use the Adapter
      listView.setAdapter(adapter);
      listView.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view,
                  int position, long id) {
            startGraphRequestActivity(graphRequests.get(position));
         }
      });
   }

   // Construct the array of GraphRequests
   private void fillGraphRequests() {
      graphRequests.add(new CustomGraphRequest("Nationality Breakdown", "",
               "DeviceCountry", R.drawable.ic_logo));
      graphRequests.add(new CustomGraphRequest("Language Locale", "",
               "LanguageLocale", R.drawable.ic_logo));
      graphRequests.add(new CustomGraphRequest("Device Manufacturer", "",
               "DeviceManufacturer", R.drawable.ic_logo));
      graphRequests.add(new CustomGraphRequest("Device Model", "",
               "DeviceModel", R.drawable.ic_logo));
      graphRequests.add(new CustomGraphRequest("Network Carrier", "",
               "NetworkCarrier", R.drawable.ic_logo));
      graphRequests.add(new CustomGraphRequest("Network Country Code", "",
               "NetworkCountryCode", R.drawable.ic_logo));
      graphRequests.add(new CustomGraphRequest("SDK Compatibility", "",
               "SDKCompatibility", R.drawable.ic_logo));
   }

   private void startGraphRequestActivity(GraphRequest graphRequest) {
      // Construct the correct Intent
      Intent intent = graphRequest
               .constructGraphRequestIntent(getApplicationContext());

      if (intent != null) {
         startActivity(intent);
      }
      else {
         Log.toastE(getApplicationContext(), graphRequest.toString()
                  + " not yet implemented.");
      }
   }
}
