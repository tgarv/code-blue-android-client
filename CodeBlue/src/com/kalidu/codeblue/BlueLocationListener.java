package com.kalidu.codeblue;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class BlueLocationListener implements LocationListener{
	public void onLocationChanged(Location location){
		Log.i("Location", "Location update at " + location.getLatitude() + ", " + location.getLongitude());
		Log.i("Location", "Location update accuracy is " + location.getAccuracy());
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
