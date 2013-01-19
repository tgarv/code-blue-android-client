package com.kalidu.codeblue;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;


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
		ActivityManager activityManager = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
	    List<RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
	    for(int i = 0; i < procInfos.size(); i++){
	    	Log.i("TEST", procInfos.get(i).processName);
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
