package com.kalidu.codeblue.utils;

import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.kalidu.codeblue.activities.MainActivity;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;

public class BlueLocationListener implements LocationListener{
	public void onLocationChanged(Location location){
		Log.i("Location", "Location update at " + location.getLatitude() + ", " + location.getLongitude()
				+ ", " + location.getAltitude());
		Log.i("Location", "Location update accuracy is " + location.getAccuracy());
		
		// Update the location stored in the SharedPreferences
		final SharedPreferences preferences = MainActivity.getPreferences();
        Editor editor = preferences.edit();
        editor.putInt("latitude", (int) (location.getLatitude() * 1e6));
        editor.putInt("longitude", (int) (location.getLongitude() * 1e6));
        editor.commit();
        
        HttpTaskHandler handler = new HttpTaskHandler(){
			public void taskSuccessful(JSONObject json) {
				// TODO Auto-generated method stub
				
			}

			public void taskFailed() {
				// TODO Auto-generated method stub
				
			}
        };
		// And make the POST to update the location on the server.
		MainActivity.getRequestManager().setLocation(handler, location);
	}
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		Log.i("Location", "ProviderDisabled");
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.i("Location", "ProviderEnabled");
	}

	public void onStatusChanged(String provider, int status,
			Bundle extras) {
		// TODO Auto-generated method stub
		Log.i("Location", "StatusChanged");
	}
}
