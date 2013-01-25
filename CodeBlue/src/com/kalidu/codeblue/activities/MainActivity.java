package com.kalidu.codeblue.activities;

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

import com.google.android.gcm.GCMRegistrar;
import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.listQuestionActivity.ListQuestionActivity;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;
import com.kalidu.codeblue.utils.BlueLocationListener;
import com.kalidu.codeblue.utils.RequestManager;
import com.kalidu.codeblue.utils.URLManager;

public class MainActivity extends Activity {

	private static SharedPreferences preferences;
	private static LocationManager locationManager;
	private static URLManager urlManager;
	private static RequestManager requestManager;
	
	static final String SENDER_ID = "164033855111";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        MainActivity.preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        MainActivity.setUrlManager(new URLManager());
        MainActivity.setRequestManager(new RequestManager());
        Log.i("Prefs", preferences.getAll().toString());
        
        initGCM();
        
        // Set up the LocationManager to get location updates
        initLocationManager();
		
		// Redirect to the login page if the sharedPreferences doesn't have a token string or if that string 
		// isn't valid
		
		checkLogin();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void initGCM(){
    	// Set up GCM Stuff
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
          GCMRegistrar.register(this, MainActivity.SENDER_ID);
        } else {
          Log.i("GCM", "Already registered");
          Log.i("GCM", GCMRegistrar.getRegistrationId(this));
        }
    }
    
    private void initLocationManager(){
    	MainActivity.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new BlueLocationListener();
		MainActivity.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
    
    private void checkLogin() {
    	final Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
		if((!preferences.contains("token"))){
			Log.e("Login", "No token found in preferences");
			// User not logged in, redirect to login page
			MainActivity.this.startActivity(loginIntent);
		}
		
		else{
			String token = preferences.getString("token", "");
			
			// The handler to handle the API response once the asynchronous http request returns.
			HttpTaskHandler handler = new HttpTaskHandler(){
				public void taskSuccessful(JSONObject json) {
					try {
						if(!json.getBoolean("success")){
							Log.i("Login", json.toString());
							Log.e("Login", "JSON response says login was unsuccessful");
							MainActivity.this.startActivity(loginIntent);
						}
						else {
							// user is logged in, probably redirect to profile page or something
							Intent intent = new Intent(MainActivity.this, ListQuestionActivity.class);
							startActivity(intent);
						}
					} catch (JSONException e) {
						Log.e("Login", "JSON response had no \"success\" value");
						MainActivity.this.startActivity(loginIntent);
					}
				}

				public void taskFailed() {
					// TODO Auto-generated method stub
					MainActivity.this.startActivity(loginIntent);
				}
			};
			
			// Send the request.
			getRequestManager().verifyToken(handler, token);
	
		}
	}
    
    // Getters and Setters
    
    public static SharedPreferences getPreferences(){
    	return MainActivity.preferences;
    }

	public static URLManager getUrlManager() {
		return urlManager;
	}

	public static void setUrlManager(URLManager urlManager) {
		MainActivity.urlManager = urlManager;
	}

	public static RequestManager getRequestManager() {
		return requestManager;
	}

	public static void setRequestManager(RequestManager requestManager) {
		MainActivity.requestManager = requestManager;
	}
}
