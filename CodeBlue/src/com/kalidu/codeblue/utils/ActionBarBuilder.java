package com.kalidu.codeblue.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

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
					View searchView = activity.findViewById(R.id.searchbar);
					if (searchView != null){
						Log.i("TODO", "Search");
					}
					
					else{
						/*Display display = activity.getWindowManager().getDefaultDisplay();
						int width = display.getWidth();
						
						RelativeLayout searchBarContainer = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.searchbar, null);
						LinearLayout actionBar = (LinearLayout) activity.findViewById(R.id.actionbar);
						actionBar.addView(searchBarContainer, 1);
						EditText searchBar = (EditText) activity.findViewById(R.id.searchbar);
	
						View addButton = activity.findViewById(R.id.button_actionbar_add);
						addButton.setVisibility(View.GONE);
						View logo = activity.findViewById(R.id.logo);
						logo.setVisibility(View.GONE);
						
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
						
						params.height = (int)(0.75*addButton.getHeight());
				        params.width = width - addButton.getWidth();
						params.addRule(RelativeLayout.CENTER_VERTICAL, R.id.searchbar_container);
				        
				        searchBar.setLayoutParams(params);
	
				        searchBar.setPadding(10, 0, 10, 0);
				        searchBar.setSingleLine(true);
				        
				        Button closeButton = (Button) activity.findViewById(R.id.button_clear_search);
				        
				        params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				        params.addRule(RelativeLayout.CENTER_VERTICAL, R.id.searchbar);
				        params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.searchbar);
				        
				        closeButton.setLayoutParams(params);*/
					}
				}
			}
		);
	}
}
