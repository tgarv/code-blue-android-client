package com.kalidu.codeblue;


/**
 * 
 * @author tgarv
 * The model class for a Question
 */
public class Question {

	private final int questionId;
	private String title;
	private String text;
	private final int userId;

	public Question(int userId, int questionId, String title, String text) {
		this.userId = userId;
		this.questionId = questionId;
		this.setTitle(title);
		this.setText(text);
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
	
	public int getUserId() {
		return userId;
	}
}
