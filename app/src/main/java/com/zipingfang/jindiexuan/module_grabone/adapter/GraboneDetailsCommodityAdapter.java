package com.zipingfang.jindiexuan.module_grabone.adapter;

import android.content.Context;

import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.adapter.util.ViewHolder;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_grabone.model.GraboneDetailsCommodityModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class GraboneDetailsCommodityAdapter extends BaseRecyclerAdapter<GraboneDetailsCommodityModel> {


    private Context context;
    public GraboneDetailsCommodityAdapter(List<GraboneDetailsCommodityModel> mData,  Context context) {
        super(mData, R.layout.item_grabone_details_commodity, context);
        this.context =context;
    }

    @Override
    public void onBind(int position, GraboneDetailsCommodityModel graboneDetailsCommodityModel, ViewHolder holder) {

    }
}
