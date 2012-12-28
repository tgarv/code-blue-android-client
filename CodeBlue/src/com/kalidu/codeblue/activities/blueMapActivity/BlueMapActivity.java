package com.kalidu.codeblue.activities.blueMapActivity;

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
import com.kalidu.codeblue.R;
import com.kalidu.codeblue.R.drawable;
import com.kalidu.codeblue.R.id;
import com.kalidu.codeblue.R.layout;
import com.kalidu.codeblue.R.menu;
import com.kalidu.codeblue.activities.MainActivity;

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
        addPoint(latitude, longitude, "That's you!", "Yeah!", "user", 0);	// TODO userId is always 0
        
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
	 * @param type the type of marker this is, either "user" or "question" for now
	 * @param id the id of the question if type == "question", otherwise nothing interesting (0) for now
	 * @return true if the point was successfully added
	 */
	public boolean addPoint(int latitude, int longitude, String title, String text, String type, int id){
		GeoPoint point = new GeoPoint(latitude, longitude);
        BlueOverlayItem overlay = new BlueOverlayItem(point, title, text, type, id);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable icon = this.getResources().getDrawable(R.drawable.marker);
        BlueItemizedOverlay itemizedOverlay = new BlueItemizedOverlay(icon, this);
        itemizedOverlay.addOverlay(overlay);
        mapOverlays.add(itemizedOverlay);
        
        return true;
	}
	
	/**
	 * Makes the HTTP Get request to get the list of questions, and then adds them as @BlueOverlayItem to the map
	 */
	public void addQuestions(){
		String url = MainActivity.getUrlManager().getListQuestionsURL();
    	JSONObject j = MainActivity.getClient().httpGet(url);
    	try {
			JSONArray questions = j.getJSONArray("questions");
			for (int i=0; i<questions.length(); i++){
				JSONObject question = questions.getJSONObject(i);
				int latitude = (int) ((question.getDouble("latitude") * 1e6));
				int longitude = (int) ((question.getDouble("longitude") * 1e6));
				String title = question.getString("title");
				String text = question.getString("text");
				int id = question.getInt("id");
				addPoint(latitude, longitude, title, text, "question", id);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
