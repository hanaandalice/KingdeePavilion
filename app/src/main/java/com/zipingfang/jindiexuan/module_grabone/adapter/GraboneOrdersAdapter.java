package com.zipingfang.jindiexuan.module_grabone.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.flyco.dialog.widget.NormalDialog;
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
    private int type=0;
    private int dialogtype =0;
    private Context context;
    public GraboneOrdersAdapter(List<GraboneOrdersModel> mData, Context context) {
        super(mData, R.layout.item_grabone_orders, context);
        this.context =context;

    }

    @Override
    public void onBind(int position, GraboneOrdersModel graboneOrdersModel, ViewHolder holder) {
        if (position==0){
            holder.setOnClickListener(R.id.tv_punch, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent();
                    Bundle bundle =new Bundle();
                    switch (type) {
                        case 0:
                            bundle.putString("type",type+"");
                            intent.putExtras(bundle);
                            intent.setClass(context, GraboneDetailsActivity.class);
                            context.startActivity(intent);
                            type++;
                            break;
                        case 1:
                            bundle.putString("type",type+"");
                            intent.putExtras(bundle);
                            intent.setClass(context, GraboneDetailsActivity.class);
                            context.startActivity(intent);
                            type++;
                            break;
                        case 2:
                            bundle.putString("type",type+"");
                            intent.putExtras(bundle);
                            intent.setClass(context, GraboneDetailsActivity.class);
                            context.startActivity(intent);
                            type++;
                            break;
                        case 3:
                            bundle.putString("type",type+"");
                            intent.putExtras(bundle);
                            intent.setClass(context, GraboneDetailsActivity.class);
                            context.startActivity(intent);
                            type++;
                            break;
                        case 4:
                            bundle.putString("type",type+"");
                            intent.putExtras(bundle);
                            intent.setClass(context, GraboneDetailsActivity.class);
                            context.startActivity(intent);
                            type++;
                            break;
                        case 5:
                            bundle.putString("type",type+"");
                            intent.putExtras(bundle);
                            intent.setClass(context, GraboneDetailsActivity.class);
                            context.startActivity(intent);
                            type++;
                            break;
                        case 6:
                            bundle.putString("type",type+"");
                            intent.putExtras(bundle);
                            intent.setClass(context, GraboneDetailsActivity.class);
                            context.startActivity(intent);
                            type++;
                            break;
                        case 7:
                            bundle.putString("type",type+"");
                            intent.putExtras(bundle);
                            intent.setClass(context, GraboneDetailsActivity.class);
                            context.startActivity(intent);
                            type=0;
                            break;
                    }
                }
            });
        }else{
            holder.setOnClickListener(R.id.tv_punch, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (dialogtype) {
                        case 0:
                            showDialog("抢单失败","手慢了，订单被别人抢走了~~","继续抢单");
                            dialogtype++;
                            break;
                        case 1:
                            showDialog("抢单失败","你刚刚抢到一单，等一会再来抢吧！","好的");
                            dialogtype++;
                            break;
                        case 2:
                            showDialog("抢单失败","1小时最多抢6单，等会再来吧！","好的");
                            dialogtype++;
                            break;
                        case 3:
                            showDialog("抢单失败","你的司机认证尚未通过，\n通过之后再来抢单吧！","好的");
                            dialogtype=0;
                            break;
//                case 4:
//
//                    break;
//                case 5:
//
//                    break;
                    }
                }
            });


        }

    }

    private void showDialog(String title, String contents, String btn) {
        final NormalDialog dialog2 = new NormalDialog(context);
        dialog2.content(contents)
                .contentTextSize(14)
                .cornerRadius(15)
                .contentTextColor(context.getResources().getColor(R.color.black))
                .style(NormalDialog.STYLE_TWO)
                .titleTextColor(context.getResources().getColor(R.color.black))
                .titleTextSize(18)
                .title(title)
                .btnNum(1)
                .btnTextSize(16)
                .btnTextColor(context.getResources().getColor(R.color.textAccent))
                .btnText(btn)
                .showAnim(null)
                .dismissAnim(null)
                .show();
    }
}
