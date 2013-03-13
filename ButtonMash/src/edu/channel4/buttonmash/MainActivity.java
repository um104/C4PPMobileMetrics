package edu.channel4.buttonmash;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import edu.channel4.mobilemetrics.sdk.android.LocalyticsSession;

public class MainActivity extends Activity {

   private final static String LOCALYTICS_APP_KEY = "2b9b47ca4e9178b076524b4-d8a060da-215f-11e2-5ebd-00ef75f32667";
   private final static int SECONDS = 5;
   private TextView textViewTimer;
   private CountDownTimer countDownTimer;
   private int timesPressed = 0;
   private LocalyticsSession localyticsSession;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      textViewTimer = (TextView) findViewById(R.id.textViewTimer);
      textViewTimer.setText("");

      localyticsSession = new LocalyticsSession(getApplicationContext(),
               LOCALYTICS_APP_KEY);
      localyticsSession.open();
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
      textViewTimer.setText("Clicked the button " + timesPressed + " times!");
      Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT)
               .show();

      LocalyticsSession.MobileMetricsEvent event = localyticsSession
               .getNewEvent("MASHED_THAT_BUTTON");
      event.addAttribute("times_mashed", timesPressed);
      event.tagEvent();
      localyticsSession.upload();
   }

   public void theButton(View v) {
      ++timesPressed;
   }

   @Override
   protected void onPause() {
      super.onPause();
      localyticsSession.close();
   }

}
