package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.util.GraphRequestArrayAdapter;

public class CustomGraphActivity extends Activity {

	private ListView listView;
	private GraphRequestArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_graph);

		listView = (ListView) findViewById(R.id.listViewCustomGraphActivity);
		listView.setEmptyView(findViewById(R.id.textViewNoCustomGraphs));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Grab the CustomGraphRequest for the selected item.
				CustomGraphRequest customGraphRequest = new CustomGraphRequest();

				// Construct the correct Intent for the selected
				// CustomGraphRequest
				Intent intent = customGraphRequest
						.constructGraphRequestIntent(getApplicationContext());

				// Start the activity for that Intent with all of its baggage,
				// but only if the intent could actually be constructed based
				// on the GraphRequest.
				if (intent != null) {
					startActivity(intent);
				} else {
					Toast.makeText(
							getApplicationContext(),
							customGraphRequest.toString()
									+ " not yet implemented.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		adapter = new GraphRequestArrayAdapter(getApplicationContext(),
				new CustomGraphRequest[] { });
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_audience, menu);
		return true;
	}

}
