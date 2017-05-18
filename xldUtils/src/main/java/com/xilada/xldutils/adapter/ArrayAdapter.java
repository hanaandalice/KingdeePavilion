package com.xilada.xldutils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xilada.xldutils.view.utils.ViewHolder;

/**
 * 实体类list的适配器
 * @author sinata
 *
 * @param <T>
 */
public abstract class ArrayAdapter<T> extends BaseAdapter {

	protected Context mContext;
    protected LayoutInflater mInflater;

    protected T[] mList;   // 数据集

    protected int mLayoutID;    // 布局资源ID
	
	public ArrayAdapter(Context ctx, T[] list, int layoutID) {
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
        mList = list;
        mLayoutID = layoutID;
    }
	
	@Override
    public int getCount() {
        if (mList != null && mList.length > 0) return mList.length;

        return 0;
    }

    @Override
    public T getItem(int position) {
        if (mList != null && mList.length > 0) 
        	return mList[position];
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = mInflater.inflate(mLayoutID, null);

        ViewHolder holder = new ViewHolder(convertView);
        T t=getItem(position);
        if (t!=null) {
        	onBind(position, t, holder);
		}
        return convertView;
    }

    /**
     * 绑定数据
     */
    protected abstract void onBind(int position, T data, ViewHolder holder);

}
