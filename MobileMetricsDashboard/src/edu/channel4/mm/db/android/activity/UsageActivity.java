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
import edu.channel4.mm.db.android.model.request.UsageGraphRequest;
import edu.channel4.mm.db.android.util.GraphRequestArrayAdapter;

public class UsageActivity extends Activity {

	private ListView listView;
	private GraphRequestArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usage);

		listView = (ListView) findViewById(R.id.listviewUsageActivity);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Grab the UsageGraphRequest for the selected item.
				UsageGraphRequest usageGraphRequest = UsageGraphRequest
						.values()[position];

				// Construct the correct Intent for the selected
				// UsageGraphRequest
				Intent intent = usageGraphRequest.constructGraphRequestIntent(
						getApplicationContext());

				// Start the activity for that Intent with all of its baggage,
				// but only if the intent could actually be constructed based
				// on the GraphRequest.
				if (intent != null) {
					startActivity(intent);
				} else {
					Toast.makeText(
							getApplicationContext(),
							usageGraphRequest.toString()
									+ " not yet implemented.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		adapter = new GraphRequestArrayAdapter(getApplicationContext(),
				UsageGraphRequest.values());
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_usage, menu);
		return true;
	}

}
