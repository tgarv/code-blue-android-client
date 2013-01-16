package com.kalidu.codeblue.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.blueMapActivity.BlueMapActivity;
import com.kalidu.codeblue.activities.listQuestionActivity.ListQuestionActivity;

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
        setListeners();
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
     * Make a request through the RequestManager to create a new Question on the server.
     * @param title	The title to be used for this question.
     * @param text	The text/query/body of the question.
     */
    private void createQuestion(String title, String text){
    	String form_delta = "6";	// TODO make this changeable.
    	JSONObject j = MainActivity.getRequestManager().createQuestion(title, text, form_delta);
    	Log.i("Question", j.toString());
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
}
