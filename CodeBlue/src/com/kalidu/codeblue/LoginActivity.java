package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        ((Button) findViewById(R.id.login)).setOnClickListener(
        	new OnClickListener(){

				public void onClick(View v) {
					// Get the username and password from the form, and verify the credentials with the api.
					String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
					String password = ((EditText)findViewById(R.id.login_password)).getText().toString();
					List<NameValuePair> params = new ArrayList<NameValuePair>(0);
					params.add(new BasicNameValuePair("username", username));
					params.add(new BasicNameValuePair("password", password));
					JSONObject j = MainActivity.getClient().httpPost(
							"http://10.0.2.2:5000/api/user/verify_credentials.json", params);
					Log.i("LOGIN", j.toString());
				}
        		
        	}
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}
