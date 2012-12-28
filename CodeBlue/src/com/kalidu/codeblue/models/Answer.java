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

	private final int answerId;
	private String text;
	private int score;
	private final int userId;

	public Answer(int userId, int answerId, String text, int score) {
		this.userId = userId;
		this.answerId = answerId;
		this.setText(text);
		this.setScore(score);
	}

	public int getAnswerId() {
		return answerId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getUserId() {
		return userId;
	}
	
	public LinearLayout getView(Context c){
		LinearLayout ll = new LinearLayout(c);
		TextView text = new TextView(c);
		text.setText(this.getText());
		ll.addView(text);
		ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		return ll;
	}

}
