package com.kalidu.codeblue.utils;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.kalidu.codeblue.activities.MainActivity;

import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

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
		} catch (HttpHostConnectException e){
			// TOTO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Store the Set-Cookie information for the session
		Header[] headers = response.getHeaders("Set-Cookie");
		if (headers.length >0 ){
			String header = headers[0].getValue();
			String[] parts = header.split("\"");
			String session = parts[0] + "\"" + parts[1] + "\"";
			Log.i("TEST", session);
			Editor editor = MainActivity.getPreferences().edit();
			editor.putString("session", session);
			editor.commit();
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
