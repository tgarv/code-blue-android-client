package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class Answer extends Commentable {

//	public static List<AnswerView> members = new ArrayList<AnswerView>(0);
	private final int answerId;
	private String text;
	private int score;

	public Answer(Context context, int userId, int answerId, String text, int score) {
		super(context, userId);
		this.answerId = answerId;
		this.setText(text);
		this.setScore(score);
//		AnswerView.members.add(this);
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

}
