package com.kalidu.codeblue;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListQuestionActivity extends ListActivity {
	private ArrayList<Question> questions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questions = new ArrayList<Question>(0);
        getQuestions();

        setListAdapter(new QuestionAdapter(this, questions));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_question, menu);
        return true;
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		//get selected items
		String selectedValue = "Question " + Integer.toString(((Question)(getListAdapter().getItem(position))).getQuestionId());
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
 
	}
    
    // Make the GET request and add the questions to the List of QuestionViews
    public void getQuestions(){
    	String url = MainActivity.urlManager.getListQuestionsURL();
    	JSONObject j = MainActivity.getClient().httpGet(url);
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
