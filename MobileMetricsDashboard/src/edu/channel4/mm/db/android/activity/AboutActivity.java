package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.channel4.mm.db.android.R;

public class AboutActivity extends Activity {
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		ListView list;
		String list_array[] = getResources().getStringArray(
				R.array.developer_names);
		list = (ListView) findViewById(R.id.listViewDeveloperNames);
		list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list_array));
	}
}
