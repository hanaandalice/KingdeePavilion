package com.zipingfang.jindiexuan.module_grabone.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_grabone.adapter.GraboneOrdersAdapter;
import com.zipingfang.jindiexuan.module_grabone.model.GraboneOrdersModel;
import com.zipingfang.jindiexuan.view.MyRecyclerDetorration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class GraboneOrdersFragment extends BaseLazyFragment {
    private GraboneOrdersAdapter graboneOrdersAdapter;
    private RecyclerView recyclerView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_grabone_orders;
    }

    @Override
    protected void onFirstVisibleToUser() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new MyRecyclerDetorration(getActivity(), LinearLayoutManager.VERTICAL, R.drawable.divider_line));
        List<GraboneOrdersModel> graboneOrdersModelList =new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            graboneOrdersModelList.add(new GraboneOrdersModel());
        }
        graboneOrdersAdapter =new GraboneOrdersAdapter(graboneOrdersModelList,getActivity());
        recyclerView.setAdapter(graboneOrdersAdapter);
    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

    }
}
