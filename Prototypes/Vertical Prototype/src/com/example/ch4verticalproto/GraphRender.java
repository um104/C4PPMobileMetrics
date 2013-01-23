package com.example.ch4verticalproto;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GraphRender extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_render);
        
        //make a web view placeholder, a frame view?
		//make a webview
		//point it to the login screen
		//fill the placeholder with the webview
		//that's it
        
        WebView wView = (WebView) findViewById(R.id.webView1);
        wView.setWebViewClient(new WebViewClient());
        wView.getSettings().setJavaScriptEnabled(true);
        wView.loadUrl("https://c.na9.visual.force.com/apex/VertProto");
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_graph_render, menu);
        return true;
    }
}
