package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import edu.channel4.mm.db.android.R;

public class CustomGraphActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_graph);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_custom_graph, menu);
		return true;
	}

}
