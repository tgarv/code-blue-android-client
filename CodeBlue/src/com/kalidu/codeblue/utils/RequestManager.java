package com.kalidu.codeblue.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

import com.kalidu.codeblue.activities.MainActivity;

public class RequestManager {
	public boolean createQuestion(String title, String query, String form_delta){
		String url = MainActivity.getUrlManager().getCreateQuestionURL();
		BlueHttpClient client = MainActivity.getClient();
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(0);
		params.add(new BasicNameValuePair("form_delta", form_delta));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("query", query));
		
		JSONObject j = client.httpPost(url, params);
		Log.i("JSON", j.toString());
		return true;
	}
}
