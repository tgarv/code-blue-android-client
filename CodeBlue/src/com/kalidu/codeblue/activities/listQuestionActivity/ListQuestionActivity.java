package com.kalidu.codeblue.activities.listQuestionActivity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.CreateQuestionActivity;
import com.kalidu.codeblue.activities.MainActivity;
import com.kalidu.codeblue.activities.ViewQuestionActivity;
import com.kalidu.codeblue.activities.blueMapActivity.BlueMapActivity;
import com.kalidu.codeblue.models.Question;
import com.kalidu.codeblue.utils.ActionBarBuilder;
import com.kalidu.codeblue.utils.NavBarBuilder;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;

public class ListQuestionActivity extends ListActivity {
	private ArrayList<Question> questions;
	private ActionBarBuilder actionBarBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_question);
        NavBarBuilder navBuilder = new NavBarBuilder(this);
        navBuilder.setListeners();
        this.actionBarBuilder = new ActionBarBuilder(this);
        this.actionBarBuilder.setListeners();
        Button button = (Button) findViewById(R.id.button_navbar_home);
        button.setBackgroundColor(0xFFFFFFFF);
        questions = new ArrayList<Question>(0);
//        getQuestions();
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	getQuestions();
    	this.actionBarBuilder.setNotificationsCount();	// Update the notifications indicator on the action bar
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
    		case R.id.menu_refresh:
    			getQuestions();
    			return true;
    		case R.id.menu_settings:
    			// TODO
    			return true;
			default:
				return super.onOptionsItemSelected(item);
    	}
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		//get selected items
    	int questionId = ((Question)(getListAdapter().getItem(position))).getQuestionId();
		String selectedValue = "Question " + questionId;
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(ListQuestionActivity.this, ViewQuestionActivity.class);
		intent.putExtra("questionId", questionId);
		ListQuestionActivity.this.startActivity(intent);
	}
    
    // Make the GET request and add the questions to the List of Questions
    public void getQuestions(){
    	MainActivity.setNotificationsCount(0);	// Reset notifications, because we just got the most recent questions
    	this.questions = new ArrayList<Question>();
    	HttpTaskHandler handler = new HttpTaskHandler(){
			public void taskSuccessful(JSONObject json) {
				handleResponse(json);
		        setListAdapter(new QuestionAdapter(ListQuestionActivity.this, questions));
			}

			public void taskFailed() {
				// TODO Auto-generated method stub
				
			}
    		
    	};
    	MainActivity.getRequestManager().listQuestions(handler);

    }
    
    private void handleResponse(JSONObject j){
    	Log.i("Questions", j.toString());
    	try {
			JSONArray questions = j.getJSONArray("questions");
			for(int i=0; i < questions.length(); i++){
				JSONObject question = questions.getJSONObject(i);
				Log.i("Question", question.toString());
				String title = question.getString("title");
				String text = question.getString("text");
				int id = question.getInt("id");
				ListQuestionActivity.this.questions.add(new Question(0, id, title, text));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
