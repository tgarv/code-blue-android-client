package com.kalidu.codeblue.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class AsyncHttpClient extends AsyncTask<HttpUriRequest, Void, JSONObject>{
	private DefaultHttpClient client = new DefaultHttpClient();

	@Override
	protected JSONObject doInBackground(HttpUriRequest... request) {
		HttpUriRequest r = request[0];
		HttpResponse response = null;
		JSONObject json = null;
		String response_string = "";
		
		// Execute the request asynchronously
		try {
			response = this.client.execute(r);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Convert the response into a JSONObject
		try {
			response_string = convertStreamToString(response.getEntity().getContent());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			json = new JSONObject(response_string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONObject json){
		if (json != null) {
			taskHandler.taskSuccessful(json);
		} else {
			taskHandler.taskFailed();
		}
	}
	
	public static interface HttpTaskHandler {
        void taskSuccessful(JSONObject json);
        void taskFailed();
    }

	HttpTaskHandler taskHandler;
	
	public void setTaskHandler(HttpTaskHandler taskHandler) {
	    this.taskHandler = taskHandler;
	}
	
	/**
	 * From StackOverflow. Takes a Stream and converts it to a String.
	 * 
	 * @param inputStream the InputStream to be converted
	 * @return the converted String
	 */
	String convertStreamToString(java.io.InputStream inputStream) {
	    try {
	        return new java.util.Scanner(inputStream).useDelimiter("\\A").next();
	    } catch (java.util.NoSuchElementException e) {
	        return "";
	    }
	}

}
