package com.kalidu.codeblue.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.kalidu.codeblue.activities.MainActivity;
import com.kalidu.codeblue.models.User;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;

public class RequestManager {
	/**
	 * Adds a Cookie containing the session information to the header of the request. The cookie value is 
	 * retrieved from the SharedPreferences object where it was stored after login.
	 * 
	 * @param request The request to add the session cookie to
	 * @return The same request, but with the added header information
	 */
	private HttpUriRequest addSessionCookie(HttpUriRequest request){
		SharedPreferences prefs = MainActivity.getPreferences();
		String session = prefs.getString("session", "");
		
		request.addHeader("Cookie", session);
		return request;
	}
	
	private HttpUriRequest getGETRequest(String url){
		HttpGet request = new HttpGet();
		try {
			request.setURI(new URI(url));
		} catch (URISyntaxException e) {
			Log.e("Http", "URISyntaxException");
			return null;
		}
		return addSessionCookie(request);
	}
	
	private HttpUriRequest getPOSTRequest(String url, List<NameValuePair> params){
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return addSessionCookie(post);
	}
	
	public void createQuestion(HttpTaskHandler handler, String title, String text, double latitude, 
			double longitude) {
		
		String url = MainActivity.getUrlManager().getCreateQuestionURL();
		Log.i("Create question", url);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("text", text));
		params.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
		params.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
		
		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getPOSTRequest(url, params);
		task.execute(request);
	}
	
	public void login(HttpTaskHandler handler, String username, String password){
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("user_identifier", username));
		params.add(new BasicNameValuePair("password", password));
		
		String url = MainActivity.getUrlManager().getLoginURL();
		
		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getPOSTRequest(url, params);
		task.execute(request);
	}
	
	public void viewAnswer(HttpTaskHandler handler, int answerId){
		String url = MainActivity.getUrlManager().getViewAnswerURL(Integer.toString(answerId));
  		
		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getGETRequest(url);
		task.execute(request);
	}
	
	public void createAnswer(HttpTaskHandler handler, String text, int latitude, int longitude, int questionId){
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("text", text));
		params.add(new BasicNameValuePair("latitude", Integer.toString(latitude)));
		params.add(new BasicNameValuePair("longitude", Integer.toString(longitude)));
		params.add(new BasicNameValuePair("question_id", Integer.toString(questionId)));
		
		String url = MainActivity.getUrlManager().getCreateAnswerURL();

		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getPOSTRequest(url, params);
		task.execute(request);
	}
	
	public void viewQuestion(HttpTaskHandler handler, int questionId){
		String url = MainActivity.getUrlManager().getViewQuestionURL(Integer.toString(questionId));
  		
		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getGETRequest(url);
		task.execute(request);
	}
	
	public void listQuestions(HttpTaskHandler handler){
		String url = MainActivity.getUrlManager().getListQuestionsURL();
		
		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getGETRequest(url);
		task.execute(request);
	}
	
	public void setLocation(HttpTaskHandler handler, Location location){
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("latitude", Double.toString(location.getLatitude())));
		params.add(new BasicNameValuePair("longitude", Double.toString(location.getLongitude())));
		
		String url = MainActivity.getUrlManager().getSetLocationURL();

		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getPOSTRequest(url, params);
		task.execute(request);
	}
	
	public void viewUser(HttpTaskHandler handler, int userId){
		String url = MainActivity.getUrlManager().getUserURL(userId);
		
		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getGETRequest(url);
		task.execute(request);
	}
	
	public void updateUserGCM(HttpTaskHandler handler, User user){
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("registration_id", user.getGcmRegId()));
		
		String url = MainActivity.getUrlManager().getUserGcmURL();
		
		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getPOSTRequest(url, params);
		task.execute(request);
	}
}
