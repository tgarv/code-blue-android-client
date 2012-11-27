package com.kalidu.codeblue;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private MapView mapView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        final SharedPreferences preferences = MainActivity.getPreferences();
        // The values stored for latitude and longitude are degrees, but the GeoPoint needs microdegrees
        int latitude = (int) (preferences.getFloat("latitude", 0) * 1e6);
        int longitude = (int) (preferences.getFloat("longitude", 0) * 1e6);
        
        // Add a marker for the user location
        addPoint(latitude, longitude, "That's you!", "Yeah!");
        
        // Center the map on the user's location and set the zoom level
        MapController controller = mapView.getController();
        controller.setCenter(new GeoPoint(latitude, longitude));
        controller.setZoom(16);
        
        // Add the questions to the map
        addQuestions();
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
	
	/**
	 * 
	 * @param latitude the latitude of the point
	 * @param longitude	the longitude of the point
	 * @param title	the title of the point
	 * @param text the text for the point
	 * @return true if the point was successfully added
	 */
	public boolean addPoint(int latitude, int longitude, String title, String text){
		GeoPoint point = new GeoPoint(latitude, longitude);
        OverlayItem userMarker = new OverlayItem(point, title, text);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
        BlueItemizedOverlay itemizedOverlay = new BlueItemizedOverlay(drawable, this);
        itemizedOverlay.addOverlay(userMarker);
        mapOverlays.add(itemizedOverlay);
        
        return true;
	}
	
	public void addQuestions(){
		String url = MainActivity.urlManager.getListQuestionsURL();
    	JSONObject j = MainActivity.getClient().httpGet(url);
    	try {
			JSONArray questions = j.getJSONArray("questions");
			for (int i=0; i<questions.length(); i++){
				JSONObject question = questions.getJSONObject(i);
				int latitude = (int) ((question.getDouble("latitude") * 1e6));
				int longitude = (int) ((question.getDouble("longitude") * 1e6));
				Log.i("MAP", Integer.toString(latitude));
				Log.i("MAP", Integer.toString(longitude));
				addPoint(latitude, longitude, question.getString("title"), question.getString("text"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
