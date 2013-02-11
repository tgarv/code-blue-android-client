package com.kalidu.codeblue.models;

import java.util.List;


/**
 * 
 * @author tgarv
 * The model class for a Question
 */
public class Question {

	private String text;
	private List<Answer> answers;
	private int id;
	private String username;
	private double latitude;
	private double longitude;
	private String title;
//	private User author;

	public Question(int questionId, String username, String title, String text, double latitude, 
			double longitude) {
		this.setUsername(username);
		this.setId(questionId);
		this.setTitle(title);
		this.setText(text);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(Answer answer){
		this.answers.add(answer);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
