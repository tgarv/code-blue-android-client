package com.kalidu.codeblue;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					MainActivity.this.startActivity(intent);
				}
        	}
        );
        
        // Set up the LocationManager to get location updates
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new BlueLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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
