package com.xilada.xldutils.activitys;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import com.xilada.xldutils.R;
import com.xilada.xldutils.tool.CacheActivity;
import com.xilada.xldutils.view.NavigationBar;

import org.json.JSONException;
public abstract class TitleBarActivity extends BaseActivity {
    /**
     * 页面layout
     * @return
     */
    protected abstract int setContentLayout();
    /**
     * 页面组件及逻辑处理，处于onCreate生命周期
     */
    protected abstract void initView() throws JSONException, IllegalAccessException;
    private NavigationBar navigationBar;
    private LinearLayout rootView;//根view
    private View line;
    protected int titleHight;
    private boolean isStatus =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        setContentView(R.layout.activity_title_bar);
        initStatus();
        CacheActivity.addActivity(this);
        try {
            init();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            View view = bind(R.id.status_view);
            view.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight();
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.height = statusHeight;
            view.setLayoutParams(params);
        }
    }

    private  int height =0;
    private void init() throws JSONException, IllegalAccessException {
        navigationBar = bind(R.id.navigationBar);
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        navigationBar.measure(w, h);
        height =navigationBar.getMeasuredHeight();
        int width =navigationBar.getMeasuredWidth();
        navigationBar.init();
        rootView=bind(R.id.rootView);
        line = bind(R.id.line);
        int viewId=setContentLayout();
        View v=View.inflate(this, viewId, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        rootView.addView(v, 2, layoutParams);
        initView();
        navigationBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                navigationBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                titleHight = navigationBar.getMeasuredHeight();
            }
        });
//        showTitleBarLine(false);
    }
    protected void addView(View view){
        rootView.addView(view);
    }
    protected void addView(int position,View view){
        rootView.addView(view,position);
    }
    protected void addView(View view, ViewGroup.LayoutParams params){
        rootView.addView(view,params);
    }
    protected void addView(int position, View view, ViewGroup.LayoutParams params){
        rootView.addView(view, position, params);
    }
    protected void toggleTip(boolean show){
        mHolder.bind(R.id.tv_tip).setVisibility(show ? View.VISIBLE : View.GONE);
    }
    protected void hideTitle(boolean hide){
        navigationBar.setVisibility(!hide ? View.VISIBLE : View.GONE);
    }
    protected int getNavigationBar(){
        return this.height;
    }
    /**
     * 设置标题
     * @param title
     */
    public void setTitle(CharSequence title){
        navigationBar.setTitle(title);
    }
    /**
     * 设置标题栏背景颜色
     * @param color
     */
    public void setTitleBarBackgroundColor(int color){
        navigationBar.setBackgroundColor(color);
    }

    /**
     * 不显示左边按钮
     */
    protected void hideLeftButton(){
        navigationBar.hideLeftButton();
    }

    /**
     * 不显示右边按钮
     */
    protected void hideRightButton(){
        navigationBar.hideRightButton();
    }
    /**
     * 显示右边按钮
     */
    protected void showRightButton(){
        navigationBar.showRightButton();
    }
    /**
     * 是否显示标题栏下一根线
     * @param show
     */
    public void showTitleBarLine(boolean show){
        line.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    /**
     * 设置右边按钮是否可用
     * @param enabled
     */
    protected void enabledRightButton(boolean enabled){
        navigationBar.enableRightButton(enabled);
    }
    protected void setOnClickListener(int resId,View.OnClickListener clickListener){
        mHolder.setOnClickListener(resId, clickListener);
    }
    /**
     * 左边按钮事件
     * @param listener
     */
    protected void setOnLeftClick(View.OnClickListener listener){
        navigationBar.setOnLeftClick(listener);
    }
    /**
     * 左边按钮
     * @param text 文字
     * @param left	图标
     * @param listener onclick事件回调
     */
    protected void setLeftButton(String text,Drawable left,View.OnClickListener listener){
        navigationBar.setLeftButton(text, listener, left);
    }

    /**
     * 左按钮图片
     * @param left 图片
     */
    protected void setLeftDrawable(Drawable left, View.OnClickListener onClickListener){
        navigationBar.setLeftButtonDrawable(left);
        navigationBar.setOnLeftClick(onClickListener);
    }
    /**
     * 设置右边按钮
     * @param text
     * @param right
     * @param listener
     */
    protected void setRightButton(String text,Drawable right,View.OnClickListener listener){
        navigationBar.setRightButton(text, null, right, null, null, listener);
    }

    /**
     * 设置右边按钮
     * @param right
     */
    protected void setRightButtonImageView(Drawable right){
        navigationBar.setRightButtonImageView(right);
    }

    /**
     * 设置右边按钮文字
     * @param text 文字
     */
    protected void setRightButtonText(String text){
        navigationBar.setRightButtonText(text);
    }
    /**
     * 设置右边按钮颜色
     * @param color 颜色
     */
    protected void setRightButtonTextColor(int color){
        navigationBar.getRightButton().setTextColor(color);
    }
    /**
     * 设置右边设置图片
     */
    protected void setRightButtonImageViews(Drawable drawable){
        navigationBar.setRightButtonImageView(drawable);
    }
    /**
     * 设置右边点击状态
     */
    protected void setRightButtonClickable(boolean i){
        navigationBar.setClickable(i);
    }
    protected void hideSoftWindow(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
