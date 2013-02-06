package com.kalidu.codeblue.activities.listQuestionMapActivity;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class ListQuestionOverlayItem extends OverlayItem {
	
	private final String type;
	private final int id;

	public ListQuestionOverlayItem(GeoPoint point, String title, String snippet, String type, int id) {
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
