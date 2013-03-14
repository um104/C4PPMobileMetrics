package edu.channel4.buttonmash;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.channel4.mobilemetrics.sdk.android.LocalyticsSession;

public class UploadScoreActivity extends Activity {

   private final static String LOCALYTICS_APP_KEY = "2b9b47ca4e9178b076524b4-d8a060da-215f-11e2-5ebd-00ef75f32667";
   private LocalyticsSession localyticsSession;
   private TextView textViewHighScore;
   private EditText editTextName;
   private int timesPressed;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_upload_score);

      timesPressed = getIntent().getIntExtra("timesPressed", 0);

      textViewHighScore = (TextView) findViewById(R.id.textViewHighScore);
      
      
      String highScoreString = "Clicked the button " + timesPressed
               + " times. ";
      if (timesPressed >= 0 && timesPressed < 10) {
         highScoreString += "\n\nYou have much to learn.";
      } else if (timesPressed >= 10 && timesPressed < 20) {
         highScoreString += "\n\nI see your mashing has improved.";
      } else if (timesPressed >= 20 && timesPressed < 30) {
         highScoreString += "\n\nYou have become quite skilled in the art of mashing";
      } else if (timesPressed >= 30 && timesPressed < 40) {
         highScoreString += "\n\nYou are a mashing king!";
      } else if (timesPressed >= 40 && timesPressed < 50) {
         highScoreString += "\n\nIt's mahvel baby!";
      } else if (timesPressed >= 50) { 
         highScoreString += "\n\nStop playing ButtonMash Girum.";
      } 
      
      textViewHighScore.setText(highScoreString);

      editTextName = (EditText) findViewById(R.id.editTextName);

      localyticsSession = new LocalyticsSession(getApplicationContext(),
               LOCALYTICS_APP_KEY);
      localyticsSession.open();
   }

   @Override
   protected void onResume() {
      super.onResume();
      localyticsSession.open();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.activity_upload_score, menu);
      return true;
   }

   @Override
   protected void onPause() {
      super.onPause();
      localyticsSession.close();
   }

   public void uploadScore(View v) {
      LocalyticsSession.MobileMetricsEvent event = localyticsSession
               .getNewEvent("MASHED_THAT_BUTTON");
      event.addAttribute("times_mashed", timesPressed);
      event.addAttribute("name", editTextName.getText().toString());
      event.tagEvent();
      localyticsSession.upload();

      Toast.makeText(getApplicationContext(), "Uploaded score!",
               Toast.LENGTH_SHORT).show();
      
      finish();
   }

}
