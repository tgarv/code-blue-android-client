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
	private static int notificationsCount;
	
	static final String SENDER_ID = "164033855111";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        MainActivity.preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        MainActivity.setUrlManager(new URLManager());
        MainActivity.setRequestManager(new RequestManager());
        Log.i("Prefs", preferences.getAll().toString());
        MainActivity.setNotificationsCount(0);
        
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
        String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
          GCMRegistrar.register(this, MainActivity.SENDER_ID);
        } else {
          Log.i("GCM", "Already registered");
        }
        regId = GCMRegistrar.getRegistrationId(this);
        Log.i("GCM", regId);
        
        HttpTaskHandler handler = new HttpTaskHandler(){
			@Override
			public void taskSuccessful(JSONObject json) {
				Log.i("GCM", "Registered to server");
			}
			@Override
			public void taskFailed() {
				// TODO Auto-generated method stub
				Log.e("GCM", "Registration failed");
			}
        };
        
        getRequestManager().registerGCM(handler, regId);
    }
    
    private void initLocationManager(){
    	MainActivity.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new BlueLocationListener();
		MainActivity.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
    
    private void checkLogin() {
    	final Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
		this.startActivity(loginIntent);
		
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

	public static int getNotificationsCount() {
		return notificationsCount;
	}

	public static void setNotificationsCount(int notificationsCount) {
		MainActivity.notificationsCount = notificationsCount;
	}
}
