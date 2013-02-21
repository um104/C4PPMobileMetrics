package edu.channel4.mm.db.android.activity;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.R.layout;
import edu.channel4.mm.db.android.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EventFunnelsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_funnels);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_event_funnels, menu);
		return true;
	}

}
