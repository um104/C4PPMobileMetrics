package com.example.ch4verticalproto;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class AppList extends ListActivity {
 
	public final static String KEY_APP = "com.example.firsttest.APPCHOICE";
	static final String[] FRUITS = new String[] { "AdFree", "KanauruApp", "Metal Slug XD",
			"p2000", "PhilStone", "Angry..no", "MagnumOps"};
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		// no more this
		// setContentView(R.layout.list_fruit);
 
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_app_list,FRUITS));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*
				Intent intent = new Intent(AppList.this, GraphRender.class);
				intent.putExtra(KEY_APP, FRUITS[position]);
				startActivity(intent);
				*/
				
				
				Uri uri = Uri.parse("https://c.na9.visual.force.com/apex/VertProto");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				
				
				WebView webview = new WebView(AppList.this); 
				//setContentView(webview);
				
			    // When clicked, show a toast with the TextView text
			    //Toast.makeText(getApplicationContext(),
				//((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				
				
				
			}
		});
 
	}
 
}
