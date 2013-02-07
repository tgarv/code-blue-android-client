package com.kalidu.codeblue.activities;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.kalidu.codeblue.CreateQuestionItemizedOverlay;
import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.listQuestionActivity.ListQuestionActivity;
import com.kalidu.codeblue.activities.listQuestionMapActivity.ListQuestionMapActivity;
import com.kalidu.codeblue.utils.ActionBarBuilder;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;
import com.kalidu.codeblue.utils.NavBarBuilder;

/**
 * 
 * @author tgarv
 * The activity that allows a user to create a new question using a form.
 * 
 */
public class CreateQuestionActivity extends MapActivity {
	private MapView mapView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_question);
        NavBarBuilder navBuilder = new NavBarBuilder(this);
        navBuilder.setListeners();
        ActionBarBuilder actionBuilder = new ActionBarBuilder(this);
        actionBuilder.setListeners();
        setListeners();
        
        // Set up the map and overlay
        Drawable marker = getResources().getDrawable(R.drawable.marker);
        marker.setBounds(-marker.getIntrinsicWidth()/2, -marker.getIntrinsicHeight(), 
        		marker.getIntrinsicWidth()/2, 0);
        
        this.setMapView((MapView) findViewById(R.id.mapview));
        this.getMapView().getOverlays().add(new CreateQuestionItemizedOverlay(marker));
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
    			Intent questionsMapIntent = new Intent(this, ListQuestionMapActivity.class);
				this.startActivity(questionsMapIntent);
				return true;
    		case R.id.menu_profile:
    			// TODO
    			return true;
    		case R.id.menu_refresh:
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
     * Make a request through the RequestManager to create a new Question on the server.
     * @param title	The title to be used for this question.
     * @param text	The text/query/body of the question.
     */
    private void createQuestion(String title, String text){
    	String form_delta = "6";	// TODO make this changeable.
    	
    	// The handler to handle the API response after it returns
		HttpTaskHandler handler = new HttpTaskHandler(){
			public void taskSuccessful(JSONObject json) {
				// Question created, redirect to question list and clear the inputs
				((TextView) findViewById(R.id.new_question_title)).setText("");
				((TextView) findViewById(R.id.new_question_text)).setText("");
				Intent intent = new Intent(CreateQuestionActivity.this, ListQuestionActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}

			public void taskFailed() {
				// TODO Auto-generated method stub
				
			}
		};
		// Get the position used to mark the location of the question
		CreateQuestionItemizedOverlay overlay = (CreateQuestionItemizedOverlay)this.getMapView().
				getOverlays().get(this.getMapView().getOverlays().size()-1);
		OverlayItem item = overlay.getItem(overlay.size()-1);
		GeoPoint point = item.getPoint();
		double latitude = point.getLatitudeE6()/(double)1e6;
		double longitude = point.getLongitudeE6()/(double)1e6;
		Log.i("TEST", "Need to create question at: " + Double.toString(latitude) + ", " + Double.toString(longitude));
		// Make the request
//    	MainActivity.getRequestManager().createQuestion(handler, title, text, form_delta);
    }
    
    /**
     * Set up click/touch listeners
     */
    private void setListeners(){
    	// Set click listener for "New Question" button
    	((Button) findViewById(R.id.create_question)).setOnClickListener(
    		new OnClickListener(){
    			public void onClick(View arg0) {
    				String title = ((TextView) findViewById(R.id.new_question_title)).getText().toString();
    				String text = ((TextView) findViewById(R.id.new_question_text)).getText().toString();
    				createQuestion(title, text);
    			}
    		}
    	);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public MapView getMapView() {
		return mapView;
	}

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}
}
