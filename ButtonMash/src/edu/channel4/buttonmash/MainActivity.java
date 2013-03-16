package edu.channel4.buttonmash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

   public final static int SECONDS = 5;
   private TextView textViewTimer;
   private CountDownTimer countDownTimer;
   private int timesPressed = 0;
   private Button buttonStartMashing;
   private Button buttonTheButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      textViewTimer = (TextView) findViewById(R.id.textViewTimer);
      textViewTimer.setText("");

      buttonStartMashing = (Button) findViewById(R.id.buttonStartMashing);
      buttonTheButton = (Button) findViewById(R.id.buttonButton);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
   }

   @Override
   protected void onResume() {
      super.onResume();

      buttonStartMashing.setEnabled(true);
      buttonStartMashing.setText("Start mashing!");
      buttonTheButton.setEnabled(false);
   }

   public void startMashing(View v) {
      timesPressed = 0;
      buttonStartMashing.setEnabled(false);
      buttonStartMashing.setText("Mash as fast as you can!");

      final int TOTAL_TIME = SECONDS * 1000 + 2000;

      countDownTimer = new CountDownTimer(TOTAL_TIME, 100){

         @Override
         public void onTick(long millisUntilFinished) {

            if (millisUntilFinished < TOTAL_TIME
                     && millisUntilFinished >= TOTAL_TIME - 1000) {
               textViewTimer.setText("Ready...");
            }
            else if (millisUntilFinished < TOTAL_TIME - 1000
                     && millisUntilFinished >= TOTAL_TIME - 2000) {
               textViewTimer.setText("Set...");
            }
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

   private void timeUp() {
      textViewTimer.setText("");

      Intent intent = new Intent(getApplicationContext(),
               UploadScoreActivity.class);

      intent.putExtra("timesPressed", timesPressed);

      startActivity(intent);
   }

   public void theButton(View v) {
      ++timesPressed;
   }

}
