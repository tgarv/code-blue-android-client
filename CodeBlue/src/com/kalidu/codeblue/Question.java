package com.kalidu.codeblue;

import java.util.List;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Question extends Commentable {

	private final int questionId;
	private String title;
	private String text;
	private List<Answer> answers;

	public Question(Context context, int userId, int questionId, String title, String text, 
			List<Answer> answers) {
		super(context, userId);
		// TODO Auto-generated constructor stub
		this.questionId = questionId;
		this.setTitle(title);
		this.setText(text);
		this.answers = answers;
	}

	public int getQuestionId() {
		return questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}
	
	public LinearLayout getView(){
		// TODO implement this
		LinearLayout ll = new LinearLayout(this.getContext());
		TextView title = new TextView(this.getContext());
		title.setText(this.getTitle());
		ll.addView(title);
		TextView text = new TextView(this.getContext());
		text.setText(this.getText());
		ll.addView(text);
		return ll;
	}

}
