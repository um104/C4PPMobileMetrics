package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
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
				GraphRequest graphRequest = new CustomGraphRequest();

				// Construct the correct Intent for the selected
				// CustomGraphRequest
				Intent intent = graphRequest.constructGraphRequestIntent(getApplicationContext());

				// Start the activity for that Intent with all of its baggage,
				// but only if the intent could actually be constructed based
				// on the GraphRequest.
				if (intent != null) {
					startActivity(intent);
				} else {
					Toast.makeText(
							getApplicationContext(),
							graphRequest.toString()
									+ " not yet implemented.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		adapter = new GraphRequestArrayAdapter(getApplicationContext(),
				new CustomGraphRequest[] {});
		listView.setAdapter(adapter);
	}

	public void createNewCustomGraph(View v) {
		startActivity(new Intent(getApplicationContext(),
				CreateNewCustomGraphActivity.class));
	}

}