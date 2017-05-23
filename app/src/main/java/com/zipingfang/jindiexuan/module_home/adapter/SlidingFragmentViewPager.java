package com.zipingfang.jindiexuan.module_home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.zipingfang.jindiexuan.module_home.fragment.CommodityFragment;
import com.zipingfang.jindiexuan.module_home.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
public class SlidingFragmentViewPager extends FragmentPagerAdapter {
    private List<HomeModel.CateBean> cateBeanList =new ArrayList<>();
    private List<Fragment> fragmentList =new ArrayList<>();
    public SlidingFragmentViewPager(FragmentManager fm, Context context,  List<HomeModel.CateBean> cateBeanList) {
        super(fm);
        this.cateBeanList =cateBeanList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return cateBeanList.get(position).getCate_name();
    }

    @Override
    public int getCount() {
        return cateBeanList.size();
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
    private static final String TAG = "SlidingFragmentViewPage";
    @Override
    public void notifyDataSetChanged() {
        for (int i = 0; i < cateBeanList.size(); i++) {
            CommodityFragment commodityFragment =new CommodityFragment();
            Bundle bundle =new Bundle();
            bundle.putString("cate_id",cateBeanList.get(i).getCate_id()+"");
            commodityFragment.setArguments(bundle);
            fragmentList.add(commodityFragment);
        }
        super.notifyDataSetChanged();
    }
}
