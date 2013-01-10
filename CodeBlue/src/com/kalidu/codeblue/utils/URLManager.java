package com.kalidu.codeblue.utils;

/**
 * Wrapper around URLs used for various requests, so that it's easier to change the URLs for things without changes
 * elsewhere.
 * 
 * @author tgarv
 * 
 */
public class URLManager {
	private String base = "http://10.0.2.2:5000/api/";
	private String androidBase = "http://10.0.2.2:5000/api/android/";
	private String verifyToken = "verify_token.json";
	private String verifyCredentials = "user/verify_credentials.json";
	private String setLocation = "user/set_location.json";
	private String listQuestions = "question/list.json";
	private String createQuestion = "question/create.json";
	private String viewQuestion = "question/%s/view.json";
	private String createAnswer = "answer/create.json";
	
	public String getVerifyTokenURL(){
		return androidBase + verifyToken;
	}
	
	public String getVerifyCredentialsURL(){
		return base + verifyCredentials;
	}
	
	public String getSetLocationURL(){
		return androidBase + setLocation;
	}
	
	public String getListQuestionsURL(){
		return androidBase + listQuestions;
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
}
