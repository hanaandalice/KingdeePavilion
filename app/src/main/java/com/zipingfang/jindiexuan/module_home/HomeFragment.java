package com.zipingfang.jindiexuan.module_home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.xilada.xldutils.adapter.RecyclingPagerAdapter;
import com.xilada.xldutils.adapter.RecyclingUnlimitedPagerAdapter;
import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.xilada.xldutils.view.BannerLayout;
import com.xilada.xldutils.view.utils.ViewHolder;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_home.adapter.SlidingFragmentViewPager;
import com.zipingfang.jindiexuan.view.view_switcher.UpDownViewSwitcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class HomeFragment extends BaseLazyFragment {
    private static final String TAG = "HomeFragment";
    private BannerLayout bannerLayout;
    private RecyclingUnlimitedPagerAdapter unlimitedPagerAdapter;
    private UpDownViewSwitcher upDownViewSwitcher;
    private SlidingTabLayout slding_tab;
    private ViewPager viewPager;
    private AppBarLayout mAppBar;
    private List<String> list = new ArrayList<>();
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }
    @Override
    protected void onFirstVisibleToUser() {

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bannerLayout =findViewById(R.id.bannerLayout);
        upDownViewSwitcher =findViewById(R.id.upDownViewSwitcher);
        slding_tab =findViewById(R.id.slding_tab);
        viewPager =findViewById(R.id.viewPager);
        mAppBar =findViewById(R.id.mAppBar);

        bannerLayout.setBannerItemListener(new BannerLayout.BannerItemListener() {
            @Override
            public void onItem(int position) {

            }
            @Override
            public void onPageSelected(int position) {

            }
        });
        list.add("https://raw.githubusercontent.com/youth5201314/banner/master/app/src/main/res/mipmap-xhdpi/b3.jpg");
        list.add("https://raw.githubusercontent.com/youth5201314/banner/master/app/src/main/res/mipmap-xhdpi/b1.jpg");
        list.add("https://raw.githubusercontent.com/youth5201314/banner/master/app/src/main/res/mipmap-xhdpi/b2.jpg");
        unlimitedPagerAdapter = new RecyclingUnlimitedPagerAdapter(bannerLayout.getAutoScrollViewPager(),getActivity(), list, R.layout.item_banner_imgae) {
            @Override
            protected void onBind(int position, Object data, ViewHolder holder) {
                Glide.with(getActivity()).load(data).centerCrop().into(holder.<ImageView>bind(R.id.img));
            }
        };
        bannerLayout.setAdapter(unlimitedPagerAdapter);
        bannerLayout.showIndicator(true);
        bannerLayout.startAutoScroll();

        final List<String> stringList =new ArrayList<>();
        stringList.add("我们推出了一款水果口味的蛋糕");
        stringList.add("提拉米苏是我们店的主打商品");
        stringList.add("新烤的蓝莓蛋挞新鲜出炉");
        upDownViewSwitcher.setSwitcheNextViewListener(new UpDownViewSwitcher.SwitchNextViewListener() {
            @Override
            public void switchTONextView(View nextView, int index) {
                if (nextView == null) return;
                String title =stringList.get(index%stringList.size());
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(title);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        });
        upDownViewSwitcher.setContentLayout(R.layout.home_switch_view);

        List<String> stringTitle =new ArrayList<>();
        stringTitle.add("热销");
        stringTitle.add("新品");
        stringTitle.add("起司");
        stringTitle.add("水果");
        stringTitle.add("抹茶");
        viewPager.setOffscreenPageLimit(2);
        SlidingFragmentViewPager slidingFragmentViewPager = new SlidingFragmentViewPager(getActivity().getSupportFragmentManager(),getActivity(),stringTitle);
        viewPager.setAdapter(slidingFragmentViewPager);
        slding_tab.setViewPager(viewPager);
    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

    }
    public void addListener(AppBarLayout.OnOffsetChangedListener listener) {
        if (mAppBar!=null)
            mAppBar.addOnOffsetChangedListener(listener);
    }

    public void removeListener(AppBarLayout.OnOffsetChangedListener listener) {
        if (mAppBar!=null)
            mAppBar.removeOnOffsetChangedListener(listener);
    }
}
