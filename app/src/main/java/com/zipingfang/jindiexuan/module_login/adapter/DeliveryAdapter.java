package com.zipingfang.jindiexuan.module_login.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.adapter.util.ViewHolder;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_home.model.HomeCommodityModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class DeliveryAdapter extends BaseRecyclerAdapter<String> {
    private Context context;
    private int selectPosition=-1;
    public DeliveryAdapter(List<String> mData, Context context) {
        super(mData, R.layout.item_select_delivery, context);
        this.context= context;
    }

    @Override
    public void onBind(int position, String s, ViewHolder holder) {
        if (selectPosition==position) {
            holder.bind(R.id.tv_delivery).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.radius_25dp_accent_bg));
            holder.<TextView>bind(R.id.tv_delivery).setTextColor(context.getResources().getColor(R.color.white));
        }else{
            holder.bind(R.id.tv_delivery)
                    .setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.radius_25dp_white_bg_line_accent_7e_1dp));
            holder.<TextView>bind(R.id.tv_delivery).setTextColor(context.getResources().getColor(R.color.textAccent_7e));
        }
        holder.setText(R.id.tv_delivery,s);
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return selectPosition;
    }
}
