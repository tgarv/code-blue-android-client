package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

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
//        List<AnswerView> answers = new ArrayList<AnswerView>(0);
//        QuestionView qv = new QuestionView(this.getBaseContext(), 0, 0, "Test Title", "This is the text.", answers);
//        root.addView(qv.getView());
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
    	Log.i("Questions", j.toString());
    }
}
