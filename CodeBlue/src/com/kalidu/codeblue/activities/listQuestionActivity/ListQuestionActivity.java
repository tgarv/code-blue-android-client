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
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.MainActivity;
import com.kalidu.codeblue.activities.ViewQuestionActivity;
import com.kalidu.codeblue.models.Question;
import com.kalidu.codeblue.utils.BlueHttpClient;

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
    	String questionId = Integer.toString(((Question)(getListAdapter().getItem(position))).getQuestionId());
		String selectedValue = "Question " + questionId;
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
		
		String url = MainActivity.getUrlManager().getViewQuestionURL(questionId);
		
		BlueHttpClient client = MainActivity.getClient();
		JSONObject j = client.httpGet(url);
		
		Intent intent = new Intent(ListQuestionActivity.this, ViewQuestionActivity.class);
		intent.putExtra("questionJSON", j.toString());
		ListQuestionActivity.this.startActivity(intent);
	}
    
    // Make the GET request and add the questions to the List of Questions
    public void getQuestions(){
    	String url = MainActivity.getUrlManager().getListQuestionsURL();
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
