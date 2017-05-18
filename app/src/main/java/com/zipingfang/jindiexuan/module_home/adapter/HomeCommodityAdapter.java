package com.zipingfang.jindiexuan.module_home.adapter;

import android.content.Context;

import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.adapter.util.ViewHolder;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_home.model.HomeCommodityModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class HomeCommodityAdapter extends BaseRecyclerAdapter<HomeCommodityModel> {

    public HomeCommodityAdapter(List<HomeCommodityModel> mData,Context context) {
        super(mData, R.layout.item_home_commodity, context);
    }

    @Override
    public void onBind(int position, HomeCommodityModel homeCommodityModel, ViewHolder holder) {

    }
}
