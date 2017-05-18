package com.xilada.xldutils.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.xilada.xldutils.R;
import com.xilada.xldutils.netstatus.NetChangeObserver;
import com.xilada.xldutils.netstatus.NetStateReceiver;
import com.xilada.xldutils.netstatus.NetUtils;
import com.xilada.xldutils.tool.CacheActivity;
import com.xilada.xldutils.utils.DensityUtil;
import com.xilada.xldutils.view.utils.ViewHolder;
import com.xilada.xldutils.widget.ProgressDialog;

import java.lang.reflect.Field;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    protected boolean isDestroy = false;
    protected ViewHolder mHolder;
    protected Context mContext;
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;
    protected int mDensityDpi;
    //关闭应用广播action
    public String ACTION_CLOSE_ALL ;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent!=null && TextUtils.equals(intent.getAction(),ACTION_CLOSE_ALL)){
                finish();
            }
        }
    };
    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(!isTaskRoot()){
            Intent intent = getIntent();
            String action = intent.getAction();
            if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)){
                finish();
                return;
            }
        }
        ACTION_CLOSE_ALL = String.format("cn.sinata.ba se.%s.all.close",getPackageName());
        //注册“关闭页面”广播监听器
        if (isRegisterCloseBroadReceiver()){
            registerReceiver(broadcastReceiver,new IntentFilter(ACTION_CLOSE_ALL));
        }
        //按需注册网络变化监听器
        if (shouldRegisterNetworkChangeReceiver()){
            NetStateReceiver.registerNetworkStateReceiver(this);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mScreenHeight = DensityUtil.getDeviceHeight(this);
        mScreenWidth = DensityUtil.getDeviceWidth(this);
        mContext = this;
        isDestroy = false;
        mHolder = new ViewHolder(this);

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }
            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheActivity.finishSingleActivity(this);
        if (shouldRegisterNetworkChangeReceiver()){
            NetStateReceiver.unRegisterNetworkStateReceiver(this);
        }
        if (isRegisterCloseBroadReceiver()){
            unregisterReceiver(broadcastReceiver);
        }
        if(Util.isOnMainThread()) {
            Glide.get(this).clearMemory();
        }
        mHolder.unBind();
        isDestroy = true;
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        dismissDialog();
    }
    /**
     * 是否需要注册“关闭全部页面”广播
     * @return 默认false
     */
    protected boolean isRegisterCloseBroadReceiver(){
        return true;
    }
    /**
     * 是否注册网络变化监听器
     * @return 默认false
     */
    protected boolean shouldRegisterNetworkChangeReceiver(){
        return false;
    }
    /**
     * 关闭所有页面
     */
    protected void closeAll(){
        Intent intent = new Intent(ACTION_CLOSE_ALL);
        sendBroadcast(intent);
    }
    /**
     * 必须先调用注册网络状态监听广播。否则没有任何反应
     * must call NetStateReceiver.registerNetworkStateReceiver(context) first ,if not,nothing change
     * @param type 网络类型
     */
    protected void onNetworkConnected(NetUtils.NetType type){}
    /**
     * 必须先调用注册网络状态监听广播。否则没有任何反应
     * must call NetStateReceiver.registerNetworkStateReceiver(context) first ,if not,nothing change
     */
    protected void onNetworkDisConnected(){}
    /**
     * 绑定视图，简化系统findViewById写法
     * @param resId 视图id
     * @param <T> 视图类型继承自系统的View类
     * @return 返回组件实例
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T bind(int resId){
        return  (T) mHolder.bind(resId);
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
        Intent intent = new Intent(this,mClass);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转页面带参数
     * @param number 电话号码
     */
    protected void goTelPhoneActivity(String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        if (number!=null&&number.length()>10){
            Uri data = Uri.parse("tel:" + number);
            intent.setData(data);
        }
        startActivity(intent);
    }

    /**
     * 带页面回调跳转，不带参数
     * @param mClass 目标页面
     * @param requestCode 请求code
     */
    protected void goActivityForResult(Class<?> mClass,int requestCode){
        goActivityForResult(mClass, null, requestCode);
    }

    /**
     * 带页面回调跳转，带参数
     * @param mClass 目标页面
     * @param bundle 参数
     * @param requestCode 请求码
     */
    protected void goActivityForResult(Class<?> mClass,Bundle bundle,int requestCode){
        Intent intent = new Intent(this,mClass);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
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
        if (isDestroy) {
            return;
        }
        if(dialog==null){
            dialog=new ProgressDialog(this,R.style.Theme_ProgressDialog);
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
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
    // 点击空白区域 自动隐藏软键盘
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    /**
     * 通过反射的方式获取状态栏高度
     * @return
     */
    protected int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
