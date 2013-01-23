package com.kalidu.codeblue.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.blueMapActivity.BlueMapActivity;
import com.kalidu.codeblue.activities.listQuestionActivity.ListQuestionActivity;
import com.kalidu.codeblue.models.Answer;
import com.kalidu.codeblue.utils.ActionBarBuilder;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;

public class ViewQuestionActivity extends Activity {

    private static LinearLayout answerRoot;
	private int questionId;
	private JSONObject question;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        ActionBarBuilder builder = new ActionBarBuilder(this);
        builder.setListeners();
        extractQuestionFromBundle();
        
        
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
	 * Get the Bundle associated with the Intent that created this Activity, and extract the Question from that Bundle.
	 * Then, turn that into a JSONObject and set up the view.
	 */
	private void extractQuestionFromBundle(){
        Bundle b = this.getIntent().getExtras();
        final int questionId = b.getInt("questionId");
        
        HttpTaskHandler handler = new HttpTaskHandler(){
			public void taskSuccessful(JSONObject json) {
				ViewQuestionActivity.setAnswerRoot(((LinearLayout) findViewById(R.id.answers)));
				setQuestion(json);
				setQuestionId(questionId);
				setQuestionView(json);
			}

			public void taskFailed() {
				// TODO Auto-generated method stub
				
			}
        	
        };
        
        MainActivity.getRequestManager().viewQuestion(handler, questionId);
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
					HttpTaskHandler handler = new HttpTaskHandler(){
						public void taskSuccessful(JSONObject json) {
							try {
								if (json.getBoolean("success")){
									// The Answer was successfully created, so update the view
									JSONObject question = json.getJSONObject("question");
									setQuestionView(question);
									((EditText)findViewById(R.id.new_answer_text)).setText("");
								}
								else{
									// TODO Create answer failed
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						public void taskFailed() {
							// TODO Auto-generated method stub
							
						}
					};
					// Make the request to create an answer.
					MainActivity.getRequestManager().createAnswer(handler, text, questionId);
					
					
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

	public JSONObject getQuestion() {
		return question;
	}

	public void setQuestion(JSONObject question) {
		this.question = question;
	}
}
