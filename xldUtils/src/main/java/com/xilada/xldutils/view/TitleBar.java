package com.xilada.xldutils.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xilada.xldutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TitleBar extends ViewGroup{

    private List<View> rightsView;
    private TextView leftView;
    private TextView titleView;
    private boolean showLeft = false;
    private boolean showTitle = false;

    public TitleBar(Context context) {
        super(context);
        initTitle();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitle();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTitle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTitle();
    }
    void initTitle(){
        LayoutInflater.from(getContext()).inflate(R.layout.titler_bar_layout,this,true);
        titleView = (TextView) findViewById(R.id.title);
        leftView = (TextView) findViewById(R.id.leftButton);
    }
//    void initLeft(){
//        leftView = new TextView(getContext());
//        leftView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//        leftView.setTextColor(getResources().getColor(R.color.textColor));
////        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
////        leftView.setLayoutParams(params);
//        leftView.setGravity(Gravity.CENTER_VERTICAL);
//        leftView.setText("返回");
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        System.err.println("heightSize-->"+heightSize);
        int usedWidth = 0;
//        int heightSize = 0;
        if (showLeft){
            measureChild(leftView,widthMeasureSpec,heightMeasureSpec);
//            heightSize = Math.max(heightSize,leftView.getMeasuredHeight());
            usedWidth = leftView.getMeasuredWidth();
        }
        int rightUsed=0;
        if (hasRightButton()){
            int w =0;
            int padding = 0;
            for (View view :rightsView) {

                measureChild(view,widthMeasureSpec,heightMeasureSpec);
//                heightSize = Math.max(heightSize,view.getMeasuredHeight());
                w+= view.getMeasuredWidth();
                rightUsed += (w+padding);
                padding = dip2px(8);

            }
        }
//        if (showTitle){
            usedWidth = Math.max(usedWidth,rightUsed);
            measureChildWithMargins(titleView,widthMeasureSpec,usedWidth,heightMeasureSpec,0);
//            heightSize = Math.max(heightSize,titleView.getMeasuredHeight());
//        }
        setMeasuredDimension(widthSize, heightSize);
    }

    private int getMeasuredWidthWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
    }

    private int getMeasuredHeightWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        int contentTop = 0;
        System.err.println("--left-->"+left+"--right-->"+right);
        System.err.println("--top-->"+top+"--bottom-->"+bottom);
        int leftUsed = 0;
        if (showLeft){
            leftUsed = leftView.getMeasuredWidth();
            layoutRightButtons(leftView,0,contentTop,leftUsed,leftView.getMeasuredHeight());
        }
        int rightUsed = 0;
        if (hasRightButton()){
            int usedWidth=0;
            int margin = 0;
            for (View view :rightsView){
                int w = view.getMeasuredWidth();
                layoutRightButtons(view,right-(w+usedWidth+margin),contentTop,w,view.getMeasuredHeight());
                rightUsed += (w+margin);
                usedWidth+=w;
                margin = dip2px(8);
            }
        }
        if (showTitle){
            int used = Math.max(leftUsed,rightUsed);
            int w = right - 2*used;

            System.err.println("w--->"+titleView.getMeasuredWidth()+"--used-->"+used+"--w-->"+w);
//            titleView.setPadding(used,0,used,0);
//            titleView.getLayoutParams().width = w-1;
//            titleView.setBackgroundColor(Color.WHITE);
//            titleView.setSingleLine(true);
//            titleView.setText(titleView.getText());
            System.err.println(""+titleView.getText());
            layoutRightButtons(titleView,used,contentTop,w,getMeasuredHeight());
        }

    }

    private void layoutRightButtons(View view,int left,int top,int right,int bottom){
        view.layout(left, top, left + right, top + bottom);

    }

    public void setTitle(CharSequence title){
        showTitle = true;
//        if (titleView ==null){
//            initTitle();
//        }
        titleView.setText(title);
    }

    public void showLeftButton(boolean show){
        showLeft = show;
        leftView.setVisibility(show?VISIBLE:GONE);
//        if (show){
//            initLeft();
//            addView(leftView);
//        }else {
//            if (leftView!=null ){
//                removeView(leftView);
//                leftView=null;
//            }
//        }
    }

    /**
     * 最多两个按钮view
     * @param view view
     */
    public void addRightView(View view){
        initRightViewList();
        //超过2个不操作
        if (rightsView.size()<2){
            rightsView.add(view);
            addView(view);
        }

    }

    /**
     * 初始化右边按钮数组
     */
    private void initRightViewList(){
        if (rightsView==null){
            rightsView = new ArrayList<>();
        }
    }

    /**
     * 是否有右边按钮
     * @return 默认false
     */
    private boolean hasRightButton(){
        return rightsView!=null&& rightsView.size()>0;
    }

    private int dip2px(int dip){
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int)(dip * scale + 0.5f);
    }
}
