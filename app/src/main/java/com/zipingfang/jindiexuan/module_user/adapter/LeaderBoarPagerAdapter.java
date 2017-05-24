package com.zipingfang.jindiexuan.module_user.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.zipingfang.jindiexuan.module_home.fragment.CommodityFragment;
import com.zipingfang.jindiexuan.module_home.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class LeaderBoarPagerAdapter extends FragmentPagerAdapter {

    private List<String> stringList =new ArrayList<>();
    private List<Fragment> fragmentList =new ArrayList<>();
    public LeaderBoarPagerAdapter(FragmentManager fm, List<String> stringList,List<Fragment> fragmentList) {
        super(fm);
        this.stringList =stringList;
        this.fragmentList =fragmentList;
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
