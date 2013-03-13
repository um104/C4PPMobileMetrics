package edu.channel4.mm.db.android.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.util.Log;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		// get listView from about activity
		ListView list = (ListView) findViewById(R.id.listViewDeveloperNames);
		// set the list view's adapter to be our custom array adapter
		list.setAdapter(new AboutArrayAdapter(this, R.id.cellAbout,
				getResources().getStringArray(R.array.developer_names)));
	}

	private class AboutArrayAdapter extends ArrayAdapter<String> {

		private String[] developerNames;

		public AboutArrayAdapter(Context context, int textViewResourceId,
				String[] developerNames) {
			super(context, textViewResourceId, developerNames);
			this.developerNames = developerNames;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (developerNames == null) {
				Log.e("appList List is null: will not draw cell");
				return convertView;
			}

			// Grab the pertinent name
			String name = developerNames[position];

			// inflate the cell layout
			LayoutInflater inflater = (LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.cell_about, null);

			//setting the cell's text
			TextView developerTextView = (TextView) convertView
					.findViewById(R.id.developerName);
			developerTextView.setText(name);

			return convertView;
		}
	}
}
