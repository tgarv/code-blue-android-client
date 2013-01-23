package com.kalidu.codeblue.activities.blueMapActivity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.CreateQuestionActivity;
import com.kalidu.codeblue.activities.MainActivity;
import com.kalidu.codeblue.activities.listQuestionActivity.ListQuestionActivity;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;

public class BlueMapActivity extends Activity {
    private GoogleMap map;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        final SharedPreferences preferences = MainActivity.getPreferences();
        
        int latitude = (int) (preferences.getFloat("latitude", 0));
        int longitude = (int) (preferences.getFloat("longitude", 0));
        LatLng center = new LatLng(latitude, longitude);
        
        // Add a marker for the user location
        //addPoint(latitude, longitude, "That's you!", "Yeah!", "user", 0);	// TODO userId is always 0
        
        // Center the map on the user's location and set the zoom level
        
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 16));
        
        // Add the questions to the map
        //addQuestions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    		case R.id.menu_new_question:
    			Intent newQuestionIntent = new Intent(this, CreateQuestionActivity.class);
				this.startActivity(newQuestionIntent);
				return true;
    		case R.id.menu_questions_list:
    			Intent questionsListIntent = new Intent(this, ListQuestionActivity.class);
				this.startActivity(questionsListIntent);
				return true;
    		case R.id.menu_questions_map:
    			Intent questionsMapIntent = new Intent(this, BlueMapActivity.class);
				this.startActivity(questionsMapIntent);
				return true;
    		case R.id.menu_profile:
    			// TODO
    			return true;
    		case R.id.menu_search:
    			// TODO
    			return true;
    		case R.id.menu_settings:
    			// TODO
    			return true;
			default:
				return super.onOptionsItemSelected(item);
    	}
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
		/*GeoPoint point = new GeoPoint(latitude, longitude);
        BlueOverlayItem overlay = new BlueOverlayItem(point, title, text, type, id);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable icon = this.getResources().getDrawable(R.drawable.marker);
        BlueItemizedOverlay itemizedOverlay = new BlueItemizedOverlay(icon, this);
        itemizedOverlay.addOverlay(overlay);
        mapOverlays.add(itemizedOverlay);*/
        
        return true;
	}
	
	/**
	 * Makes the HTTP Get request to get the list of questions, and then adds them as @BlueOverlayItem to the map
	 */
	public void addQuestions(){
		
    	HttpTaskHandler handler = new HttpTaskHandler(){
			public void taskSuccessful(JSONObject json) {
				// TODO Auto-generated method stub
				try {
					JSONArray questions = json.getJSONArray("questions");
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

			public void taskFailed() {
				// TODO Auto-generated method stub
				
			}
    	};
    	MainActivity.getRequestManager().listQuestions(handler);
	}
}
