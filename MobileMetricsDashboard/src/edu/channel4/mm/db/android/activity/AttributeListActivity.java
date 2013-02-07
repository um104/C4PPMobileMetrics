package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.AppDescription;
import edu.channel4.mm.db.android.model.AttribDescription;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.GraphType;
import edu.channel4.mm.db.android.util.Keys;

public class AttributeListActivity extends Activity implements IAttributeListObserver {
	
	private AppDescription appDescription;
	private GraphType graphType;
	private SalesforceConn sfConn;
	private ListView attribListView;
	private List<AttribDescription> attribList;
	private ArrayAdapter<AttribDescription> attribAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attribute_list);
		
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // create a new simple AttribDescription adapter
        attribAdapter = new ArrayAdapter<AttribDescription>(this, android.R.layout.simple_list_item_1, attribList);
                		
        // Get intent and accompanying data
		Intent intent = getIntent();
		appDescription = intent.getParcelableExtra(Keys.PREFS_NS + Keys.APP_DESC);
		graphType = (GraphType) intent.getSerializableExtra(Keys.PREFS_NS + Keys.GRAPH_TYPE);
		
		// Instantiate SalesforceConn
		sfConn = SalesforceConn.getInstance(getApplicationContext());
		
		// Set up ListView
		attribListView = (ListView) findViewById(R.id.listViewAttribList);
		
		attribListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override 				// When attribute clicked, send Intent to GraphViewerActivity
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//TODO(mlerner): Change this in the event that the desired graph is a two-variable graph
				Intent intent = new Intent(getApplicationContext(), GraphViewerActivity.class);
				
				intent.putExtra(Keys.PREFS_NS + Keys.GRAPH_TYPE, graphType);
				intent.putExtra(Keys.PREFS_NS + Keys.APP_DESC, appDescription);
				//TODO(mlerner): Change this to send just the appId primary key
				intent.putExtra(Keys.PREFS_NS + Keys.ATTRIB_DESC, (AttribDescription)(parent.getAdapter().getItem(position)));
								
				startActivity(intent);
			}
		});

		// Hook up the array adapter to the ListView
		attribListView.setAdapter(attribAdapter);
		
		getAttribList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_attribute_list, menu);
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
	
	public void getAttribList() {
		Toast.makeText(getApplicationContext(),
				"Retrieving attrib list from Salesforce", Toast.LENGTH_SHORT).show();

		// Construct a list of classes that care about the getAttribList callback
		List<IAttributeListObserver> attribListObservers = new ArrayList<IAttributeListObserver>();

		// Add this activity to that list
		attribListObservers.add(this);

		// Execute the getAttribList method asynchronously
		sfConn.getAttribList(attribListObservers, appDescription, graphType);
	}

	// Method called once attrib list is retrieved
	@Override
	public void updateAttributeList(final List<AttribDescription> attribList) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AttributeListActivity.this.attribList.clear();
				
				for (AttribDescription attribDesc : attribList) {
					AttributeListActivity.this.attribList.add(attribDesc);
				}
				
				Collections.sort(AttributeListActivity.this.attribList);
				attribAdapter.notifyDataSetChanged();
			}
		});
		
	}
}

