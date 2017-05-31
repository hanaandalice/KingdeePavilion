package com.zipingfang.jindiexuan;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xilada.xldutils.activitys.BaseActivity;
import com.xilada.xldutils.tool.StatusBarUtils;
import com.zipingfang.jindiexuan.module_login.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * Created by Administrator on 2016/12/26.
 */
public class StartActivity extends BaseActivity implements View.OnClickListener{
//    R.mipmap.iv_start_bg_one, R.mipmap.iv_start_bg_two, R.mipmap.iv_start_bg_three
//    R.mipmap.bg_welcome,
    private int[] imageInteger = {R.mipmap.bg_start_one, R.mipmap.bg_start_two};
    private List<ImageView> listImage = new ArrayList<ImageView>();
    private boolean isScrolling;
    private boolean isStart = false;
    private View view_start;
    private TextView tv_jump_over;
    private  StartAdapter startAdapter;
    //添加圆点
    private ImageView[] indicatorViews;
    private LinearLayout layout_bottom;
    private int position =-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        StatusBarUtils.setStatusBarColor(this,R.color.white);
        ViewPager viewPager = bind(R.id.mImageViewPager);
        view_start = bind(R.id.view_start);
        view_start.setVisibility(View.GONE);
        tv_jump_over = bind(R.id.tv_jump_over);
        layout_bottom = bind(R.id.layout_bottom);
        for (int i = 0; i < imageInteger.length; i++) {
            ImageView imageView =new ImageView(this);
            imageView.setImageResource(imageInteger[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            listImage.add(imageView);
        }
        startAdapter = new StartAdapter();
        viewPager.setAdapter(startAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == listImage.size() - 1 && positionOffset == 0 && positionOffsetPixels == 0 && isScrolling) {
                    if (!isStart) {
                        isStart = true;
                        goActivity(LoginActivity.class);
//                        if (SharedPreferencesUtils.getBoolean(Utils.IS_LOGIN)){
//                            goActivity(MainActivity.class);
//                        }else{
//                            goActivity(LoginActivity.class);
//                        }
                        finish();
                    }
                }
            }
            @Override
            public void onPageSelected(int positions) {
                setImageBackground(positions);
                position =positions;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1) {
                    isScrolling = true;
                } else {
                    isScrolling = false;
                }
            }
        });
        createIndicatorLayout();
        view_start.setOnClickListener(this);
        tv_jump_over.setOnClickListener(this);
    }
    private static final String TAG = "StartActivity";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_start:
                if (position+1==imageInteger.length){
                    goActivity(LoginActivity.class);
//                    if (SharedPreferencesUtils.getBoolean(Utils.IS_LOGIN)){
//                        goActivity(MainActivity.class);
//                    }else{
//                        goActivity(LoginActivity.class);
//                    }
                    finish();
                }
                break;
            case R.id.tv_jump_over:
                goActivity(LoginActivity.class);
//                if (SharedPreferencesUtils.getBoolean(Utils.IS_LOGIN)){
//                    goActivity(MainActivity.class);
//                }else{
//                    goActivity(LoginActivity.class);
//                }
                finish();
                break;
        }
    }

    private class StartAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return listImage.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            ImageView imageView = listImage.get(position);
            container.addView(imageView);
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView imageView =listImage.get(position);
            container.removeView(imageView);
        }
    }

    private int dip2px(int dip){
        final float scale =getResources().getDisplayMetrics().density;
        return (int)(dip * scale + 0.5f);
    }

    private void createIndicatorLayout(){
        addIndicator();
    }

    //    添加圆点
    private void addIndicator(){
        if (startAdapter!=null){
            int count = startAdapter.getCount();
            indicatorViews = new ImageView[count];
            for (int i = 0;i<count;i++){
                ImageView view = new ImageView(this);
                indicatorViews[i] = view;
                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(dip2px(5),dip2px(5));
                params.rightMargin = dip2px(5);
                view.setLayoutParams(params);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setBackgroundResource(com.xilada.xldutils.R.drawable.page_indicator);
                if (i == 0){
                    view.setSelected(true);
                }
                layout_bottom.addView(view);
            }
        }
    }
    /**
     * 设置选中的tip的背景
     * @param selectItems 选中项
     */
    private void setImageBackground(int selectItems){
        for(int i=0; i<indicatorViews.length; i++){
            if(i == selectItems){
                indicatorViews[i].setSelected(true);
            }else{
                indicatorViews[i].setSelected(false);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        layout_bottom.removeAllViews();
    }
}
