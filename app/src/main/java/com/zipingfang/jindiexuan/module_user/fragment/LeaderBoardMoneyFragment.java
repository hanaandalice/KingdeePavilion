package com.zipingfang.jindiexuan.module_user.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.xilada.xldutils.fragment.VerticalLinearRecyclerViewFragment;
import com.xilada.xldutils.tool.Densityuitl;
import com.xilada.xldutils.tool.Srceen;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_home.adapter.HomeCommodityAdapter;
import com.zipingfang.jindiexuan.module_user.adapter.LeaderBoardMoneyAdapter;
import com.zipingfang.jindiexuan.module_user.model.LeaderBoardMoneyModel;
import com.zipingfang.jindiexuan.view.GridSpacingItemDecoration;
import com.zipingfang.jindiexuan.view.MyRecyclerDetorration;
import com.zipingfang.jindiexuan.view.superRefreshView.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class LeaderBoardMoneyFragment extends BaseLazyFragment {
    private LeaderBoardMoneyAdapter leaderBoardMoneyAdapter;
    private List<LeaderBoardMoneyModel> leaderBoardMoneyModelList =new ArrayList<>();

    private RecyclerView recyclerView;
    private SuperSwipeRefreshLayout swipe_refresh;
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

    private static final String TAG = "LeaderBoardMoneyFragmen";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_leader_board_money;
    }

    @Override
    protected void onFirstVisibleToUser() {
        recyclerView =findViewById(R.id.recyclerView);
        swipe_refresh =findViewById(R.id.swipe_refresh);
        Bundle bundle =getArguments();
        cate_id = bundle.getString("cate_id");
        String type = bundle.getString("type");
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyRecyclerDetorration(getActivity(), LinearLayout.VERTICAL,R.drawable.divider_line));
        int height = Srceen.getScreen(getActivity())[0];
        for (int i = 0; i < 8; i++) {
            leaderBoardMoneyModelList.add(new LeaderBoardMoneyModel());
        }
        leaderBoardMoneyAdapter =new LeaderBoardMoneyAdapter(leaderBoardMoneyModelList,getActivity(),type);
        recyclerView.setAdapter(leaderBoardMoneyAdapter);
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

    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

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
}
