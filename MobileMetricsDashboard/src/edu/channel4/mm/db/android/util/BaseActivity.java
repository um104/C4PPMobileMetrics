package edu.channel4.mm.db.android.util;

import roboguice.activity.RoboActivity;
import android.view.MenuItem;

public class BaseActivity extends RoboActivity {

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case android.R.id.home:
            onBackPressed();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

}
