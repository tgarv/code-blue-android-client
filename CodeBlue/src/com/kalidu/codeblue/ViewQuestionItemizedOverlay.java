package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class ViewQuestionItemizedOverlay extends ItemizedOverlay{
	private List<OverlayItem> items = new ArrayList<OverlayItem>();
	private boolean answers;

	public ViewQuestionItemizedOverlay(Drawable defaultMarker, GeoPoint location, boolean answers) {
		super(defaultMarker);
        this.addItem(location);
        this.answers = answers;
		populate();
	}

	@Override
    protected OverlayItem createItem(int i) {
		return(items.get(i));
    }
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.items.size();
	}
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView){
		if (answers){
			items.remove(0);
			// TODO this is kind of a hack
			items.add(0, new OverlayItem(p, "", ""));	// Add this back in as the temp marker
			populate();
			return false;
		}
		return true;
		
	}
	
	public void addItem(GeoPoint point){
		this.items.add(new OverlayItem(point, "String1", "String2"));
		populate();
	}
	
	public GeoPoint getTempMarkerLocation(){
		if (answers){	// Should only be called if this is the one for answers
			return this.items.get(0).getPoint();
		}
		else {
			return this.items.get(0).getPoint();
		}
	}

}
