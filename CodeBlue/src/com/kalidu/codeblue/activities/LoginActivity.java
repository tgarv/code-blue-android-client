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
							handleResponse(json);
						}

						public void taskFailed() {
							// TODO Auto-generated method stub
							
						}
					};
					MainActivity.getRequestManager().verifyCredentials(handler, username, password);
					
					
				}
        		
        	}
        );
    }
    
    private void handleResponse(JSONObject j){
    	try {
    		JSONObject status = j.getJSONObject("status");
    		Log.i("Login", status.toString());
			if(status.getBoolean("success")){	// Successfully verified
				// Store the username and token string in the shared preferences
				JSONObject data = status.getJSONObject("data");
				String token = data.getString("token");
				String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
				Editor editor = MainActivity.getPreferences().edit();
				
				editor.putString("username", username);
				editor.putString("token", token);
				editor.commit();
			
				// Redirect to home page
				Intent intent = new Intent(LoginActivity.this, ListQuestionActivity.class);
				LoginActivity.this.startActivity(intent);
			}
			else {
				// Login failed, show errors and try again
				// TODO
				Log.i("Login", "Failed: " + j.toString());
			}
		} catch (JSONException e) {
			// Login failed, show errors and try again
			// TODO
			e.printStackTrace();
			Log.i("Login", "Failed: " + j.toString());
		}
    }
}
