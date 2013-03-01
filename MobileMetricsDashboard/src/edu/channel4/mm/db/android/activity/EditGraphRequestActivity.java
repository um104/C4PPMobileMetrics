package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest.TimeInterval;
import edu.channel4.mm.db.android.model.request.HasEventNameParameter;
import edu.channel4.mm.db.android.model.request.HasOverTimeParameter;
import edu.channel4.mm.db.android.util.Keys;

public class EditGraphRequestActivity extends Activity implements OnEventDescriptionChangedListener {

   private GraphRequest graphRequest;
   private ArrayAdapter<GraphRequest.TimeInterval> durationAdapter;
   private ArrayAdapter<EventDescription> eventAdapter;
   private TextView event1View;
   private TextView durationView;
   private Spinner event1Spinner;
   private Spinner durationSpinner;
   private List<EventDescription> eventList;

   @SuppressLint("NewApi")
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_edit_graph_request);
      
      // Show the Up button in the action bar.
      getActionBar().setDisplayHomeAsUpEnabled(true);
      
      // Accept the GraphRequest through the intent
      graphRequest = getIntent().getParcelableExtra(Keys.GRAPH_REQUEST_EXTRA);
      
      // Get all UI elemends
      durationSpinner = (Spinner) findViewById(R.id.DurationSpinner02);
      event1Spinner = (Spinner) findViewById(R.id.spinnerEvent1);
      durationView = (TextView) findViewById(R.id.TextViewDuration);
      event1View = (TextView) findViewById(R.id.textViewEvent1);
      
      //turn off all UI elements
      durationSpinner.setVisibility(View.GONE);
      event1Spinner.setVisibility(View.GONE);
      durationView.setVisibility(View.GONE);
      event1View.setVisibility(View.GONE);
      
      // turn on/off UI elements depending on graphRequest's interfaces
      if (graphRequest instanceof HasOverTimeParameter) {
         // turn on time selecting elements
         durationSpinner.setVisibility(View.VISIBLE);
         durationView.setVisibility(View.VISIBLE);
         
         // fill the spinner with actual values
         durationAdapter = new ArrayAdapter<GraphRequest.TimeInterval>(getApplicationContext(), 
                  android.R.layout.simple_spinner_item, GraphRequest.TimeInterval.values());
         
         durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         durationSpinner.setAdapter(durationAdapter);
      }
      if (graphRequest instanceof HasEventNameParameter) {
         // turn on event selecting elements
         event1Spinner.setVisibility(View.VISIBLE);
         event1View.setVisibility(View.VISIBLE);
         
         eventList = new ArrayList<EventDescription>();
         
         // fill the event spinner with actual values
         eventAdapter = new ArrayAdapter<EventDescription>(getApplicationContext(),
                  android.R.layout.simple_spinner_item, eventList);
         
         // TODO: update the values within eventList. Use TempoDatabase, SalesforceConn. 
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.activity_edit_graph_request, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
      }
      return super.onOptionsItemSelected(item);
   }

   public void onDoneButtonClicked(View view) {
      if (validateInputs() == false) {
         // respond to invalid input
         Toast.makeText(getApplicationContext(),
                  R.string.please_enter_valid_inputs_, Toast.LENGTH_SHORT)
                  .show();
         return;
      }

      // fill in the graphRequest based on its interfaces
      if (graphRequest instanceof HasOverTimeParameter) {
         // retrieve selected time from durationspinner, put in graphrequest
         int selectedItemPosition = durationSpinner.getSelectedItemPosition();
         TimeInterval timeInterval = durationAdapter.getItem(selectedItemPosition);
         ((HasOverTimeParameter)graphRequest).setTimeDuration(timeInterval.name());
      }
      if (graphRequest instanceof HasEventNameParameter) {
         // retrieve selected event from eventspinner, put in graphrequest
         int selectedItemPosition = event1Spinner.getSelectedItemPosition();
         EventDescription eventDescription = eventAdapter.getItem(selectedItemPosition);
         ((HasEventNameParameter)graphRequest).setEventName(eventDescription.getName());
      }

      // Create GraphViewerActivity intent and put URIParams in Intent
      Intent intent = new Intent(getApplicationContext(),
               GraphViewerActivity.class);
      intent.putExtra(Keys.REQUEST_URL_PARAMETERS,
               graphRequest.getUrlParameterString(getApplicationContext()));

      // Execute the intent
      startActivity(intent);
   }

   /**
    * Use this method to do simple validation on user inputs
    * 
    * @return
    */
   private boolean validateInputs() {
      //TODO: either actually do validation of input elements, or remove this method
      return true;
   }

   @Override
   public void onAppDescriptionChanged(List<EventDescription> newEventDescriptions) {
      eventList.clear();
      eventList.addAll(newEventDescriptions);
      eventAdapter.notifyDataSetChanged();
   }

}
