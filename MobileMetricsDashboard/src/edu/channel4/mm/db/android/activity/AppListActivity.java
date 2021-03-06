package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.inject.Inject;
import com.salesforce.androidsdk.app.ForceApp;
import com.salesforce.androidsdk.rest.ClientManager.LoginOptions;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.ui.NativeMainActivity;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.callback.AppDescriptionCallback;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.network.RestClientAccess;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.BaseArrayAdapter;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

/**
 * Main activity. For us, this is functions as what the old AppListActivity did,
 * with LoginActivity built in.
 */
@ContentView(R.layout.activity_app_list)
public class AppListActivity extends NativeMainActivity implements
         AppDescriptionCallback {

   @InjectView(R.id.listViewAppList) private ListView listViewAppList;
   @InjectView(R.id.progressBarAppList) private ProgressBar progressBar;
   @InjectView(R.id.appListRoot) View rootView;
   @Inject private SharedPreferences prefs;
   @Inject private SalesforceConn sfConn;
   @Inject private ArrayList<AppDescription> appList;
   @Inject private RestClientAccess restClientAccess;
   protected AppDataArrayAdapater arrayAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setTitle("Select an app");

      // Hide the ActionBar until Salesforce is done loading in their onResume()
      getActionBar().hide();

      // Initialize Adapter
      arrayAdapter = new AppDataArrayAdapater(getApplicationContext(),
               R.layout.cell_app_list);

      // Setup the ListView
      listViewAppList.setAdapter(arrayAdapter);
      listViewAppList.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view,
                  int position, long id) {
            // Save the chosen App Label
            SharedPreferences.Editor editor = prefs.edit();

            AppDescription appDescription = appList.get(position);

            editor.putString(Keys.APP_LABEL, appDescription.getAppName());
            editor.putString(Keys.ID, appDescription.getAppId());
            editor.commit();

            // Start the DashboardActivity
            Intent intent = new Intent(getApplicationContext(),
                     DashboardActivity.class);
            startActivity(intent);
         }
      });
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_app_list, menu);
      return true;
   }

   private class AppDataArrayAdapater extends BaseArrayAdapter<AppDescription> {
      protected AppDataArrayAdapater(Context context, int textViewResourceId) {
         super(context, textViewResourceId, appList);
      }

      @Override
      public View getViewEnhanced(AppDescription appData, View convertedView) {
         // Reference the Views for this cell
         TextView appName = (TextView) convertedView
                  .findViewById(R.id.textViewAppName);
         TextView packageName = (TextView) convertedView
                  .findViewById(R.id.textViewPackageName);

         // Modify their text.
         appName.setText(appData.getAppName());
         packageName.setText(appData.getPackageName());

         return convertedView;
      }
   }

   /**
    * DON'T USE THIS ONRESUME.
    */
   @Override
   public void onResume() {
      super.onResume();

      // Hide everything until we are logged in (until the Salesforce WebView
      // passes)
      // findViewById(R.id.appListRoot).setVisibility(View.INVISIBLE);
      rootView.setVisibility(View.INVISIBLE);
   }

   /**
    * Use this onResume.
    */
   @Override
   public void onResume(RestClient client) {
      // Save a reference to the rest client for use throughout the app
      // RestClientAccess.getInstance().setRestClient(client);
      restClientAccess.setRestClient(client);

      // Show everything now that the Salesforce WebView has passed.
      rootView.setVisibility(View.VISIBLE);

      // Show the ActionBar now that Salesforce is done loading.
      getActionBar().show();

      // Asynchronously retrieve the app list from Salesforce
      getAppList(null);
   }

   @Override
   protected LoginOptions getLoginOptions() {
      LoginOptions loginOptions = new LoginOptions(
               null, // login host is chosen by user through the server picker
               ForceApp.APP.getPasscodeHash(),
               getString(R.string.oauth_callback_url),
               getString(R.string.oauth_client_id), new String[] {"api"});
      return loginOptions;
   }

   public void getAppList(View v) {
      Log.d("Retrieving app list via network");
      sfConn.getAppListViaNetwork(this);
   }

   public boolean showAboutActivity(MenuItem item) {
      startActivity(new Intent(getApplicationContext(), AboutActivity.class));
      return true;
   }

   public boolean logout(MenuItem item) {
      ForceApp.APP.logout(this);
      return true;
   }

   @Override
   public void onAppDescriptionChanged(List<AppDescription> newAppDescriptions) {
      // Hide the ProgressBar
      progressBar.setVisibility(View.GONE);

      // Sanity check, then update the ArrayAdapter and its array
      if (newAppDescriptions != null) {
         appList.clear();
         appList.addAll(newAppDescriptions);
         arrayAdapter.notifyDataSetChanged();
      }
      else {
         Log.toastD(getApplicationContext(), "Null app list...");
      }
   }
}
