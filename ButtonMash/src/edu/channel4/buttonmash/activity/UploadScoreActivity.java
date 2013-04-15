package edu.channel4.buttonmash.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.channel4.mobilemetrics.sdk.android.MMSession;

@ContentView(R.layout.activity_upload_score)
public final class UploadScoreActivity extends RoboActivity {

   private final static String LOCALYTICS_APP_KEY = "2b9b47ca4e9178b076524b4-d8a060da-215f-11e2-5ebd-00ef75f32667";
   private MMSession localyticsSession;
   private int timesPressed;

   @InjectView(R.id.textViewHighScore) private TextView textViewHighScore;
   @InjectView(R.id.editTextName) private EditText editTextName;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      localyticsSession = new MMSession(getApplicationContext(),
               LOCALYTICS_APP_KEY);
      localyticsSession.open();

      timesPressed = getIntent().getIntExtra("timesPressed", 0);

      textViewHighScore.setText(getHighScoreString(timesPressed));
   }

   @Override
   protected void onResume() {
      super.onResume();
      localyticsSession.open();
   }

   @Override
   protected void onPause() {
      super.onPause();
      localyticsSession.close();
   }

   public void uploadScore(View v) {

      // Empty name sanity check
      if (editTextName.getText().toString().trim().equals("")) {
         Toast.makeText(getApplicationContext(), "Please enter your name.",
                  Toast.LENGTH_SHORT).show();
         return;
      }

      MMSession.MMEvent event = localyticsSession
               .getNewEvent("MASHED_THAT_BUTTON");
      event.addAttribute("times_mashed", timesPressed);
      event.addAttribute("name", editTextName.getText().toString());
      event.tagEvent();
      localyticsSession.upload();

      Toast.makeText(getApplicationContext(), "Uploaded score!",
               Toast.LENGTH_SHORT).show();

      finish();
   }

   private static String getHighScoreString(int timesPressed) {
      String highScoreString = "Clicked the button " + timesPressed
               + " times. ";
      if (timesPressed >= 0 && timesPressed < 10) {
         highScoreString += "\n\nYou have much to learn.";
      }
      else if (timesPressed >= 10 && timesPressed < 20) {
         highScoreString += "\n\nI see your mashing has improved.";
      }
      else if (timesPressed >= 20 && timesPressed < 30) {
         highScoreString += "\n\nYou have become quite skilled in the art of mashing";
      }
      else if (timesPressed >= 30 && timesPressed < 40) {
         highScoreString += "\n\nYou are a mashing king!";
      }
      else if (timesPressed >= 40 && timesPressed < 50) {
         highScoreString += "\n\nIt's mahvel baby!";
      }
      else if (timesPressed >= 50) {
         highScoreString += "\n\nStop playing ButtonMash Girum.";
      }

      return highScoreString;
   }

}