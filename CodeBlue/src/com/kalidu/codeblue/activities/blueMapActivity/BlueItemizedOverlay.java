package com.kalidu.codeblue.activities.blueMapActivity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.kalidu.codeblue.activities.ViewQuestionActivity;

@SuppressWarnings("rawtypes")
public class BlueItemizedOverlay extends ItemizedOverlay {
	private ArrayList<BlueOverlayItem> mOverlays = new ArrayList<BlueOverlayItem>();
	private Context mContext;
	
	public BlueItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void addOverlay(BlueOverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected boolean onTap(int index) {
	  final BlueOverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	  builder.setTitle(item.getTitle());
	  builder.setMessage(item.getSnippet());
	  
	  builder.setNeutralButton("View Answers", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
        	//get selected items
          	String questionId = Integer.toString(item.getId());
      		
      		Intent intent = new Intent(mContext, ViewQuestionActivity.class);
      		intent.putExtra("questionId", questionId);
      		mContext.startActivity(intent);
          }
      });
	  
	  AlertDialog d = builder.create();
	  d.setCanceledOnTouchOutside(true);	// The dialog will close if you tap outside of it.
	  d.show();
	  return true;
	}

}
