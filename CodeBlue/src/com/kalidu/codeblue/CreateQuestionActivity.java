package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CreateQuestionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        
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
    
    public void createQuestion(String title, String text){
    	String formDelta = "6";	// TODO make this not constant
    	
		String url = MainActivity.urlManager.getCreateQuestionURL();
		BlueHttpClient client = MainActivity.getClient();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("form_delta", formDelta));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("query", text));
		JSONObject j = client.httpPost(url, params);
		Log.i("JSON", j.toString());
    }
}
