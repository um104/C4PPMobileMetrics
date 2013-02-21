package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.UsageGraphRequest;
import edu.channel4.mm.db.android.util.GraphRequestArrayAdapter;

public class UsageActivity extends Activity {

	private ListView listView;
	private GraphRequestArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usage);

		ListView listView = (ListView) findViewById(R.id.listviewUsageActivity);
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
