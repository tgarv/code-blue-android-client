package com.kalidu.codeblue.activities;

import org.json.JSONArray;
import org.json.JSONException;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.kalidu.codeblue.R;
import com.kalidu.codeblue.ViewQuestionItemizedOverlay;
import com.kalidu.codeblue.activities.listQuestionActivity.ListQuestionActivity;
import com.kalidu.codeblue.activities.listQuestionMapActivity.ListQuestionMapActivity;
import com.kalidu.codeblue.models.Answer;
import com.kalidu.codeblue.models.User;
import com.kalidu.codeblue.utils.ActionBarBuilder;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;
import com.kalidu.codeblue.utils.NavBarBuilder;

public class ViewQuestionActivity extends MapActivity {

    private static LinearLayout answerRoot;
	private int questionId;
	private JSONObject question;
	private MapView mapView;
	private ViewQuestionItemizedOverlay userOverlay, questionOverlay, answerOverlay;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_question);
        
        // Initialize the navbar and action bar
        NavBarBuilder navBuilder = new NavBarBuilder(this);
        navBuilder.setListeners();
        ActionBarBuilder actionBuilder = new ActionBarBuilder(this);
        actionBuilder.setListeners();
        
        extractQuestionFromBundle();
        
        setListeners();
        this.setMapView((MapView) findViewById(R.id.mapview));
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
	 * Get the Bundle associated with the Intent that created this Activity, and extract the Question from that Bundle.
	 * Then, turn that into a JSONObject and set up the view.
	 */
	private void extractQuestionFromBundle(){
        Bundle b = this.getIntent().getExtras();
        Log.i("TEST", Boolean.toString(b.containsKey("questionId")));
        final int questionId = b.getInt("questionId");
        Log.i("TEST", Integer.toString(questionId));
        
        HttpTaskHandler handler = new HttpTaskHandler(){
			public void taskSuccessful(JSONObject json) {
				Log.i("TEST", json.toString());
				ViewQuestionActivity.setAnswerRoot(((LinearLayout) findViewById(R.id.answers)));
				setQuestion(json);
				setQuestionId(questionId);
				setQuestionView(json);
				mapInit();
			}

			public void taskFailed() {
				// TODO Auto-generated method stub
				Log.i("TEST", "failed");
			}
        	
        };
        
        MainActivity.getRequestManager().viewQuestion(handler, questionId);
	}
    
    /**
     * Uses the JSON representation of the question and its answers to construct a view for the Question.
     * 
     * @param question	The JSON representation of the question and its answers
     */
    public void setQuestionView(JSONObject question){
    	TextView title = ((TextView) findViewById(R.id.view_question_title));
    	TextView text = ((TextView) findViewById(R.id.view_question_text));
    	LinearLayout root = ViewQuestionActivity.getAnswerRoot();
		root.removeAllViews();	// Remove anything that was there before
    	
    	try {
			title.setText(question.getString("title"));
			text.setText(question.getString("text"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			JSONArray answers = question.getJSONArray("answers");
			
			for(int i=0; i<answers.length(); i++){
				// For each JSON representation of an answer, create a Java Answer object and then use that to
				// construct its view.
				JSONObject answerJSON = (JSONObject) answers.get(i);
				int answerId = answerJSON.getInt("id");
				addAnswer(root, answerId);
				
			}
		} catch (JSONException e) {
			// TODO
			// There are no answers, or the JSON object is badly formed.
			e.printStackTrace();
		}
    }
    
    /**
     * Make an HTTP request to get the answer with the given id, and append it to root.
     * @param root the LinearLayout to append the answer view to
     * @param id the ID of the answer to get from the server
     */
    private void addAnswer(final LinearLayout root, int id){
    	HttpTaskHandler handler = new HttpTaskHandler(){
			@Override
			public void taskSuccessful(JSONObject json) {
				try{
					// Construct an Answer object using the parameters extracted from the JSON object.
					int answerId = json.getInt("id");
					JSONObject user = json.getJSONObject("author");
					int userId = user.getInt("id");
					String text = json.getString("text");
					int latitude = (int)(json.getDouble("latitude") * 1e6);
					int longitude = (int)(json.getDouble("longitude") * 1e6);
					Answer answer = new Answer(answerId, userId, text, latitude, longitude);
					// Get the view for this answer object
					LinearLayout answerView = answer.getView(getBaseContext());
					// And attach it to the view for the answers
					root.addView(answerView);
					addAnswerToMap(latitude, longitude);
				} catch (JSONException e){
					// TODO
					e.printStackTrace();
				}
			}
			@Override
			public void taskFailed() {
				// TODO Auto-generated method stub
				
			}
    	};
    	MainActivity.getRequestManager().viewAnswer(handler, id);
    	
    }
	
	public void setListeners(){
		// Set the click listener for the button to create a new answer
        ((Button) findViewById(R.id.create_answer)).setOnClickListener(
        	new OnClickListener(){
				public void onClick(View v) {
					String text = ((EditText) findViewById(R.id.new_answer_text)).getText().toString();
					int questionId = getQuestionId();
					GeoPoint newAnswerLocation = getNewAnswerLocation();
					int latitude = newAnswerLocation.getLatitudeE6();
					int longitude = newAnswerLocation.getLongitudeE6();
					HttpTaskHandler handler = new HttpTaskHandler(){
						public void taskSuccessful(JSONObject json) {
							try {
								if (json.getBoolean("success")){
									// The Answer was successfully created, so update the view by restarting the activity
									finish();
									startActivity(getIntent());
								}
								else{
									// TODO Create answer failed
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
					// Make the request to create an answer.
					MainActivity.getRequestManager().createAnswer(handler, text, latitude, longitude, questionId);
					
//					// Make the map, navbar and action bar visible again
//					MapView mv = (MapView) findViewById(R.id.mapview);
//					mv.setVisibility(View.VISIBLE);
//					LinearLayout actionBar = (LinearLayout) findViewById(R.id.actionbar);
//					actionBar.setVisibility(View.VISIBLE);
//					LinearLayout navBar = (LinearLayout) findViewById(R.id.navbar);
//					navBar.setVisibility(View.VISIBLE);
//					// Hide the keyboard
//					InputMethodManager imm = (InputMethodManager)getSystemService(
//							Context.INPUT_METHOD_SERVICE);
//					imm.hideSoftInputFromWindow(mv.getWindowToken(), 0);
				}
        		
        	}
        );
        
        ((EditText) findViewById(R.id.new_answer_text)).setOnClickListener(
    		new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					MapView mv = getMapView();
					mv.setVisibility(View.GONE);
					LinearLayout actionBar = (LinearLayout) findViewById(R.id.actionbar);
					actionBar.setVisibility(View.GONE);
					LinearLayout navBar = (LinearLayout) findViewById(R.id.navbar);
					navBar.setVisibility(View.GONE);
				}
    		}
    	);
	}
	
	public void mapInit(){
		MapView mapView = getMapView();
		
		JSONObject question = getQuestion();
		int latitude = 0;
		int longitude = 0;
		try {
			latitude = (int)(question.getDouble("latitude")*1e6);
			longitude = (int)(question.getDouble("longitude")*1e6);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Initialize the question overlay
		GeoPoint questionLocation = new GeoPoint(latitude, longitude);
		Drawable questionMarker = getResources().getDrawable(R.drawable.marker);
		questionMarker.setBounds(-questionMarker.getIntrinsicWidth()/2, -questionMarker.getIntrinsicHeight(), 
        		questionMarker.getIntrinsicWidth()/2, 0);
		mapView.getOverlays().add(new ViewQuestionItemizedOverlay(questionMarker, questionLocation, false));
		
		// Initialize the user overlay
		User user = MainActivity.getUser();
		int userLatitude = user.getLatitude();
        int userLongitude = user.getLongitude();
        GeoPoint userLocation = new GeoPoint(userLatitude, userLongitude);
        Drawable userMarker = getResources().getDrawable(R.drawable.marker_green);
        userMarker.setBounds(-userMarker.getIntrinsicWidth()/2, -userMarker.getIntrinsicHeight(), 
        		userMarker.getIntrinsicWidth()/2, 0);
        mapView.getOverlays().add(new ViewQuestionItemizedOverlay(userMarker, userLocation, false));
        
        // Initialize the answer overlay
        Drawable answerMarker = getResources().getDrawable(R.drawable.marker_blue);
        answerMarker.setBounds(-answerMarker.getIntrinsicWidth()/2, -answerMarker.getIntrinsicHeight(), 
        		answerMarker.getIntrinsicWidth()/2, 0);
        // It's ok to pass in the user location as the first location in this overlay, because that one determines
        // where the answer will be created which defaults to the user location.
        mapView.getOverlays().add(new ViewQuestionItemizedOverlay(answerMarker, userLocation, true));
        
		
		mapView.setBuiltInZoomControls(true);
		MapController mc = mapView.getController();
		mc.setZoom(16);
		mc.setCenter(questionLocation);
	}
	
	public void addAnswerToMap(int latitude, int longitude){
		MapView mapView = getMapView();
		ViewQuestionItemizedOverlay overlay = (ViewQuestionItemizedOverlay) mapView.getOverlays().get(2);
		overlay.addItem(new GeoPoint(latitude, longitude));
	}
	
	public GeoPoint getNewAnswerLocation(){
		MapView mapView = getMapView();
		ViewQuestionItemizedOverlay overlay = (ViewQuestionItemizedOverlay) mapView.getOverlays().get(2);
		return overlay.getTempMarkerLocation();
	}
	
	public static LinearLayout getAnswerRoot() {
		return answerRoot;
	}

	public static void setAnswerRoot(LinearLayout answerRoot) {
		ViewQuestionActivity.answerRoot = answerRoot;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public JSONObject getQuestion() {
		return question;
	}

	public void setQuestion(JSONObject question) {
		this.question = question;
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

	public ViewQuestionItemizedOverlay getUserOverlay() {
		return userOverlay;
	}

	public void setUserOverlay(ViewQuestionItemizedOverlay userOverlay) {
		this.userOverlay = userOverlay;
	}

	public ViewQuestionItemizedOverlay getQuestionOverlay() {
		return questionOverlay;
	}

	public void setQuestionOverlay(ViewQuestionItemizedOverlay questionOverlay) {
		this.questionOverlay = questionOverlay;
	}

	public ViewQuestionItemizedOverlay getAnswerOverlay() {
		return answerOverlay;
	}

	public void setAnswerOverlay(ViewQuestionItemizedOverlay answerOverlay) {
		this.answerOverlay = answerOverlay;
	}
}
