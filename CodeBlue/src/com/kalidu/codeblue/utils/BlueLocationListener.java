package com.kalidu.codeblue.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.kalidu.codeblue.activities.MainActivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class BlueLocationListener implements LocationListener{
	public void onLocationChanged(Location location){
		Log.i("Location", "Location update at " + location.getLatitude() + ", " + location.getLongitude()
				+ ", " + location.getAltitude());	// TODO altitude doesn't seem to be working
		Log.i("Location", "Location update accuracy is " + location.getAccuracy());
		// Update the location stored in the SharedPreferences
		final SharedPreferences preferences = MainActivity.getPreferences();
        Editor editor = preferences.edit();
        editor.putFloat("latitude", (float) location.getLatitude());
        editor.putFloat("longitude", (float) location.getLongitude());
        editor.commit();
		// And make the POST to update the location on the server.
		BlueHttpClient client = MainActivity.getClient();
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("latitude", Double.toString(location.getLatitude())));
		params.add(new BasicNameValuePair("longitude", Double.toString(location.getLongitude())));
		params.add(new BasicNameValuePair("elevation", Double.toString(location.getAltitude())));
		JSONObject j = client.httpPost(MainActivity.getUrlManager().getSetLocationURL(), params);
		Log.i("JSON", j.toString());
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
