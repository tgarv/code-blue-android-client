package com.kalidu.codeblue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class BlueHttpClient extends DefaultHttpClient {

	JSONObject getJSON(String url) {
		JSONObject result = new JSONObject();
		HttpResponse response = null;
		HttpGet request = new HttpGet();
		try {
			request.setURI(new URI(url));
		} catch (URISyntaxException e) {
			Log.e("Http", "URISyntaxException");
			return result;
		}
		try {
			// Execute the request
			response = this.execute(request);
			// Turn the response into a string
			String response_string = convertStreamToString(response.getEntity().getContent());
			try {
				// Parse the response string into a JSON object
				result = new JSONObject(response_string);
			} catch (JSONException e) {
				Log.e("JSON", "JSONException in BlueHttpClient");
			}
			
		} catch (ClientProtocolException e) {
			Log.e("Http", "ClientProtocolException");
			// TODO Auto-generated catch block
		} catch (IOException e) {
			Log.e("Http", "IOException");
			// TODO Auto-generated catch block
		}
		return result;

	}
	
	String convertStreamToString(java.io.InputStream is) {
	    try {
	        return new java.util.Scanner(is).useDelimiter("\\A").next();
	    } catch (java.util.NoSuchElementException e) {
	        return "";
	    }
	}
}

// try {
// HttpClient client = new DefaultHttpClient();
// HttpGet request = new HttpGet();
// request.setURI(new URI("http://10.0.2.2:5000/post/0"));
// response = client.execute(request);
// String s = convertStreamToString(response.getEntity().getContent());
// Log.d("GET", s);
// } catch (URISyntaxException e) {
// e.printStackTrace();
// } catch (ClientProtocolException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (IOException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// }
