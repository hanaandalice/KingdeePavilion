package com.zipingfang.jindiexuan.module_user.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.xilada.xldutils.adapter.HeaderAndFooterRecyclerAdapter;
import com.xilada.xldutils.adapter.util.ViewHolder;
import com.xilada.xldutils.view.RoundAngleImageView;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_user.model.LeaderBoardMoneyModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class LeaderBoardMoneyAdapter extends HeaderAndFooterRecyclerAdapter<LeaderBoardMoneyModel> {
    private static final String TAG = "LeaderBoardMoneyAdapter";
    private Context context;
    private String type;
    public LeaderBoardMoneyAdapter(List<LeaderBoardMoneyModel> mData, Context context,String type) {
        super(mData, R.layout.item_leader_board_money, context);
        this.type =type;
        Log.d(TAG, "LeaderBoardMoneyAdapter: -------->"+type);
        this.context =context;
    }

    @Override
    public void onBind(int position, LeaderBoardMoneyModel leaderBoardMoneyModel, ViewHolder holder) {
        if (position==0) {
            Glide.with(context).load(R.mipmap.icon_no1).into(holder.<RoundAngleImageView>bind(R.id.iv_leader_board));
        }else if (position==1){
            Glide.with(context).load(R.mipmap.icon_no2).into(holder.<RoundAngleImageView>bind(R.id.iv_leader_board));
        }else if (position ==2){
            Glide.with(context).load(R.mipmap.icon_no3).into(holder.<RoundAngleImageView>bind(R.id.iv_leader_board));
        }else{
            holder.setText(R.id.tv_leader_board,(position+1)+"");
        }
        if (position<=2){
            holder.setVisibility(R.id.iv_leader_board, View.VISIBLE);
            holder.setVisibility(R.id.tv_leader_board, View.GONE);
        }else{
            holder.setVisibility(R.id.iv_leader_board, View.GONE);
            holder.setVisibility(R.id.tv_leader_board, View.VISIBLE);
        }
        if (TextUtils.equals("0",type)) {
            holder.setText(R.id.tv_ps,"接单总数量：200单");
        }else{
            holder.setText(R.id.tv_ps,"收益：2000元");
        }
    }
}
