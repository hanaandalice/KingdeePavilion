package com.xilada.xldutils.tool;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Srceen {
	public static int[] getScreen(Activity acy ) {
		DisplayMetrics dm = new DisplayMetrics();
		acy.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int[] size ={dm.widthPixels,dm.heightPixels};
		return size;
	}
}
