package com.kalidu.codeblue;

import android.content.Context;
import android.view.View;

public abstract class Posting extends View {

	private final int userId;

	public Posting(Context context, int userId) {
		super(context);
		this.userId = userId;
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

}
