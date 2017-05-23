package com.zipingfang.jindiexuan.module_home.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.adapter.HeaderAndFooterRecyclerAdapter;
import com.xilada.xldutils.adapter.util.ViewHolder;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.Api;
import com.zipingfang.jindiexuan.module_home.model.CateGoodsModel;
import com.zipingfang.jindiexuan.module_home.model.HomeCommodityModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class HomeCommodityAdapter extends HeaderAndFooterRecyclerAdapter<CateGoodsModel> {

    private int height;
    private Context context;
    public HomeCommodityAdapter(List<CateGoodsModel> mData, Context context,int height) {
        super(mData, R.layout.item_home_commodity, context);
        this.height =height;
        this.context= context;
    }

    private static final String TAG = "HomeCommodityAdapter";
    @Override
    public void onBind(int position, CateGoodsModel cateGoodsModel, ViewHolder holder) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.<ImageView>bind(R.id.iv_commodity).getLayoutParams();
        layoutParams.width =LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height =height/2/5*4;
        holder.<ImageView>bind(R.id.iv_commodity).setLayoutParams(layoutParams);
        Glide.with(context).load(Api.IMG_URL+cateGoodsModel.getPic()).into( holder.<ImageView>bind(R.id.iv_commodity));
        holder.setText(R.id.tv_commodity_name,cateGoodsModel.getGoods_name());
        holder.setText(R.id.tv_commodity_parameter,cateGoodsModel.getGoods_name());
    }

}
