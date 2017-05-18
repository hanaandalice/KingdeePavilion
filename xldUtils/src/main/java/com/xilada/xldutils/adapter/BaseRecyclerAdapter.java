package com.xilada.xldutils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xilada.xldutils.adapter.util.ViewHolder;

import java.util.List;

/**
 *
 * Created by LiaoXiang on 2015/11/4.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected List<T> mData;
    private int layoutId;
    protected Context mContext;

    public BaseRecyclerAdapter(List<T> mData, int layoutId,Context context) {
        this.mData = mData;
        this.layoutId = layoutId;
        this.mContext =context;
    }
    /**
     * ItemClick的回调接口
     * @author zhy
     *
     */
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    /**
     * 设置OnItemClickListener
     * @param mOnItemClickListener OnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutId <= 0) {
            layoutId = android.R.layout.simple_list_item_1;
        }
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemClick(holder.itemView,holder.getAdapterPosition());
                }
            });

        }
        T t = mData.get(position);
        if (t != null) {
            onBind(position, t, holder);
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 绑定视图与数据
     *
     * @param position 当前位置
     * @param t        数据
     * @param holder
     */
    public abstract void onBind(int position, T t, ViewHolder holder);
}
