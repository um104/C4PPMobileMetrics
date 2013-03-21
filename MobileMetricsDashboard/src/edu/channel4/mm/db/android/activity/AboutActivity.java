package edu.channel4.mm.db.android.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.util.BaseArrayAdapter;

@ContentView(R.layout.activity_about)
public class AboutActivity extends RoboActivity {

   @InjectView(R.id.listViewDeveloperNames) private ListView listViewDeveloperNames;
   @InjectResource(R.array.developer_names) private String[] developerNames;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Set the list view's adapter to be our custom array adapter
      listViewDeveloperNames.setAdapter(new AboutArrayAdapter(
               getApplicationContext(), R.layout.cell_about, developerNames));
   }

   private static class AboutArrayAdapter extends BaseArrayAdapter<String> {

      AboutArrayAdapter(Context context, int textViewResourceId,
                        String[] developerNames) {
         super(context, textViewResourceId, developerNames);
      }

      public View getViewEnhanced(String object, View convertedView) {
         // Set the cell's "name" TextView
         TextView developerTextView = (TextView) convertedView
                  .findViewById(R.id.developerName);
         developerTextView.setText(object);

         return convertedView;
      }
   }
}
