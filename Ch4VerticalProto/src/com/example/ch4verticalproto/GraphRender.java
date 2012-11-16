package com.example.ch4verticalproto;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class GraphRender extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_render);
        /*
        Intent intent = getIntent();
        String appName = intent.getStringExtra(AppList.KEY_APP);
        
        TextView textView = new TextView (this);
        textView.setTextSize(40);
        textView.setText(appName);
        
        //Set the text view as the activity layout
        setContentView(textView);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_graph_render, menu);
        return true;
    }
}
