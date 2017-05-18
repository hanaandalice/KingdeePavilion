package com.xilada.xldutils.adapter.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xilada.xldutils.R;
import com.xilada.xldutils.view.RoundAngleImageView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    private Context context;

    public ViewHolder(View itemView,Context context) {
        super(itemView);
        mConvertView = itemView;
        this.context =context;
        initImageLoader();
    }
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                //设置加载时候需要显示的图片
                .showImageOnLoading(R.mipmap.default_error)
                .showImageForEmptyUri(R.mipmap.default_error)
                .showImageOnFail(R.mipmap.default_error)
                .cacheInMemory(true)
                .considerExifParams(true)
                //	.displayer(new FadeInBitmapDisplayer(300, true, true, true))
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
                 ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaultOptions)
//                .memoryCache(new WeakMemoryCache())
                ;
        ImageLoaderConfiguration config = builder.build();
        ImageLoader.getInstance().init(config);
    }
    @SuppressWarnings("unchecked")
    public <T extends View> T bind(int viewId) {// 通过ViewId得到View

        SparseArray<View> viewHolder = (SparseArray<View>) mConvertView
                .getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            mConvertView.setTag(viewHolder);
        }

        View childView = viewHolder.get(viewId);
        if (childView == null) {
            childView = mConvertView.findViewById(viewId);
            viewHolder.put(viewId, childView);
        }
        return (T) childView;

    }

    /**
     * 设置TextView文字
     * @param resId TextView的id
     * @param text  文字内容
     */
    public void setText(int resId, CharSequence text) {
        if (bind(resId) instanceof TextView)
            ((TextView) bind(resId)).setText(text);
    }
    /**
     * 设置ImageView
     * @param resId TextView的id
     */
    public void setImage(int resId,CharSequence text) {
        if (bind(resId) instanceof ImageView)
            ImageLoader.getInstance().displayImage("" + text, ((ImageView) bind(resId)));
    }
    /**
     * 设置ImageView
     * @param resId TextView的id
     */
    public void setImageLevel(int resId,int level) {
        if (bind(resId) instanceof ImageView){
            ((ImageView) bind(resId)).setImageLevel(level);
        }
    }
    /**
     * 通过ViewId设置点击监听
     * @param viewId
     * @param l
     */
    public void setOnClickListener(int viewId, View.OnClickListener l) {// 通过ViewId设置点击监听
        bind(viewId).setOnClickListener(l);
    }
    /**
     * 通过ViewId设置CheckView的Checkd
     * @param viewId
     */
    public void setCheckd(int viewId,boolean isCheckd){
        ((CheckBox)bind(viewId)).setChecked(isCheckd);
    }
    /**
     * 通过ViewId设置隐藏和显示
     * @param viewId
     * @param visibility
     */
    public void setVisibility(int viewId, int visibility) {// 通过ViewId设置隐藏和显示
        bind(viewId).setVisibility(visibility);
    }
}