package com.zipingfang.jindiexuan.module_grabone.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_grabone.adapter.GraboneDetailsCommodityAdapter;
import com.zipingfang.jindiexuan.module_grabone.model.GraboneDetailsCommodityModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/27.
 */

public class GraboneDetailsActivity extends TitleBarActivity {
    @BindView(R.id.recyclerView_orders)
    RecyclerView recyclerView_orders;

    private Unbinder unbinder;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_grabone_details;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("详情");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView_orders.setLayoutManager(layoutManager);
        List<GraboneDetailsCommodityModel> graboneDetailsCommodityModelList =new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            graboneDetailsCommodityModelList.add(new GraboneDetailsCommodityModel());
        }
        GraboneDetailsCommodityAdapter graboneDetailsCommodityAdapter =new GraboneDetailsCommodityAdapter(graboneDetailsCommodityModelList,this);
        recyclerView_orders.setAdapter(graboneDetailsCommodityAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
