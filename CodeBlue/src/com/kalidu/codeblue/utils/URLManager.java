package com.kalidu.codeblue.utils;

/**
 * Wrapper around URLs used for various requests, so that it's easier to change the URLs for things without changes
 * elsewhere.
 * 
 * @author tgarv
 * 
 */
public class URLManager {
//	private String base = "http://10.0.2.2:5000/api/";
	private String base = "http://codeblue.herokuapp.com/api/";
	private String androidBase = "http://10.0.2.2:5000/api/android/";
	private String login = "user_authenticate";
	private String setLocation = "user/set_location.json";
	private String listQuestions = "question";
	private String createQuestion = "question/create.json";
	private String viewQuestion = "question/%s/view.json";
	private String createAnswer = "answer/create.json";
	private String user = "user/%s";
	
	public String getLoginURL(){
		return base + login;
	}
	
	public String getSetLocationURL(){
		return androidBase + setLocation;
	}
	
	public String getListQuestionsURL(){
		return base + listQuestions;
	}
	
	public String getCreateQuestionURL(){
		return androidBase + createQuestion;
	}
	
	/**
	 * Constructs and returns the URL to view a particular question, specified by id.
	 * @param id the ID of the Question to be viewed.
	 * @return the URL to view that question.
	 */
	public String getViewQuestionURL(String id){
		return androidBase + String.format(viewQuestion, id);
	}
	
	public String getCreateAnswerURL(){
		return androidBase + createAnswer;
	}
	
	public String getUserURL(String username){
		return base + String.format(user, username);
	}
}
