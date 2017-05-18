package com.zipingfang.jindiexuan.module_home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.zipingfang.jindiexuan.module_home.fragment.CommodityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
public class SlidingFragmentViewPager extends FragmentPagerAdapter {
    private List<String> stringList =new ArrayList<>();
    private List<Fragment> fragmentList =new ArrayList<>();
    public SlidingFragmentViewPager(FragmentManager fm, Context context, List<String> stringTitle) {
        super(fm);
        this.stringList =stringTitle;
        for (int i = 0; i < stringList.size(); i++) {
            CommodityFragment commodityFragment =new CommodityFragment();
            fragmentList.add(commodityFragment);
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
    @Override
    public int getCount() {
        return stringList.size();
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
