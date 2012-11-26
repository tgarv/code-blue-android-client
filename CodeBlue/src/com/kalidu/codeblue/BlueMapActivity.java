package com.kalidu.codeblue;

import java.util.List;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class BlueMapActivity extends MapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        final SharedPreferences preferences = MainActivity.getPreferences();
        // The values stored for latitude and longitude are degrees, but the GeoPoint needs microdegrees
        int latitude = (int) (preferences.getFloat("latitude", 0) * 1e6);
        int longitude = (int) (preferences.getFloat("longitude", 0) * 1e6);
        
        GeoPoint point = new GeoPoint(latitude, longitude);
        Log.i("POINT", Integer.toString(point.getLatitudeE6()));
        OverlayItem userMarker = new OverlayItem(point, "That's you!", "Yeah!");
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
        BlueItemizedOverlay itemizedOverlay = new BlueItemizedOverlay(drawable, this);
        itemizedOverlay.addOverlay(userMarker);
        mapOverlays.add(itemizedOverlay);
        
        MapController controller = mapView.getController();
        controller.setCenter(point);
        controller.setZoom(16);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
