package com.kalidu.codeblue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.kalidu.codeblue.activities.MainActivity;


public class GCMIntentService extends GCMBaseIntentService {
	
	public GCMIntentService(){
		super("164033855111");
	}

	@Override
	protected void onError(Context context, String errorId) {
		// TODO Auto-generated method stub
		Log.i("GCM", errorId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i("GCM", intent.getExtras().toString());
		Bundle b = intent.getExtras();
		String data = b.getString("data");
		if (data.equals("New message")){
			Log.i("GCM", "New message!");
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(500);
			// Handle a new message alert
			MainActivity.setNotificationsCount(MainActivity.getNotificationsCount()+1);
			Intent i = new Intent();
			i.setAction("com.kalidu.codeblue.NOTIFICATION");
			sendBroadcast(i);
		}
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		// TODO Auto-generated method stub
		Log.i("GCM", regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		// TODO Auto-generated method stub
		Log.i("GCM", regId);
	}
	
	@Override
	protected boolean onRecoverableError(Context context, String errorId){
		Log.i("GCM", errorId);
		return false;
	}

}
