package com.kalidu.codeblue;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class LocationViewActivity extends Activity {
	
	private double latitude, longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);
        this.updatePosition(10.0, -15.0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_location_view, menu);
        return true;
    }
    
    public void updatePosition(double latitude, double longitude){
    	this.latitude = latitude;
    	this.longitude = longitude;
    	((TextView) findViewById(R.id.location_latitude)).setText(Double.toString(latitude));
    	((TextView) findViewById(R.id.location_longitude)).setText(Double.toString(longitude));
    }
}
