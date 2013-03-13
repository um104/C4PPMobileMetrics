package edu.channel4.mm.db.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.DeviceTypeDistributionGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.model.request.LanguageLocaleGraphRequest;
import edu.channel4.mm.db.android.model.request.NationalityBreakdownGraphRequest;
import edu.channel4.mm.db.android.util.GraphRequestArrayAdapter;
import edu.channel4.mm.db.android.util.Keys;

public class AudienceActivity extends Activity {

	private ListView listView;
	private GraphRequestArrayAdapter adapter;
	private List<GraphRequest> graphRequests;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audience);

		listView = (ListView) findViewById(R.id.listviewAudienceActivity);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Grab the AudienceGraphRequest for the selected item.
				GraphRequest graphRequest = graphRequests.get(position);

				// Construct the correct Intent for the selected
				// AudienceGraphRequest
				Intent intent = graphRequest
						.constructGraphRequestIntent(getApplicationContext());

				// Start the activity for that Intent with all of its baggage,
				// but only if the intent could actually be constructed based
				// on the GraphRequest.
				if (intent != null) {
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(),
							graphRequest.toString() + " not yet implemented.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		graphRequests = new ArrayList<GraphRequest>();
		graphRequests.add(new NationalityBreakdownGraphRequest());
		graphRequests.add(new LanguageLocaleGraphRequest());
		graphRequests.add(new DeviceTypeDistributionGraphRequest());

		adapter = new GraphRequestArrayAdapter(getApplicationContext(),
				graphRequests.toArray(new GraphRequest[0]));

		listView.setAdapter(adapter);
		setTitle(getResources().getString(R.string.audience));
		
		
		String appLabel = getApplicationContext().getSharedPreferences(
               Keys.PREFS_NS, 0).getString(Keys.APP_LABEL, null);

      ((TextView) findViewById(R.id.audienceHeader)).setText(appLabel);
	}

}
