package com.kalidu.codeblue;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.kalidu.codeblue.activities.MainActivity;

@SuppressWarnings("rawtypes")
public class ViewQuestionItemizedOverlay extends ItemizedOverlay{
	private List<OverlayItem> items = new ArrayList<OverlayItem>();

	public ViewQuestionItemizedOverlay(Drawable defaultMarker, GeoPoint questionLocation) {
		super(defaultMarker);
        SharedPreferences preferences = MainActivity.getPreferences();
        
        // Get user location and add it as a pin
        int latitude = (int) ((preferences.getFloat("latitude", 0))*1e6);
        int longitude = (int) ((preferences.getFloat("longitude", 0))*1e6);
        this.addItem(new GeoPoint(latitude, longitude));
        this.addItem(questionLocation);
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
	
	public void addItem(GeoPoint point){
		this.items.add(new OverlayItem(point, "String1", "String2"));
		populate();
	}

}
