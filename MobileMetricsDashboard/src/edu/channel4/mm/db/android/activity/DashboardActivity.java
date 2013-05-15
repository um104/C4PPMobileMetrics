package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.util.BaseArrayAdapter;
import edu.channel4.mm.db.android.util.Keys;

@ContentView(R.layout.activity_dashboard)
public class DashboardActivity extends RoboActivity {

   @InjectView(R.id.listViewDashboard) private ListView listView;
   @InjectView(R.id.dashboardHeader) private TextView textViewDashboardHeader;
   @Inject SharedPreferences prefs;
   private DashboardArrayAdapter adapter;
   protected ArrayList<Subactivity> subactivityDetails;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      // Hide the action bar 
      getActionBar().hide();

      // Load up the SubactivityDetails array
      subactivityDetails = getSubactivityDetails();

      // Set the titles of the activity
      String appLabel = prefs.getString(Keys.APP_LABEL, null);
      textViewDashboardHeader.setText(appLabel);
      setTitle(appLabel);

      // Setup the ListView
      adapter = new DashboardArrayAdapter(getApplicationContext(),
               R.layout.cell_dashboard_gridview_piece, subactivityDetails);
      listView.setAdapter(adapter);
      listView.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View v, int position,
                  long id) {
            startActivity(new Intent(getApplicationContext(),
                     subactivityDetails.get(position).activityClass));
         }
      });
   }

   /**
    * Helper class that aggregates details on the Activities you want to enter.
    * These are presented to the user by the GridViewAdapter
    */
   static class Subactivity {
      private Integer iconID;
      private String displayName;
      private Class<?> activityClass;

      public Subactivity(Integer iconID, String displayName,
                         Class<?> activityClass) {
         this.iconID = iconID;
         this.displayName = displayName;
         this.activityClass = activityClass;
      }
   }

   private ArrayList<Subactivity> getSubactivityDetails() {
      ArrayList<Subactivity> subactivityDetails = new ArrayList<DashboardActivity.Subactivity>();

      subactivityDetails.add(new Subactivity(R.drawable.usage_icon,
               getResources().getString(R.string.usage), UsageActivity.class));

      subactivityDetails.add(new Subactivity(R.drawable.audience_icon,
               getResources().getString(R.string.audience),
               AudienceActivity.class));

      subactivityDetails.add(new Subactivity(R.drawable.ic_launcher,
               getResources().getString(R.string.custom_graphs),
               CustomGraphActivity.class));

      return subactivityDetails;
   }

   class DashboardArrayAdapter extends BaseArrayAdapter<Subactivity> {

      public DashboardArrayAdapter(Context context, int cellLayoutId,
                                   List<Subactivity> arrayList) {
         super(context, cellLayoutId, arrayList);
      }

      @Override
      public View getViewEnhanced(Subactivity object, View convertedView) {

         // Reference and setup the icon
         ImageView icon = (ImageView) convertedView
                  .findViewById(R.id.imageViewDashboardCellIcon);
         icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
         icon.setPadding(8, 8, 8, 8);

         // Set the icon to be the correct icon
         icon.setImageResource(object.iconID);

         // Set the label to be the correct text
         TextView label = (TextView) convertedView
                  .findViewById(R.id.textViewDashboardCellLabel);
         label.setText(object.displayName);

         return convertedView;
      }
   }
}
