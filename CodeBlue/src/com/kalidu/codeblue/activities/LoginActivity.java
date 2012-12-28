package com.kalidu.codeblue.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kalidu.codeblue.R;

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
    
    private void setListeners(){
    	((Button) findViewById(R.id.login)).setOnClickListener(
        	new OnClickListener(){

				public void onClick(View v) {
					// Get the username and password from the form, and verify the credentials with the api.
					String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
					String password = ((EditText)findViewById(R.id.login_password)).getText().toString();
					JSONObject j = MainActivity.getRequestManager().verifyCredentials(username, password);
					
					try {
						if(j.getBoolean("success")){
							// Store the username and token string in the shared preferences
							String token = j.getString("token");
							Editor editor = MainActivity.getPreferences().edit();
							
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
