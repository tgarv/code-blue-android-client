package com.kalidu.codeblue;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class BlueOverlayItem extends OverlayItem {
	
	private final String type;
	private final int id;

	public BlueOverlayItem(GeoPoint point, String title, String snippet, String type, int id) {
		super(point, title, snippet);
		this.type = type;
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}

}
