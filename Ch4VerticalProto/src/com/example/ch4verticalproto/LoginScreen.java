package com.example.ch4verticalproto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginScreen extends Activity {
	public final static String KEY_USER_MESSAGE = "com.example.firsttest.USERNAME";
	public final static String KEY_PASS_MESSAGE = "com.example.firsttest.PASSWORD";
	
	private String userName;
	private String passWord;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //userName = ((EditText) findViewById(R.id.UsernameTextField)).getText().toString();
        //passWord = ((EditText) findViewById(R.id.PasswordTextField)).getText().toString();
        setContentView(R.layout.activity_login_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login_screen, menu);
        return true;
    }
    
    public void logIn (View view) {
//    	Intent intent = new Intent(this, LoginScreen.class);
    	Intent intent = new Intent(this, AppList.class);
    	intent.putExtra(KEY_USER_MESSAGE, userName);
    	intent.putExtra(KEY_PASS_MESSAGE, passWord);
    	startActivity(intent);
    	
    }
}
