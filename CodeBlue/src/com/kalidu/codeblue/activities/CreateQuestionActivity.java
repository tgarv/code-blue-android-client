package com.kalidu.codeblue.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.R.id;
import com.kalidu.codeblue.R.layout;
import com.kalidu.codeblue.R.menu;
import com.kalidu.codeblue.utils.BlueHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author tgarv
 * The activity that allows a user to create a new question using a form.
 * 
 */
public class CreateQuestionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        addListeners();
    }
    
    private void addListeners(){
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_question, menu);
        return true;
    }
    
    private void createQuestion(String title, String text){
    	String form_delta = "6";	// TODO make this changeable.
    	MainActivity.getRequestManager().createQuestion(title, text, form_delta);
    }
}
