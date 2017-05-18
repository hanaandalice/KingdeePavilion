package com.xilada.xldutils.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 设备显示相关参数
 * @author Administrator
 *
 */
public class DensityUtil {
	
	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getDeviceWidth(Context context){ 
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels; 
	}
	

	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getDeviceHeight(Context context){ 
		
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
	}
	
	/**
	 * dip转换px
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue){ 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(dipValue * scale + 0.5f); 
	} 

	/**
	 * px转换dip
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue){ 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(pxValue / scale + 0.5f); 
	} 
	
	/**
     * 将sp值转换为px值，保证文字大小不变
     * @param context 上下文
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }

	public static float getDensity(Context context){
		final float density = context.getResources().getDisplayMetrics().density;
		return density;
	}
}
