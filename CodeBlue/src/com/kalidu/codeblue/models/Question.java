package com.kalidu.codeblue.models;


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
	private String username;
	private String datetime;

	public Question(int userId, int questionId, String title, String text, String username, String datetime) {
		this.userId = userId;
		this.questionId = questionId;
		this.setTitle(title);
		this.setText(text);
		this.setUsername(username);
		this.setDatetime(datetime);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
}
