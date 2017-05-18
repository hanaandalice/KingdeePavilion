package com.xilada.xldutils.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 *
 */
public class DialogUtils {


    /**
     * 创建简单dialog
     * @param context 上下文
     * @param message 提示文字
     */
    public static void createNoticeDialog(Context context,String message){
        createNoticeDialog(context,"请注意",message);
    }
    /**
     * 创建简单dialog
     * @param context 上下文
     * @param title 标题
     * @param message 提示文字
     */
    public static void createNoticeDialog(Context context,String title,String message){
        createNoticeDialog(context,title,message,"确定",null);
    }

    /**
     * 创建dialog
     * @param context 上下文
     * @param title 标题
     * @param message 提示信息
     * @param positiveButton 按钮文字，不显示按钮请传null
     */
    public static void createNoticeDialog(Context context,String title,String message,String positiveButton){
        createNoticeDialog(context,title,message,positiveButton,null);
    }

    /**
     * 创建dialog
     * @param context 上下文
     * @param title 标
     * @param message 提示信
     * @param positiveButton 按钮文字，不显示按钮请传null
     * @param negativeButton 按钮文字，不显示按钮请传null
     */
    public static void createNoticeDialog(Context context,String title,String message,String positiveButton,String negativeButton){
        createNoticeDialog(context,title,message,positiveButton,negativeButton,null,null);
    }
    /**
     * 创建dialog
     * @param context 上下文
     * @param title 标
     * @param message 提示信
     * @param positiveButton 按钮文字，不显示按钮请传null
     * @param negativeButton 按钮文字，不显示按钮请传null
     * @param positiveListener 确定按钮点击事件
     * @param negativeListener 取消按钮点击事件
     */
    public static void createNoticeDialog(Context context, String title, String message, String positiveButton, String negativeButton, DialogInterface.OnClickListener positiveListener,DialogInterface.OnClickListener negativeListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        if (!TextUtils.isEmpty(positiveButton)) {
            builder.setPositiveButton(positiveButton,positiveListener);
        }
        if (!TextUtils.isEmpty(negativeButton)) {
            builder.setNegativeButton(negativeButton, negativeListener);
        }
        builder.create().show();
    }
}
