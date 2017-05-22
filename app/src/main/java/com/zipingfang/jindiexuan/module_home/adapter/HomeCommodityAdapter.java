package com.zipingfang.jindiexuan.module_home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.adapter.util.ViewHolder;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_home.model.HomeCommodityModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class HomeCommodityAdapter extends BaseRecyclerAdapter<HomeCommodityModel> {
    private int height;
    public HomeCommodityAdapter(List<HomeCommodityModel> mData,Context context,int height) {
        super(mData, R.layout.item_home_commodity, context);
        this.height =height;
    }

    @Override
    public void onBind(int position, HomeCommodityModel homeCommodityModel, ViewHolder holder) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.<ImageView>bind(R.id.iv_commodity).getLayoutParams();
        layoutParams.width =LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height =height/2/5*4;
        holder.<ImageView>bind(R.id.iv_commodity).setLayoutParams(layoutParams);
    }
}
