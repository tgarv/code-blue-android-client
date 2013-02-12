package com.kalidu.codeblue.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kalidu.codeblue.models.Question;

/**
 * 
 * @author tgarv
 *
 * Wrapper around methods to extract objects from JSON.
 */
public class JSONExtractor {
	public List<Question> extractQuestion(JSONObject j){
		List<Question> questions = new ArrayList<Question>(0);
		try {
			JSONArray jsonQuestions = j.getJSONArray("objects");
			for(int i=0; i < jsonQuestions.length(); i++){
				JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);
				JSONObject user = jsonQuestion.getJSONObject("author");
				JSONArray answers = jsonQuestion.getJSONArray("answers");
				int questionId = jsonQuestion.getInt("id");
				String username = user.getString("username");
				String title = jsonQuestion.getString("title");
				String text = jsonQuestion.getString("text");
				int latitude = jsonQuestion.getInt("latitude");
				int longitude = jsonQuestion.getInt("longitude");
				
				Question q = new Question(questionId, username, title, text, latitude, longitude);
				questions.add(q);
			}
		} catch(JSONException e) {
			// TODO
		}
		return questions;
	}
}
