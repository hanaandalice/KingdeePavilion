package com.zipingfang.jindiexuan.module_user.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_user.adapter.LeaderBoarPagerAdapter;
import com.zipingfang.jindiexuan.module_user.fragment.LeaderBoardMoneyFragment;
import com.zipingfang.jindiexuan.module_user.fragment.LeaderBoardStarFragment;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/22.
 */

public class LeaderBoardActivity extends TitleBarActivity {
    @BindView(R.id.slding_tab)
    SlidingTabLayout slding_tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private Unbinder unbinder;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_leader_board;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("排行榜");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);

        List<String> stringList =new ArrayList<>();
        stringList.add("接单排名");
        stringList.add("收益排名");
        stringList.add("服务星级排名");
        List<Fragment> fragmentList =new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            LeaderBoardMoneyFragment leaderBoardMoneyFragment = new LeaderBoardMoneyFragment();
            Bundle bundle =new Bundle();
            bundle.putString("type",i+"");
            leaderBoardMoneyFragment.setArguments(bundle);
            fragmentList.add(leaderBoardMoneyFragment);
        }

        fragmentList.add(new LeaderBoardStarFragment());
        LeaderBoarPagerAdapter leaderBoarPagerAdapter =new LeaderBoarPagerAdapter(getSupportFragmentManager(),stringList,fragmentList);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(leaderBoarPagerAdapter);
        slding_tab.setViewPager(viewPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
