package com.xilada.xldutils.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.johnpersano.supertoasts.SuperToast;
import com.xilada.xldutils.R;
import com.xilada.xldutils.utils.DensityUtil;
import com.xilada.xldutils.utils.StringUtils;
import com.xilada.xldutils.widget.ProgressDialog;
import com.xilada.xldutils.xldUtils;

/**
 * Created by LiaoXiang on 2015/11/18.
 */
public class BaseFragment extends Fragment{
    private SuperToast superToast;
    private ProgressDialog dialog;
    protected SharedPreferences sp;
    protected Context context;
    protected int mScreenWidth;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        mScreenWidth = DensityUtil.getDeviceWidth(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // ButterKnife注解框架进行绑定，在主gradle添加classpath

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public SharedPreferences getSharedPreferences(){
        if (sp == null) {
            String spName;
            if (StringUtils.isEmpty(xldUtils.SPNAME)){
                spName = context.getPackageName();
            }else {
                spName = xldUtils.SPNAME;
            }
            sp=context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        }
        return sp;
    }
    /**
     * 查找	View
     * @param paramInt
     * @return
     */
    public final <T extends View>T findViewById(int paramInt) {

        return (T)getView().findViewById(paramInt);
    }

    /**
     * 获取存储的字符型数据
     * @param key 键
     * @return value
     */
    protected String getStringSharedPreferences(String key){
        return getSharedPreferences().getString(key,"");
    }

    /**
     * 获取存储的布尔型数据
     * @param key 键
     * @return value
     */
    protected boolean getBooleanSharedPreferences(String key){
        return getSharedPreferences().getBoolean(key,false);
    }

    /**
     * 清空所有SharedPreferences数据
     */
    protected void clearSharedPreferences(){
        getSharedPreferences().edit().clear().apply();
    }

    /**
     * 显示加载提示窗
     */
    protected void showDialog(){
        showDialog("加载中...");
    }

    /**
     * 显示加载提示窗
     * @param msg 提示文字
     */
    protected void showDialog(CharSequence msg){
        showDialog(msg,false);
    }

    /**
     * 显示加载提示窗
     * @param msg 提示文字
     * @param canCancel 是否可手动取消
     */
    protected void showDialog(CharSequence msg,boolean canCancel){
        if (context == null) {
            return;
        }
        if(dialog == null){
            dialog = new ProgressDialog(context, R.style.Theme_ProgressDialog);
        }
        dialog.setCanceledOnTouchOutside(canCancel);
        dialog.setMessage(msg);
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
    /**
     * 关闭加载窗
     */
    protected void dismissDialog(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    /**
     * 显示Toast
     * @param msg 显示文字
     */
    public void showToast(String msg){
        if (context == null) {
            return;
        }
        if (superToast == null) {
            superToast = new SuperToast(context);
            superToast.setDuration(1000);
            superToast.setTextSize(SuperToast.TextSize.SMALL);
        }
        superToast.setText(msg);
        if (!superToast.isShowing()) {
            superToast.show();
        }
    }

    /**
     * 跳转页面
     * @param mClass 目标页面
     */
    protected void goActivity(Class<?> mClass){
        goActivity(mClass,null);
    }

    /**
     * 跳转页面带参数
     * @param mClass 目标页面
     * @param bundle 参数
     */
    protected void goActivity(Class<?> mClass,Bundle bundle){
        Intent intent = new Intent(context,mClass);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 带页面回调跳转，不带参数
     * @param mClass 目标页面
     * @param requestCode 请求code
     */
    protected void goActivityForResult(Class<?> mClass,int requestCode){
        goActivityForResult(mClass,null,requestCode);
    }

    /**
     * 带页面回调跳转，带参数
     * @param mClass 目标页面
     * @param bundle 参数
     * @param requestCode 请求码
     */
    protected void goActivityForResult(Class<?> mClass,Bundle bundle,int requestCode){
        Intent intent = new Intent(context,mClass);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }
}
