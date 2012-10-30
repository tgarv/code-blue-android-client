package com.kalidu.codeblue;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.util.Log;

public class MainActivity extends Activity {

    private BlueHttpClient client;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.client = new BlueHttpClient();
        
        // Test the client's getJSON method. Using some random API that Square made me use during their interview
        JSONObject j = this.client.getJSON("http://api.seatgeek.com/2/performers/288");
        try {
			Log.i("JSON", j.get("name").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Set click listener for the button to go to the login page
        ((Button) findViewById(R.id.launch_login)).setOnClickListener(
        	new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					MainActivity.this.startActivity(intent);
				}
        	}
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public BlueHttpClient getClient(){
    	return this.client;
    }
}
