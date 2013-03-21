package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.callback.EventDescriptionCallback;
import edu.channel4.mm.db.android.callback.EventNameDescriptionCallback;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AttributeDescription;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.model.description.EventNameDescription;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest.TimeInterval;
import edu.channel4.mm.db.android.model.request.HasAttributeParameter;
import edu.channel4.mm.db.android.model.request.HasEventNameParameter;
import edu.channel4.mm.db.android.model.request.HasOverTimeParameter;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.Keys;

@ContentView(R.layout.activity_edit_graph_request)
public class EditGraphRequestActivity extends RoboActivity implements
         EventDescriptionCallback, EventNameDescriptionCallback {

   @InjectExtra(Keys.GRAPH_REQUEST_EXTRA) private GraphRequest graphRequest;
   @Inject private SalesforceConn sfConn;
   @Inject private TempoDatabase tempoDatabase; // singleton

   private ArrayAdapter<GraphRequest.TimeInterval> durationAdapter;
   private ArrayAdapter<EventNameDescription> eventNameAdapter;
   private ArrayAdapter<EventDescription> eventAdapter;
   private ArrayAdapter<EventDescription> eventAttributeAdapter;
   private List<ArrayAdapter<EventDescription>> eventAttributeAdapters;
   private ArrayAdapter<AttributeDescription> attributeAdapter;

   @InjectView(R.id.spinnerEvent1) private Spinner event1Spinner;
   @InjectView(R.id.spinnerAttribute1) private Spinner attribute1Spinner;
   @InjectView(R.id.spinnerDuration) private Spinner durationSpinner;
   @InjectView(R.id.event1Group) private View event1Group;
   @InjectView(R.id.attribute1Group) private View attribute1Group;
   @InjectView(R.id.durationGroup) private View durationGroup;

   @Inject private ArrayList<EventDescription> eventList;
   @Inject private ArrayList<EventNameDescription> eventNameList;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Set the activity's title
      setTitle(graphRequest.toString());

      // turn off all UI elements
      event1Group.setVisibility(View.GONE);
      attribute1Group.setVisibility(View.GONE);
      durationGroup.setVisibility(View.GONE);

      // turn on UI elements depending on graphRequest's interfaces
      if (graphRequest instanceof HasOverTimeParameter) {
         // turn on time selecting elements
         durationGroup.setVisibility(View.VISIBLE);

         // fill the adapter with different possible time values
         durationAdapter = new ArrayAdapter<GraphRequest.TimeInterval>(
                  getApplicationContext(), R.layout.cell_dropdown_item,
                  GraphRequest.TimeInterval.values());

         // TODO: make a new layout that works better for the dropdown part
         // set the layout for displaying options
         /*
          * durationAdapter.setDropDownViewResource(android.R.layout.
          * simple_spinner_dropdown_item);
          */
         durationAdapter.setDropDownViewResource(R.layout.cell_dropdown_item);

         // set the adapter that fills the spinner
         durationSpinner.setAdapter(durationAdapter);
      }
      if (graphRequest instanceof HasEventNameParameter) {
         // turn on event selecting elements
         event1Group.setVisibility(View.VISIBLE);

         // make the eventName list and adapter
         eventNameAdapter = new ArrayAdapter<EventNameDescription>(
                  getApplicationContext(), R.layout.cell_dropdown_item,
                  eventNameList);

         // fill the eventNameList with values pulled from SFConn
         sfConn.getEventNameListViaNetwork(this);

         // set the layout for displaying options
         eventNameAdapter.setDropDownViewResource(R.layout.cell_dropdown_item);

         // set the adapter that fills the spinner
         event1Spinner.setAdapter(eventNameAdapter);
      }
      // this one should never happen alongside HasEventNameParameter
      if (graphRequest instanceof HasAttributeParameter) {
         // turn on event- and attribute-selecting elements
         event1Group.setVisibility(View.VISIBLE);
         attribute1Group.setVisibility(View.VISIBLE);

         // this adapter just needs to call toString() to get names of the
         // events
         eventAdapter = new ArrayAdapter<EventDescription>(
                  getApplicationContext(), R.layout.cell_dropdown_item,
                  eventList);
         // attributeAdapter = new ();

         // When something is selected in the event1Spinner, it changes the
         // attribute1Spinner
         event1Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                     int position, long id) {
               // get the event that was selected. NOTE: "Session" is in here
               // too
               EventDescription eventDescription = eventAdapter
                        .getItem(position);
               // Make a new adapter out of the attributes of that event
               attributeAdapter = new ArrayAdapter<AttributeDescription>(
                        getApplicationContext(), R.layout.cell_dropdown_item,
                        eventDescription.getAttributes());
               // set the new adapter on the attribute1Spinner
               attribute1Spinner.setAdapter(attributeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
         });

         // fill the eventList with values pulled from SFConn
         sfConn.getEventListViaNetwork(this);

         // set the layout for displaying options
         eventAdapter.setDropDownViewResource(R.layout.cell_dropdown_item);

         // set the adapters that fill the spinners
         event1Spinner.setAdapter(eventAdapter);
         // attribute1Spinner.setAdapter(attributeAdapter);

         // make sure that the onItemSelecedListener gets fired off
         // event1Spinner.setSelection(0);
      }
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
         // retrieve selected time from durationSpinner, put in graphRequest
         int selectedItemPosition = durationSpinner.getSelectedItemPosition();
         TimeInterval timeInterval = durationAdapter
                  .getItem(selectedItemPosition);
         ((HasOverTimeParameter) graphRequest).setTimeInterval(timeInterval
                  .name());
      }
      if (graphRequest instanceof HasEventNameParameter) {
         // retrieve selected event from event1Spinner, put in graphRequest
         int selectedItemPosition = event1Spinner.getSelectedItemPosition();
         EventNameDescription eventDescription = eventNameAdapter
                  .getItem(selectedItemPosition);
         ((HasEventNameParameter) graphRequest).setEventName(eventDescription
                  .getName());
      }
      if (graphRequest instanceof HasAttributeParameter) {
         // put their info in the graphRequest
         // get positions of selected event and attribute
         int selectedEventPosition = event1Spinner.getSelectedItemPosition();
         int selectedAttributePosition = attribute1Spinner
                  .getSelectedItemPosition();

         // get the actual event and attribute
         EventDescription event = eventAdapter.getItem(selectedEventPosition);
         AttributeDescription attribute = attributeAdapter
                  .getItem(selectedAttributePosition);

         // if the event name is "Session Attributes", replace it with an empty
         // string
         String eventName = event.getName();
         eventName = eventName.replace("Session Attributes", "");

         // put the event and attribute info into the graphRequest
         ((HasAttributeParameter) graphRequest).setAttributeName(attribute
                  .getName());
         ((HasAttributeParameter) graphRequest).setEventName(eventName);
      }

      // Save the finalized GraphRequest into the DB, if it's a
      // CustomGraphRequest
      saveCustomGraphRequest(graphRequest);

      // Create GraphViewerActivity intent and put URIParams in Intent
      Intent intent = new Intent(getApplicationContext(),
               GraphViewerActivity.class);
      intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, graphRequest);

      // Execute the intent
      startActivity(intent);
   }

   private void saveCustomGraphRequest(GraphRequest graphRequest) {
      if (graphRequest instanceof CustomGraphRequest) {
         CustomGraphRequest customGraphRequest = (CustomGraphRequest) graphRequest;

         // Save its new name
         customGraphRequest.setName(customGraphRequest.getAttributeName()
                  + "." + customGraphRequest.getEventName());

         // Once a CustomGraphRequest is saved, it becomes "Predefined."
         customGraphRequest.setReadOnly(true);

         tempoDatabase.addCustomGraphRequest(customGraphRequest);
      }
   }

   /**
    * Use this method to do simple validation on user inputs
    * 
    * @return
    */
   private boolean validateInputs() {
      // TODO: either actually do validation of input elements, or remove this
      // method
      return true;
   }

   @Override
   public void onEventNameDescriptionChanged(
            List<EventNameDescription> newEventNameDescriptions) {
      if (null != newEventNameDescriptions) {
         this.eventNameList.clear();
         this.eventNameList.addAll(newEventNameDescriptions);
         this.eventNameAdapter.notifyDataSetChanged();
      }
   }

   @Override
   public void onEventDescriptionChanged(
            List<EventDescription> newEventDescriptions) {
      if (null != newEventDescriptions) {
         eventList.clear();
         eventList.addAll(newEventDescriptions);
         eventAdapter.notifyDataSetChanged();
      }

   }

}
