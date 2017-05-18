package com.zipingfang.jindiexuan.module_home.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_home.adapter.HomeCommodityAdapter;
import com.zipingfang.jindiexuan.module_home.model.HomeCommodityModel;
import com.zipingfang.jindiexuan.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class CommodityFragment  extends BaseLazyFragment{
    private HomeCommodityAdapter homeCommodityAdapter;
    private RecyclerView recyclerView;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_commodity;
    }

    @Override
    protected void onFirstVisibleToUser() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView =findViewById(R.id.recyclerView);
        GridSpacingItemDecoration layoutDecoration = new GridSpacingItemDecoration(2,20,false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.addItemDecoration(layoutDecoration);

        List<HomeCommodityModel> homeCommodityModelList =new ArrayList<>();
        homeCommodityModelList.add(new HomeCommodityModel());
        homeCommodityModelList.add(new HomeCommodityModel());
        homeCommodityModelList.add(new HomeCommodityModel());
        homeCommodityModelList.add(new HomeCommodityModel());
        homeCommodityModelList.add(new HomeCommodityModel());
        homeCommodityModelList.add(new HomeCommodityModel());
        homeCommodityAdapter =new HomeCommodityAdapter(homeCommodityModelList,getActivity());
        recyclerView.setAdapter(homeCommodityAdapter);
    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

    }
}
