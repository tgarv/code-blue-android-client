package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class ViewQuestionActivity extends Activity {

    private static LinearLayout answerRoot;
	private int questionId;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        
        JSONObject question = null;
        Bundle b = this.getIntent().getExtras();
        try {
			question = new JSONObject(b.getString("questionJSON"));
			setQuestionId(question.getInt("question_id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ViewQuestionActivity.setAnswerRoot(((LinearLayout) findViewById(R.id.answers)));
        setQuestionView(question);
        
        setCreateAnswerListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_question, menu);
        return true;
    }
    
    // Uses the JSON representation of the question and its answers to construct a view for the Question.
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
			root.removeAllViews();	// Remove anything that was there before
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

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public void setCreateAnswerListener(){
		// Set the click listener for the button to create a new answer
        ((Button) findViewById(R.id.create_answer)).setOnClickListener(
        	new OnClickListener(){
				public void onClick(View v) {
					String text = ((EditText) findViewById(R.id.new_answer_text)).getText().toString();
					List<NameValuePair> params = new ArrayList<NameValuePair>(0);
					params.add(new BasicNameValuePair("text", text));
					params.add(new BasicNameValuePair("question_id", Integer.toString(getQuestionId())));
					BlueHttpClient client = MainActivity.getClient();
					String url = MainActivity.urlManager.getCreateAnswerURL();
					JSONObject j = client.httpPost(url, params);
					
					try {
						if (j.getBoolean("success")){
							JSONObject question = j.getJSONObject("question");
							setQuestionView(question);
							((EditText)findViewById(R.id.new_answer_text)).setText("");
						}
						else{
							// TODO failed
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
        		
        	}
        );
	}
}
