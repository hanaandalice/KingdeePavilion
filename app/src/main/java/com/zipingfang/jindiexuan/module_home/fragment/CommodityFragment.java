package com.zipingfang.jindiexuan.module_home.fragment;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.xilada.xldutils.fragment.VerticalLinearRecyclerViewFragment;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.tool.Densityuitl;
import com.xilada.xldutils.tool.Srceen;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_home.HomeFragment;
import com.zipingfang.jindiexuan.module_home.adapter.HomeCommodityAdapter;
import com.zipingfang.jindiexuan.module_home.model.CateGoodsModel;
import com.zipingfang.jindiexuan.module_home.model.HomeCommodityModel;
import com.zipingfang.jindiexuan.view.GridSpacingItemDecoration;
import com.zipingfang.jindiexuan.view.superRefreshView.SuperSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/18.
 */

public class CommodityFragment extends BaseLazyFragment implements AppBarLayout.OnOffsetChangedListener{

    private static final String TAG = "CommodityFragment";
    private HomeCommodityAdapter homeCommodityAdapter;
    private RecyclerView recyclerView;
    private SuperSwipeRefreshLayout swipe_refresh;
    @BindView(R.id.mAppBar)
    AppBarLayout mAppBar;
    private String cate_id;
    private int page =1;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_commodity;
    }

    @Override
    protected void onFirstVisibleToUser() {
        recyclerView =findViewById(R.id.recyclerView);
        swipe_refresh =findViewById(R.id.swipe_refresh);
        addListener();
        Bundle bundle =getArguments();
        cate_id = bundle.getString("cate_id");
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        GridSpacingItemDecoration layoutDecoration = new GridSpacingItemDecoration(2, Densityuitl.dip2px(getActivity(),10),Densityuitl.dip2px(getActivity(),10),false);
        recyclerView.addItemDecoration(layoutDecoration);
        int height = Srceen.getScreen(getActivity())[0];
        homeCommodityAdapter = new HomeCommodityAdapter(cateGoodsModelList,getActivity(),height);
        recyclerView.setAdapter(homeCommodityAdapter);
        initRefreshView();
        retrieveData();
    }
    private void initRefreshView() {
        swipe_refresh.setHeaderViewBackgroundColor(0xffffffff);
        swipe_refresh.setHeaderView(createHeaderView());// add headerView
        swipe_refresh.setFooterView(createFooterView());
        swipe_refresh.setTargetScrollWithLayout(true);
        swipe_refresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                swipe_refresh.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }
                    @Override
                    public void onPullDistance(int distance) {
                        // pull distance
                    }
                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });
        swipe_refresh.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        footerImageView.setVisibility(View.VISIBLE);
                        footerProgressBar.setVisibility(View.GONE);
                        swipe_refresh.setLoadMore(false);
                    }
                }, 3000);
            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }

            @Override
            public void onPushDistance(int distance) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void retrieveData() {
        if(TextUtils.isEmpty(cate_id))return;
        RequestManager.getCateGoods(cate_id, page, new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                parserCate(response.getJsonArray());
                if (page<=1){
                    if (response.getJsonArray().length()<=6) {
                        swipe_refresh.setRefreshing(false);
                    }
                }
                page++;
                refreshData();
            }
            @Override
            public void onResult() {
                super.onResult();

            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                if (null!=getActivity()) {
                    Toast.create(getActivity()).show(""+e);
                }
            }
        });
    }

    private void refreshData() {
        if (null!=homeCommodityAdapter) {
            homeCommodityAdapter.notifyDataSetChanged();
        }

    }

    List<CateGoodsModel> cateGoodsModelList =new ArrayList<>();
    private void parserCate(JSONArray jsonArray) {
        cateGoodsModelList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object =jsonArray.optJSONObject(i);
            CateGoodsModel cateGoodsModel =new CateGoodsModel();
            cateGoodsModel.setGid(object.optString("gid"));
            cateGoodsModel.setCate_id(object.optString("cate_id"));
            cateGoodsModel.setGoods_name(object.optString("goods_name"));
            cateGoodsModel.setWeight(object.optString("weight"));
            cateGoodsModel.setPic(object.optString("pic"));
            cateGoodsModel.setIs_new(object.optString("is_new"));
            cateGoodsModel.setIs_hot(object.optString("is_hot"));
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);

        }
    }
    @Override
    protected void onVisibleToUser() {
        addListener();
    }

    @Override
    protected void onInvisibleToUser() {
        removeListener();
    }
    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.mipmap.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }
    private View createHeaderView() {
        View headerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.mipmap.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }
    public void addListener(AppBarLayout.OnOffsetChangedListener listener) {
        if (mAppBar!=null)
            mAppBar.addOnOffsetChangedListener(listener);
    }

    public void removeListener(AppBarLayout.OnOffsetChangedListener listener) {
        if (mAppBar!=null)
            mAppBar.removeOnOffsetChangedListener(listener);
    }
    private void addListener() {
        Fragment fragment = getParentFragment();
        Log.d(TAG, "addListener: "+(fragment != null));
        if (fragment != null && !fragment.isDetached() && fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).addListener(this);
        }
    }
    private void removeListener() {
        Fragment fragment = getParentFragment();
        if (fragment != null && !fragment.isDetached() && fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).removeListener(this);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.d(TAG, "onOffsetChanged: ----------->"+verticalOffset);
        Toast.create(getActivity()).show(""+verticalOffset);
        swipe_refresh.setEnabled(verticalOffset == 0);
    }
}
