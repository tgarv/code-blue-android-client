package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private static BlueHttpClient client;
	private static SharedPreferences preferences;
	private static LocationManager locationManager;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.client = new BlueHttpClient();
        MainActivity.preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Log.i("Prefs", preferences.getAll().toString());
        
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
        MainActivity.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new BlueLocationListener();
		MainActivity.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		// Redirect to the login page if the sharedPreferences doesn't have a token string or if that string 
		// isn't valid
		Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
		if((!preferences.contains("token"))){
			Log.e("Login", "No token found in preferences");
			// User not logged in, redirect to login page
			MainActivity.this.startActivity(loginIntent);
		}
		
		else{
			String verifyTokenURL = "http://10.0.2.2:5000/api/android/verify_token.json";
			List<NameValuePair> params = new ArrayList<NameValuePair>(0);
			params.add(new BasicNameValuePair("token", preferences.getString("token", "")));
			JSONObject response = client.httpPost(verifyTokenURL, params);
			
			try {
				if(!response.getBoolean("success")){
					Log.e("Login", "JSON response says it was unsuccessful");
					MainActivity.this.startActivity(loginIntent);
				}
				else {
					// user is logged in, probably redirect to profile page or something
				}
			} catch (JSONException e) {
				Log.e("Login", "JSON response had no \"success\" value");
				MainActivity.this.startActivity(loginIntent);
			}
		}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public static BlueHttpClient getClient(){
    	return MainActivity.client;
    }
    
    public static SharedPreferences getPreferences(){
    	return MainActivity.preferences;
    }
}
