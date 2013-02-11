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

import android.location.Location;
import android.util.Log;

import com.kalidu.codeblue.activities.MainActivity;
import com.kalidu.codeblue.utils.AsyncHttpClient.HttpTaskHandler;

public class RequestManager {
	private HttpUriRequest getGETRequest(String url){
		HttpGet request = new HttpGet();
		try {
			request.setURI(new URI(url));
		} catch (URISyntaxException e) {
			Log.e("Http", "URISyntaxException");
			return null;
		}
		return request;
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
		return post;
	}
	
	public void createQuestion(HttpTaskHandler handler, String title, String query, String form_delta) {
		String url = MainActivity.getUrlManager().getCreateQuestionURL();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("form_delta", form_delta));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("query", query));
		params.add(new BasicNameValuePair("token", MainActivity.getPreferences().getString("token", "")));
		
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
	
	public void createAnswer(HttpTaskHandler handler, String text, int questionId){
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("text", text));
		params.add(new BasicNameValuePair("question_id", Integer.toString(questionId)));
		params.add(new BasicNameValuePair("token", MainActivity.getPreferences().getString("token", "")));
		
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
		params.add(new BasicNameValuePair("elevation", Double.toString(location.getAltitude())));
		params.add(new BasicNameValuePair("token", MainActivity.getPreferences().getString("token", "")));
		
		String url = MainActivity.getUrlManager().getSetLocationURL();

		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getPOSTRequest(url, params);
		task.execute(request);
	}
	
	public void registerGCM(HttpTaskHandler handler, String regId){
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("token", MainActivity.getPreferences().getString("token", "")));
		params.add(new BasicNameValuePair("registration_id", regId));
		
		String url = MainActivity.getUrlManager().getRegisterGcmURL();
		
		AsyncHttpClient task = new AsyncHttpClient();
		task.setTaskHandler(handler);
		HttpUriRequest request = getPOSTRequest(url, params);
		task.execute(request);
	}
}
