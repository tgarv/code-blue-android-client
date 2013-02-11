package com.kalidu.codeblue.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.listQuestionActivity.ListQuestionActivity;
import com.kalidu.codeblue.models.User;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;

/**
 * 
 * @author tgarv
 * Activity to handle logins. If there's a token already stored, the login is handled with that.
 * Otherwise, there's a form for the user to log in.
 *
 */
public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    /**
     * Set up click/touch listeners
     */
    private void setListeners(){
    	((Button) findViewById(R.id.login)).setOnClickListener(
        	new OnClickListener(){

				public void onClick(View v) {
					// Get the username and password from the form, and verify the credentials with the api.
					String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
					String password = ((EditText)findViewById(R.id.login_password)).getText().toString();
					
					// The handler to handle the API response after it returns
					HttpTaskHandler handler = new HttpTaskHandler(){
						public void taskSuccessful(JSONObject json) {
							Log.i("Login", json.toString());
							handleLoginResponse(json);
						}

						public void taskFailed() {
							// TODO Auto-generated method stub
							
						}
					};
					MainActivity.getRequestManager().login(handler, username, password);
					
					
				}
        		
        	}
        );
    }
    
    private void handleLoginResponse(JSONObject j){
    	try {
			if(j.getBoolean("success")){	// Successfully verified
				// Get the user ID, and make an API request for the full user object.
				JSONObject data = j.getJSONObject("data");
				int id = data.getInt("id");
				HttpTaskHandler handler = new HttpTaskHandler(){
					@Override
					public void taskSuccessful(JSONObject json) {
						handleUserResponse(json);
					}

					@Override
					public void taskFailed() {
						// TODO Auto-generated method stub
						
					}
				};
				MainActivity.getRequestManager().viewUser(handler, id);

			}
			else {
				// Login failed, show errors and try again
				// TODO
				Log.i("Login", "Failed(1): " + j.toString());
			}
		} catch (JSONException e) {
			// Login failed, show errors and try again
			// TODO
			e.printStackTrace();
			Log.i("Login", "Failed(2): " + j.toString());
		}
    }
    
    private void handleUserResponse(JSONObject json){
    	try{
	    	int id = json.getInt("id");
	    	String profileImageURL = json.getString("profile_img_url");
	    	String username = json.getString("username");
	    	String gcmRegId = json.getString("gcm_registration_id");
	    	int latitude = (int)(json.optDouble("latitude", 0.0) * 1e6);
	    	int longitude = (int)(json.optDouble("longitude", 0.0) * 1e6);
	    	
	    	User user = new User(id, username, profileImageURL, gcmRegId, latitude, longitude);
	    	MainActivity.setUser(user);
			Editor editor = MainActivity.getPreferences().edit();
			
			editor.putInt("userId", id);
			editor.putString("profileImageURL", profileImageURL);
			editor.putString("username", username);
			editor.putInt("latitude", latitude);
			editor.putInt("longitude", longitude);
			editor.commit();
		
			// Redirect to home page
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			LoginActivity.this.startActivity(intent);
    	} catch (JSONException e) {
    		// TODO
    		e.printStackTrace();
    	}
    }
}
