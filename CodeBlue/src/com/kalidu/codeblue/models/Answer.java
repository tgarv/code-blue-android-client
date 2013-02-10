package com.kalidu.codeblue.models;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author tgarv
 * The model class for an Answer.
 */
public class Answer {

	private int id;
	private String text;
	private int userId;
	private int questionId;

	public Answer(int answerId, int userId, String text, int questionId) {
		this.setId(answerId);
		this.setUserId(userId);
		this.setText(text);
		this.setQuestionId(questionId);
	}

	
	public LinearLayout getView(Context c){
		LinearLayout ll = new LinearLayout(c);
		TextView text = new TextView(c);
		text.setText(this.getText());
		ll.addView(text);
		ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		return ll;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getQuestionId() {
		return questionId;
	}


	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

}
