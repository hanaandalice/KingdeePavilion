package com.zipingfang.jindiexuan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/5/19.
 */

public class SquareThreeQuarterRelativeLayout extends RelativeLayout {

    public SquareThreeQuarterRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareThreeQuarterRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareThreeQuarterRelativeLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, View.MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, (heightMeasureSpec/4*3));
    }
}
