package com.zipingfang.jindiexuan.module_grabone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.adapter.util.ViewHolder;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_grabone.activity.GraboneDetailsActivity;
import com.zipingfang.jindiexuan.module_grabone.model.GraboneOrdersModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class GraboneOrdersAdapter extends BaseRecyclerAdapter<GraboneOrdersModel> {

    private Context context;
    public GraboneOrdersAdapter(List<GraboneOrdersModel> mData, Context context) {
        super(mData, R.layout.item_grabone_orders, context);
        this.context =context;
    }

    @Override
    public void onBind(int position, GraboneOrdersModel graboneOrdersModel, ViewHolder holder) {
            holder.setOnClickListener(R.id.tv_punch, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent();
                    intent.setClass(context, GraboneDetailsActivity.class);
                    context.startActivity(intent);
                }
            });
    }
}
