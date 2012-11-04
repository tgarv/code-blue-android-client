package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

public class ListQuestionActivity extends Activity {
	private List<Question> questions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_question);
        
        questions = new ArrayList<Question>(0);
        LinearLayout root = ((LinearLayout) findViewById(R.id.questions_root));
        getQuestions();
        // TODO GET request to get the questions and answers, add them all to the view
//        List<Answer> answers = new ArrayList<Answer>(0);
//        Question q = new Question(this.getBaseContext(), 0, 0, "Test Title", "This is the text.", answers);
//        root.addView(q.getView());
        for(int i=0; i<this.questions.size(); i++){
        	Question q = questions.get(i);
        	root.addView(q.getView());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_question, menu);
        return true;
    }
    
    // Make the GET request and add the questions to the List of QuestionViews
    public void getQuestions(){
    	String url = MainActivity.urlManager.getListQuestionsURL();
    	JSONObject j = MainActivity.getClient().httpGet(url);
    	//Log.i("Questions", j.toString());
    	try {
			JSONArray questions = j.getJSONArray("questions");
			for(int i=0; i < questions.length(); i++){
				JSONObject question = questions.getJSONObject(i);
				Log.i("Question", question.toString());
				String title = question.getString("title");
				String text = question.getString("text");
				List<Answer> answers = new ArrayList<Answer>(0);
				ListQuestionActivity.this.questions.add(
						new Question(getBaseContext(), 0, 0, title, text, answers));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
