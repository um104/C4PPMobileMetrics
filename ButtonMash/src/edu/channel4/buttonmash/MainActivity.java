package edu.channel4.buttonmash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

   private final static int SECONDS = 5;
   private TextView textViewTimer;
   private CountDownTimer countDownTimer;
   private int timesPressed = 0;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      textViewTimer = (TextView) findViewById(R.id.textViewTimer);
      textViewTimer.setText("");

   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
   }

   public void startMashing(View v) {
      timesPressed = 0;

      countDownTimer = new CountDownTimer(SECONDS * 1000, 100){

         @Override
         public void onTick(long millisUntilFinished) {
            textViewTimer.setText(String.valueOf(millisUntilFinished / 1000.0));
         }

         @Override
         public void onFinish() {
            timeUp();
         }
      };

      countDownTimer.start();
   }

   private void timeUp() {
      Intent intent = new Intent(getApplicationContext(),
               UploadScoreActivity.class);

      intent.putExtra("timesPressed", timesPressed);

      startActivity(intent);
   }

   public void theButton(View v) {
      ++timesPressed;
   }

}
