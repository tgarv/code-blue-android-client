package com.kalidu.codeblue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewQuestionActivity extends Activity {

    private static LinearLayout answerRoot;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        
        JSONObject question = null;
        Bundle b = this.getIntent().getExtras();
        try {
			question = new JSONObject(b.getString("questionJSON"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ViewQuestionActivity.setAnswerRoot(((LinearLayout) findViewById(R.id.answers)));
        setQuestionView(question);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_question, menu);
        return true;
    }
    
    public void setQuestionView(JSONObject question){
    	Log.i("QuestionJSON", question.toString());
    	
    	TextView title = ((TextView) findViewById(R.id.view_question_title));
    	TextView text = ((TextView) findViewById(R.id.view_question_text));
    	
    	try {
			title.setText(question.getString("title"));
			text.setText(question.getString("text"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			JSONArray answers = question.getJSONArray("answers");
			LinearLayout root = ViewQuestionActivity.getAnswerRoot();
			for(int i=0; i<answers.length(); i++){
				// For each JSON representation of an answer, create a Java Answer object and then use that
				// to get its view.
				JSONObject a = (JSONObject) answers.get(i);
				String answerText = a.getString("text");
				Log.i("DEBUG", answerText);
				int score = a.getInt("score");
				int answerId = a.getInt("answer_id");
				int userId = 0;	// TODO add this to the API call
				Answer answer = new Answer(userId, answerId, answerText, score);
				// Get the view for this answer object
				LinearLayout answerView = answer.getView(getBaseContext());
				// And attach it to the view for the answers
				root.addView(answerView);
			}
		} catch (JSONException e) {
			// There are no answers?
			e.printStackTrace();
		}
    }

	public static LinearLayout getAnswerRoot() {
		return answerRoot;
	}

	public static void setAnswerRoot(LinearLayout answerRoot) {
		ViewQuestionActivity.answerRoot = answerRoot;
	}
}
