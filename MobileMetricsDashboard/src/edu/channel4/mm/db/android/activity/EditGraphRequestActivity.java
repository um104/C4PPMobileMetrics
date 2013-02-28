package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.model.request.HasEventNameParameter;
import edu.channel4.mm.db.android.model.request.HasOverTimeParameter;
import edu.channel4.mm.db.android.util.Keys;

public class EditGraphRequestActivity extends Activity {

   GraphRequest graphRequest;
   
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Accept the GraphRequest through the intent
      graphRequest = getIntent().getParcelableExtra(Keys.GRAPH_REQUEST_EXTRA);
      
      //TODO: turn off all UI elements
      
      // turn on/off UI elements depending on graphRequest's interfaces
      if (graphRequest instanceof HasOverTimeParameter) {
         // TODO: turn on time selecting elements
      }
      if (graphRequest instanceof HasEventNameParameter) {
         // TODO: turn on event selecting elements
      }
   }
   
   public void onDoneButtonClicked(View view) {
      if (validateInputs() == false) {
         //TODO: respond to invalid input
         return;
      }
      
      // Create GraphViewerActivity intent and put URIParams in Intent
      Intent intent = new Intent(getApplicationContext(), GraphViewerActivity.class);
      intent.putExtra(Keys.REQUEST_URL_PARAMETERS, graphRequest.getUrlParameterString(getApplicationContext()));
      
      // Execute the intent
      startActivity(intent);
   }
   
   /**
    * Use this method to do simple validation on user inputs
    * @return
    */
   private boolean validateInputs() {
      return true;
   }

}
