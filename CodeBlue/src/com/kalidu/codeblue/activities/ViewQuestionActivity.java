package com.kalidu.codeblue.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.models.Answer;

public class ViewQuestionActivity extends Activity {

    private static LinearLayout answerRoot;
	private int questionId;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        JSONObject question = extractQuestionFromBundle();
        setQuestionView(question);
        
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_question, menu);
        return true;
    }
    
    /**
	 * Get the Bundle associated with the Intent that created this Activity, and extract the Question from that Bundle.
	 * Then, turn that into a JSONObject and set up the view.
	 */
	private JSONObject extractQuestionFromBundle(){
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
        return question;
	}
    
    /**
     * Uses the JSON representation of the question and its answers to construct a view for the Question.
     * 
     * @param question	The JSON representation of the question and its answers
     */
    public void setQuestionView(JSONObject question){
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
				// For each JSON representation of an answer, create a Java Answer object and then use that to
				// construct its view.
				JSONObject answerJSON = (JSONObject) answers.get(i);
				String answerText = answerJSON.getString("text");
				int score = answerJSON.getInt("score");
				int answerId = answerJSON.getInt("answer_id");
				int userId = 0;	// TODO add this to the API call
				
				// Construct an Answer object using the parameters extracted from the JSON object.
				Answer answer = new Answer(userId, answerId, answerText, score);
				// Get the view for this answer object
				LinearLayout answerView = answer.getView(getBaseContext());
				// And attach it to the view for the answers
				root.addView(answerView);
			}
		} catch (JSONException e) {
			// TODO
			// There are no answers, or the JSON object is badly formed.
			e.printStackTrace();
		}
    }
	
	public void setListeners(){
		// Set the click listener for the button to create a new answer
        ((Button) findViewById(R.id.create_answer)).setOnClickListener(
        	new OnClickListener(){
				public void onClick(View v) {
					String text = ((EditText) findViewById(R.id.new_answer_text)).getText().toString();
					int questionId = getQuestionId();
					// Make the request to create an answer.
					JSONObject j = MainActivity.getRequestManager().createAnswer(text, questionId);
					
					try {
						if (j.getBoolean("success")){
							// The Answer was successfully created, so update the view
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
}
