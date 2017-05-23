package com.zipingfang.jindiexuan.module_login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.flyco.dialog.widget.NormalDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.tool.Densityuitl;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_login.adapter.DeliveryAdapter;
import com.zipingfang.jindiexuan.view.GridSpacingItemDecoration;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/19.
 */

public class SelectedDeliveryActivity extends TitleBarActivity {
    @BindView(R.id.recyclerView_delivery)
    RecyclerView recyclerView_delivery;
    private DeliveryAdapter deliveryAdapter;
    private Unbinder unbinder;
    private  List<String> stringList;
    public static  final String DATA ="data";
    private String type;
    private String delivery;
    private int selectPosition=-1;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_selected_delivery;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("选择区域");
        showTitleBarLine(true);
        setRightButton("确定", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deliveryAdapter.getSelectPosition()>=0) {
                    Intent intent=new Intent();
                    Bundle bundle =new Bundle();
                    bundle.putString(DATA, stringList.get(deliveryAdapter.getSelectPosition()));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.create(SelectedDeliveryActivity.this).show("请选择区域");
                }
            }
        });
        type =getIntent().getStringExtra("type");
        delivery = getIntent().getStringExtra("delivery");
        if (!TextUtils.isEmpty(type)) {
            final NormalDialog dialog2 = new NormalDialog(this);
            dialog2.content("修改区域之后需要后台审核，\n审核通过之后根据原区域抢单。")
                    .contentTextSize(14)
                    .cornerRadius(15)
                    .contentTextColor(getResources().getColor(R.color.black))
                    .style(NormalDialog.STYLE_TWO)
                    .titleTextColor(getResources().getColor(R.color.black))
                    .titleTextSize(18)
                    .btnNum(1)
                    .btnTextSize(16)
                    .btnTextColor(getResources().getColor(R.color.textAccent))
                    .btnText("知道啦")
                    .showAnim(null)
                    .dismissAnim(null)
                    .show();
        }
        unbinder = ButterKnife.bind(this);
        recyclerView_delivery.setLayoutManager(new GridLayoutManager(this,4));
        stringList=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            stringList.add("福"+i+"区");
        }
        stringList.add("南山区");
        deliveryAdapter =new DeliveryAdapter(stringList,this);
        recyclerView_delivery.setAdapter(deliveryAdapter);
        recyclerView_delivery.addItemDecoration(new GridSpacingItemDecoration(4, Densityuitl.dip2px(this,16),Densityuitl.dip2px(this,10),false));
        deliveryAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                deliveryAdapter.setSelectPosition(position);
            }
        });
        for (int i = 0; i < stringList.size(); i++) {
            if (TextUtils.equals(stringList.get(i),delivery)) {
                selectPosition =i;
                deliveryAdapter.setSelectPosition(selectPosition);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
