package com.xilada.xldutils.widget.autoscoll;

import android.content.Context;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/5/17.
 */

public class BGABannerScroller extends Scroller {
    private int mDuration = 1000;
    public BGABannerScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
