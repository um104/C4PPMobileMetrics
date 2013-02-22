package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import edu.channel4.mm.db.android.EventPickerActivity;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.UsageGraphRequest;
import edu.channel4.mm.db.android.util.GraphRequestArrayAdapter;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Logger;

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
				Intent intent = constructGraphRequestIntent(usageGraphRequest);

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

	private Intent constructGraphRequestIntent(
			UsageGraphRequest usageGraphRequest) {
		Intent intent = null;

		switch (usageGraphRequest) {
		case ACTIVE_USERS:
			// NYI
			break;
		case EVENT_COUNT_OVER_TIME:
			intent = new Intent(getApplicationContext(),
					EventPickerActivity.class);
			break;
		case NUMBER_OF_SESSIONS_OVER_TIME:
			// NYI
			break;
		case SESSION_LENGTH_OVER_TIME:
			// NYI
			break;
		case TIME_OF_DAY_DIST:
			// NYI
			break;
		default:
			Logger.e("Unknown UsageGraphRequest: " + usageGraphRequest);
			return null;
		}

		// Sanity check to make sure we're using an implemented
		// UsageGraphRequest
		if (intent == null) {
			Logger.e("Unimplemented UsageGraphRequest selected");
			return null;
		}

		// Always put the GraphRequest type itself into the intent
		intent.putExtra(Keys.GRAPH_REQUEST_TYPE, usageGraphRequest);

		return intent;
	}

}
