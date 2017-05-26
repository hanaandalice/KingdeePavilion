package com.zipingfang.jindiexuan.module_grabone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.flyco.tablayout.SlidingTabLayout;
import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_grabone.fragment.GraboneOrdersFragment;
import com.zipingfang.jindiexuan.module_user.adapter.LeaderBoarPagerAdapter;
import com.zipingfang.jindiexuan.module_user.fragment.LeaderBoardMoneyFragment;
import com.zipingfang.jindiexuan.module_user.fragment.LeaderBoardStarFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/18.
 */

public class GraboneFragment extends BaseLazyFragment {

    private SlidingTabLayout slding_tab;
    private ViewPager viewPager;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_grabone;
    }

    @Override
    protected void onFirstVisibleToUser() {

    }

    private static final String TAG = "GraboneFragment";
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        slding_tab =findViewById(R.id.slding_tab);
        viewPager =findViewById(R.id.viewPager);
        List<String> stringList =new ArrayList<>();
        stringList.add("到店订单");
        stringList.add("到家订单");
        stringList.add("紧急订单");
        List<Fragment> fragmentList =new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GraboneOrdersFragment graboneOrdersFragment =new GraboneOrdersFragment();
            Bundle bunble =new Bundle();
            bunble.putString("type",(i+1)+"");
            graboneOrdersFragment.setArguments(bunble);
            fragmentList.add(graboneOrdersFragment);
        }
        LeaderBoarPagerAdapter leaderBoarPagerAdapter =new LeaderBoarPagerAdapter(this.getChildFragmentManager(),stringList,fragmentList);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(leaderBoarPagerAdapter);
        slding_tab.setViewPager(viewPager);
    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

    }
}
