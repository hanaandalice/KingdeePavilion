package com.zipingfang.jindiexuan.module_user.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.xilada.xldutils.adapter.HeaderAndFooterRecyclerAdapter;
import com.xilada.xldutils.adapter.util.ViewHolder;
import com.xilada.xldutils.view.RoundAngleImageView;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_user.model.LeaderBoardStarModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class LeaderBoardStarAdapter extends HeaderAndFooterRecyclerAdapter<LeaderBoardStarModel> {

    private Context context;
    public LeaderBoardStarAdapter(List<LeaderBoardStarModel> mData , Context context) {
        super(mData, R.layout.item_leader_board_star, context);
        this.context =context;
    }

    @Override
    public void onBind(int position, LeaderBoardStarModel leaderBoardStarModel, ViewHolder holder) {
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
    }
}
