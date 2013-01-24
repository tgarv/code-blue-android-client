package com.kalidu.codeblue.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.CreateQuestionActivity;

public class ActionBarBuilder {
	private Activity activity;

	public ActionBarBuilder(Activity activity){
		this.activity = activity;
	}
	
	public void setListeners(){
		((ImageButton) activity.findViewById(R.id.button_actionbar_add)).setOnClickListener(
			new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(ActionBarBuilder.this.activity, CreateQuestionActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					activity.startActivity(intent);
				}
				
			}
		);
		
		((ImageButton) activity.findViewById(R.id.button_actionbar_search)).setOnClickListener(
			new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Log.i("TODO", "Search");
				}
				
			}
		);
	}
}
