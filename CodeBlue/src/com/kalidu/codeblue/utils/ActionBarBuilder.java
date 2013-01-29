package com.kalidu.codeblue.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.kalidu.codeblue.R;
import com.kalidu.codeblue.activities.CreateQuestionActivity;
import com.kalidu.codeblue.activities.MainActivity;
import com.kalidu.codeblue.activities.listQuestionActivity.ListQuestionActivity;

public class ActionBarBuilder {
	private Activity activity;

	public ActionBarBuilder(Activity activity){
		this.activity = activity;
		IntentFilter filter = new IntentFilter("com.kalidu.codeblue.NOTIFICATION");
		BroadcastReceiver receiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				setNotificationsCount();
			}
			
		};
		activity.registerReceiver(receiver, filter);
	}
	
	public void setListeners(){
		((Button) activity.findViewById(R.id.button_actionbar_notification)).setOnClickListener(
			new OnClickListener(){
				@Override
				public void onClick(View v) {
					MainActivity.setNotificationsCount(0);
					ActionBarBuilder.this.setNotificationsCount();
					Intent intent = new Intent(ActionBarBuilder.this.activity, ListQuestionActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					activity.startActivity(intent);
				}
			}
		);
		
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
	
	public void setNotificationsCount(){
		Button button = (Button) activity.findViewById(R.id.button_actionbar_notification);
		String count = Integer.toString(MainActivity.getNotificationsCount());
		button.setText(count);
	}
}
