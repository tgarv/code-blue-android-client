package com.kalidu.codeblue;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuestionAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;

	private int layout;
	
	private static ArrayList<Question> questions;
	
	public QuestionAdapter(Context context, ArrayList<Question> questions){
		this.mInflater = LayoutInflater.from(context);
		QuestionAdapter.questions = questions;
		this.layout = R.layout.question_layout;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		// TODO implement this
		Question question = questions.get(position);
		View questionView = mInflater.inflate(layout, parent, false);
		
		TextView title = (TextView) questionView.findViewById(R.id.question_title);
		title.setText(question.getTitle());
		
		TextView text = (TextView) questionView.findViewById(R.id.question_text);
		text.setText(question.getText());
		
		return questionView;
	}

	public int getCount() {
		return questions.size();
	}

	public Question getItem(int index) {
		return questions.get(index);
	}

	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

}
