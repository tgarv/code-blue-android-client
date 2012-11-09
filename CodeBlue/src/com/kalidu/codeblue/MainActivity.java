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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static BlueHttpClient client;
	private static SharedPreferences preferences;
	private static LocationManager locationManager;
	static URLManager urlManager;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.client = new BlueHttpClient();
        MainActivity.preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        MainActivity.urlManager = new URLManager();
        Log.i("Prefs", preferences.getAll().toString());
        
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
			String verifyTokenURL = urlManager.getVerifyTokenURL();
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
		// Set the greeting
		((TextView) findViewById(R.id.welcome)).setText("Welcome, " + preferences.getString("username", "stranger"));

		// Set click listener for "View Questions" button
		((Button) findViewById(R.id.launch_question_list)).setOnClickListener(
			new OnClickListener(){
				public void onClick(View arg0) {
					Intent viewQuestionsIntent = new Intent(MainActivity.this, ListQuestionActivity.class);
					MainActivity.this.startActivity(viewQuestionsIntent);
				}
			}
		);
		
		// Set click listener for "New Question" button
		((Button) findViewById(R.id.question_create)).setOnClickListener(
			new OnClickListener(){
				public void onClick(View arg0) {
							Intent createQuestionIntent = new Intent(MainActivity.this, CreateQuestionActivity.class);
							MainActivity.this.startActivity(createQuestionIntent);
				}
			}
		);
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
