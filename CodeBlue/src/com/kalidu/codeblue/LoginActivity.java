package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // If there's a username in the preferences, fill in the username line
        final SharedPreferences preferences = MainActivity.getPreferences();
        String oldUsername = preferences.getString("username", "");
        ((EditText) findViewById(R.id.login_username)).setText(oldUsername);
        
        ((Button) findViewById(R.id.login)).setOnClickListener(
        	new OnClickListener(){

				public void onClick(View v) {
					Log.i("Click", "Login click");
					// Get the username and password from the form, and verify the credentials with the api.
					String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
					String password = ((EditText)findViewById(R.id.login_password)).getText().toString();
					
					// Add them to the params list to be used for the post request.
					List<NameValuePair> params = new ArrayList<NameValuePair>(0);
					params.add(new BasicNameValuePair("username", username));
					params.add(new BasicNameValuePair("password", password));
					
					// Make sure the credentials are valid
					String url = MainActivity.urlManager.getVerifyCredentialsURL();
					JSONObject j = MainActivity.getClient().httpPost(url, params);
					Log.i("LOGIN", j.toString());
					
					try {
						if(j.getBoolean("success")){
							String token = j.getString("token");
							// Store the username and token string in the shared preferences
							Editor editor = preferences.edit();
							editor.putString("username", username);
							editor.putString("token", token);
							editor.commit();
						
							// Redirect to home page
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							LoginActivity.this.startActivity(intent);
						}
						else {
							// Login failed, show errors and try again TODO
						}
					} catch (JSONException e) {
						// Login failed, show errors and try again TODO
						
					}
				}
        		
        	}
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    		case R.id.home:
    			Intent intent = new Intent(this, MainActivity.class);
				this.startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
    	}
    }
}
