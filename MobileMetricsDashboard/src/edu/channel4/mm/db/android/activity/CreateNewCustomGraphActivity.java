package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AttributeDescription;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.model.request.HasOverTimeParameter;
import edu.channel4.mm.db.android.model.request.GraphRequest.TimeInterval;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Logger;

public class CreateNewCustomGraphActivity extends Activity implements
		OnAttributeDescriptionChangedListener {

	protected SalesforceConn sfConn;
	private List<String> attributeDescriptions = new ArrayList<String>();
	private ArrayAdapter<String> adapterAttribute1;
	private ArrayAdapter<String> adapterStartTime;

	private AttributeDescription attributeDescription1;
	private AttributeDescription attributeDescription2;
	private GraphRequest graphRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_custom_graph);
		
		// Get the graphRequest from the Intent
		graphRequest = getIntent().getParcelableExtra(Keys.GRAPH_REQUEST_EXTRA);

		// Reference the views
		Spinner spinnerAttribute1 = (Spinner) findViewById(R.id.spinnerAttribute1);
		Spinner spinnerAttribute2 = (Spinner) findViewById(R.id.spinnerAttribute2);
		Spinner spinnerStartTime = (Spinner) findViewById(R.id.spinnerStartTime);

		// Create the SFConn for this activity
		sfConn = SalesforceConn.getInstance(getApplicationContext());

		// Setup the attribute1 Spinner.
		adapterAttribute1 = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, attributeDescriptions);
		spinnerAttribute1.setAdapter(adapterAttribute1);

		// Disable the attribute2 Spinner (for now).
		spinnerAttribute2.setEnabled(false);

		// Setup the startTime spinner
		adapterStartTime = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item,
				GraphRequest.TimeInterval.getStringArray());
		spinnerStartTime.setAdapter(adapterStartTime);

		// Subscribe the activity to the Database for changes to
		// attributeDescription
		TempoDatabase.getInstance().addOnAttributeDescriptionChangedListener(
				this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Send an asynchronous request to SF to get the current attribute list.
		sfConn.getAttribList();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Unsubscribe this activity from the database for changes to
		// attributeDescription
		TempoDatabase.getInstance()
				.removeOnAttributeDescriptionChangedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.activity_create_new_custom_graph, menu);
		return true;
	}

	/*
	public void onViewCustomGraphButtonClicked(View view) {
	   
	   Spinner spinnerAttribute1 = (Spinner) findViewById(R.id.spinnerAttribute1);
	   Spinner spinnerStartTime = (Spinner) findViewById(R.id.spinnerStartTime);
	   
      // retrieve selected time from durationspinner, put in graphrequest
      int selectedItemPosition = spinnerAttribute1.getSelectedItemPosition();
      TimeInterval timeInterval = durationAdapter.getItem(selectedItemPosition);
      ((HasOverTimeParameter)graphRequest).setTimeDuration(timeInterval.name());
	   
	   ((CustomGraphRequest) graphRequest).
	   
      // Create GraphViewerActivity intent and put URIParams in Intent
      Intent intent = new Intent(getApplicationContext(),
               GraphViewerActivity.class);
      intent.putExtra(Keys.REQUEST_URL_PARAMETERS,
               graphRequest.getUrlParameterString(getApplicationContext()));

      // Execute the intent
      startActivity(intent);
	}
	*/

	@Override
	public void onAttributeDescriptionChanged(
			List<AttributeDescription> newAttributeDescriptions) {
		attributeDescriptions.clear();

		// Convert the attribute descriptions to Strings for the Spinner's
		// ArrayAdapter
		for (AttributeDescription attributeDescription : newAttributeDescriptions) {
			attributeDescriptions.add(attributeDescription.getName());
		}

		adapterAttribute1.notifyDataSetChanged();

		Toast.makeText(
				getApplicationContext(),
				"Refreshed attributes. Got " + newAttributeDescriptions.size()
						+ " attributes.", Toast.LENGTH_SHORT).show();
		Logger.d("Got new attribute descriptions: " + attributeDescriptions);
	}
}
