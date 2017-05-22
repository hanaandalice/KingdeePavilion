package com.zipingfang.jindiexuan.module_login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
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
        unbinder = ButterKnife.bind(this);
        recyclerView_delivery.setLayoutManager(new GridLayoutManager(this,4));
        stringList=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            stringList.add("");
        }
        deliveryAdapter =new DeliveryAdapter(stringList,this);
        recyclerView_delivery.setAdapter(deliveryAdapter);
        recyclerView_delivery.addItemDecoration(new GridSpacingItemDecoration(4,20,false));
        deliveryAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                deliveryAdapter.setSelectPosition(position);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
