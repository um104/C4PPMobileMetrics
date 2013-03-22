package edu.channel4.mm.db.android.activity;

import java.util.List;

import roboguice.activity.RoboActivity;
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
import android.widget.TextView;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;
import edu.channel4.mm.db.android.view.GraphRequestArrayAdapter;

@ContentView(R.layout.activity_audience)
public class AudienceActivity extends RoboActivity {

   @InjectView(R.id.listviewAudienceActivity) private ListView listView;
   @InjectView(R.id.audienceHeader) private TextView textViewAudienceHeader;
   @InjectResource(R.string.audience) private String activityTitle;
   @Inject private SharedPreferences prefs;
   @Inject private List<GraphRequest> graphRequests;
   private GraphRequestArrayAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Set the Activity's titles
      String appLabel = prefs.getString(Keys.APP_LABEL, null);
      setTitle(activityTitle);
      textViewAudienceHeader.setText(appLabel);

      // Set the Adapter to use the array of GraphRequests
      fillGraphRequests();
      adapter = new GraphRequestArrayAdapter(getApplicationContext(),
               graphRequests.toArray(new GraphRequest[graphRequests.size()]));

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
               "DeviceCountry__c", R.drawable.nationality_breakdown));
      graphRequests.add(new CustomGraphRequest("Language Locale", "",
               "LanguageLocale__c", R.drawable.language_locale));
      graphRequests.add(new CustomGraphRequest("Device Type Distribution", "",
               "DeviceType__c", R.drawable.device_type_dist));
      graphRequests.add(new CustomGraphRequest("Device Manufacturer", "",
               "DeviceManufacturer__c", R.drawable.ic_launcher));
      graphRequests.add(new CustomGraphRequest("Device Model", "",
               "DeviceModel__c", R.drawable.ic_launcher));
      graphRequests.add(new CustomGraphRequest("Network Carrier", "",
               "NetworkCarrier__c", R.drawable.ic_launcher));
      graphRequests.add(new CustomGraphRequest("Network Country Code", "",
               "NetworkCountryCode__c", R.drawable.ic_launcher));
      graphRequests.add(new CustomGraphRequest("SDK Compatibility", "",
               "SDKCompatibility__c", R.drawable.ic_launcher));
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
