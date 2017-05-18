package com.xilada.xldutils.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class Version {
	/**
	 * 
	 * @param context
	 * @return String数组，第0个元素代表版本名，第1个元素代表版本号
	 */
	public static String[] getVersion(Context context){
		String[] array = new String[2];
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			String versionName = pi.versionName;
			int code = pi.versionCode;
			array[0] = versionName;
			array[1] = code+"";
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return array;
	}
}
