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
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.view.BannerLayout;
import com.xilada.xldutils.view.utils.ViewHolder;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.Api;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_home.activity.InformationListActivity;
import com.zipingfang.jindiexuan.module_home.adapter.SlidingFragmentViewPager;
import com.zipingfang.jindiexuan.module_home.model.HomeModel;
import com.zipingfang.jindiexuan.view.view_switcher.UpDownViewSwitcher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

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

    private  SlidingFragmentViewPager slidingFragmentViewPager;
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

        unlimitedPagerAdapter = new RecyclingUnlimitedPagerAdapter<HomeModel.LunboBean>(bannerLayout.getAutoScrollViewPager(),getActivity(), lunboBeanList, R.layout.item_banner_imgae) {
            @Override
            protected void onBind(int position, HomeModel.LunboBean data, ViewHolder holder) {
                Glide.with(getActivity()).load(Api.IMG_URL+data.getPic()).centerCrop().into(holder.<ImageView>bind(R.id.img));
                Log.d(TAG, "onBind: -------->"+Api.IMG_URL+data.getPic());
            }
        };
        bannerLayout.setAdapter(unlimitedPagerAdapter);
        bannerLayout.showIndicator(true);
        bannerLayout.startAutoScroll();
        upDownViewSwitcher.setSwitcheNextViewListener(new UpDownViewSwitcher.SwitchNextViewListener() {
            @Override
            public void switchTONextView(View nextView, int index) {
                if (zixunBeanList.size()<=0)return;
                if (nextView == null) return;
                String title =zixunBeanList.get(index%zixunBeanList.size()).getContent();
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(title);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goActivity(InformationListActivity.class);
                    }
                });
            }
        });
        upDownViewSwitcher.setContentLayout(R.layout.home_switch_view);
        viewPager.setOffscreenPageLimit(2);
        slidingFragmentViewPager = new SlidingFragmentViewPager(getActivity().getSupportFragmentManager(),getActivity(),cateBeanList);
        viewPager.setAdapter(slidingFragmentViewPager);
        slding_tab.setViewPager(viewPager);
        retrieveData();
    }
    @Override
    protected void onVisibleToUser() {

    }

    private void retrieveData() {
//        showDialog();
        RequestManager.getHome(new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                JSONObject object =response.getJsonObject();
                HomeModel homeModel =new HomeModel();
                JSONArray cateArray =object.optJSONArray("cate")==null?new JSONArray():object.optJSONArray("cate");
                homeModel.setCate(parserCateArray(cateArray));
                JSONArray lunboArray =object.optJSONArray("lunbo")==null?new JSONArray():object.optJSONArray("lunbo");
                homeModel.setLunbo(parserLunboArray(lunboArray));
                JSONArray zixunArray =object.optJSONArray("zixun")==null?new JSONArray():object.optJSONArray("zixun");
                homeModel.setZixun(parserZixunArray(zixunArray));
                refreshData();

            }
            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                com.xilada.xldutils.utils.Toast.create(getActivity()).show(""+e);
            }
            @Override
            public void onResult() {
                super.onResult();
            }
        });
    }
    private void refreshData() {
        if (null!=slidingFragmentViewPager) {
            slidingFragmentViewPager.notifyDataSetChanged();
            slding_tab.notifyDataSetChanged();
        }
        if (null!=unlimitedPagerAdapter) {
            unlimitedPagerAdapter.notifyDataSetChanged();
        }
    }
    List<HomeModel.ZixunBean> zixunBeanList =new ArrayList<>();
    private List<HomeModel.ZixunBean> parserZixunArray(JSONArray zixunArray) {
        zixunBeanList.clear();
        for (int i = 0; i < zixunArray.length(); i++) {
            JSONObject object  =zixunArray.optJSONObject(i);
            HomeModel.ZixunBean zixunBean =new HomeModel.ZixunBean();
            zixunBean.setId(object.optString("id"));
            zixunBean.setTitle(object.optString("title"));
            zixunBean.setContent(object.optString("content"));
            zixunBean.setPic(object.optString("pic"));
            zixunBean.setCreate_time(object.optString("create_time"));
            zixunBeanList.add(zixunBean);
        }
        return zixunBeanList;
    }
    List<HomeModel.LunboBean> lunboBeanList =new ArrayList<>();
    private List<HomeModel.LunboBean> parserLunboArray(JSONArray lunboArray) {
        lunboBeanList.clear();
        for (int i = 0; i < lunboArray.length(); i++) {
            JSONObject object =lunboArray.optJSONObject(i);
            HomeModel.LunboBean lunboBean =new HomeModel.LunboBean();
            lunboBean.setContent(object.optString("content"));
            lunboBean.setId(object.optString("id"));
            lunboBean.setTitle(object.optString("title"));
            lunboBean.setPic(object.optString("pic"));
            lunboBean.setUrl(object.optString("url"));
            lunboBeanList.add(lunboBean);
        }
        return lunboBeanList;
    }
    List<HomeModel.CateBean> cateBeanList =new ArrayList<>();
    private List<HomeModel.CateBean> parserCateArray(JSONArray cateArray) {
        cateBeanList.clear();
        for (int i = 0; i < cateArray.length(); i++) {
            JSONObject object =cateArray.optJSONObject(i);
            HomeModel.CateBean cateBean =new HomeModel.CateBean();
            cateBean.setCate_id(object.optInt("cate_id"));
            cateBean.setCate_name(object.optString("cate_name"));
            cateBeanList.add(cateBean);
        }
        return cateBeanList;
    }

    @Override
    protected void onInvisibleToUser() {
        Log.d(TAG, "onInvisibleToUser: ");
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
