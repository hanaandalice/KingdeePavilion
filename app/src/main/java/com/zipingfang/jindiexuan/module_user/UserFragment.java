package com.zipingfang.jindiexuan.module_user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_user.activity.PersonalInformationActivity;
import com.zipingfang.jindiexuan.view.gradation.GradationScrollView;

/**
 * Created by Administrator on 2017/5/18.
 */

public class UserFragment extends BaseLazyFragment implements View.OnClickListener{
    private RelativeLayout layout_title;
    private GradationScrollView gradationScrollview;
    private RelativeLayout layout_banner;
    private ImageView iv_head_img;

    private int titleHeight;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_user;
    }

    @Override
    protected void onFirstVisibleToUser() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layout_title =findViewById(R.id.layout_title);
        gradationScrollview =findViewById(R.id.gradationScrollview);
        layout_banner =findViewById(R.id.layout_banner);
        iv_head_img =findViewById(R.id.iv_head_img);

        ViewTreeObserver vto = layout_banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout_title.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int intw= View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int inth=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                layout_banner.measure(intw, inth);
                titleHeight = layout_banner.getMeasuredHeight();
                gradationScrollview.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
                        // TODO Auto-generated method stub
                        if (y <= 0) {   //设置标题的背景颜色
                            layout_title.setBackgroundColor(Color.argb((int) 0, 138,0,0));
                        } else if (y > 0 && y <= titleHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            float scale = (float) y / titleHeight;
                            float alpha = (255 * scale);
                            layout_title.setBackgroundColor(Color.argb((int) alpha, 138,0,0));
                        } else {    //滑动到banner下面设置普通颜色
                            layout_title.setBackgroundColor(Color.argb((int) 255, 138,0,0));
                        }
                    }
                });
            }
        });

        iv_head_img.setOnClickListener(this);
    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head_img:
                goActivity(PersonalInformationActivity.class);
                break;
        }
    }
}
