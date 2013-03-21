package edu.channel4.buttonmash.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

   /**
    * The number of seconds you want to mash for.
    */
   public final static int SECONDS = 5;

   /**
    * Time spent for all of the following actions:
    * 
    * 1.) Saying "ready..." + 2.) Saying "set..." + 3.) Actually mashing The
    * Button
    * 
    * in milliseconds.
    */
   private final static int TOTAL_TIMER_TIME = SECONDS * 1000 + 2000;

   /**
    * Main counter for The Button.
    */
   private int timesPressed = 0;

   @InjectView(R.id.textViewTimer) private TextView textViewTimer;
   @InjectView(R.id.buttonStartMashing) private Button buttonStartMashing;
   @InjectView(R.id.buttonTheButton) private Button buttonTheButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      textViewTimer.setText("");
   }

   @Override
   protected void onResume() {
      super.onResume();

      // Enable the "Start mashing" button
      buttonStartMashing.setEnabled(true);
      buttonStartMashing.setText("Start mashing!");

      // Disable The Button
      buttonTheButton.setEnabled(false);
   }

   public void startMashing(View v) {
      // Reset the counter
      timesPressed = 0;

      // Disable the "Start mashing" button
      buttonStartMashing.setEnabled(false);
      buttonStartMashing.setText("Mash as fast as you can!");

      CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIMER_TIME, 100) {
         @Override
         public void onTick(long millisUntilFinished) {

            // Ready...
            if (millisUntilFinished < TOTAL_TIMER_TIME
                     && millisUntilFinished >= TOTAL_TIMER_TIME - 1000) {
               textViewTimer.setText("Ready...");
            }
            // Set...
            else if (millisUntilFinished < TOTAL_TIMER_TIME - 1000
                     && millisUntilFinished >= TOTAL_TIMER_TIME - 2000) {
               textViewTimer.setText("Set...");
            }
            // GO!
            else if (millisUntilFinished < SECONDS * 1000) {
               buttonTheButton.setEnabled(true);
               textViewTimer.setText(String
                        .valueOf(millisUntilFinished / 1000.0));
            }
         }

         @Override
         public void onFinish() {
            timeUp();
         }
      };

      countDownTimer.start();
   }

   /**
    * Starts the {@link UploadScoreActivity} to upload the score to Channel 4++
    * Mobile Metrics.
    */
   private void timeUp() {
      // Clear out the text for the timer (it shows garbage milliseconds if you
      // don't do this)
      textViewTimer.setText("");

      // Disable The Button so that it doesn't count any additional presses
      buttonTheButton.setEnabled(false);

      Intent intent = new Intent(getApplicationContext(),
               UploadScoreActivity.class);
      intent.putExtra("timesPressed", timesPressed);

      startActivity(intent);
   }

   public void pressedTheButton(View v) {
      // The Button is only setEnabled(true) during the GO! stage.
      // So we don't have to worry about falsely incrementing the counter.
      ++timesPressed;
   }

}
