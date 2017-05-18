package com.xilada.xldutils.activitys;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xilada.xldutils.R;
import com.xilada.xldutils.adapter.HeaderAndFooterRecyclerAdapter;
import com.xilada.xldutils.utils.DensityUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * 竖式列表RecyclerViewActivity/ListView型
 */
public abstract class VerticalLinearRecyclerViewActivity extends TitleBarActivity {

    private RelativeLayout emptyView;
    private TextView tv_empty_data;

    protected abstract RecyclerView.Adapter setAdapter();

    /**
     * 下拉刷新
     */
    protected abstract void pullDownRefresh();

    /**
     * 加载更多
     */
    protected abstract void loadMore();

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView recyclerView;
    private RecyclerView.ItemDecoration defaultItemDecoration;
    private int lastVisibleItem = 0;
    private boolean isLoadMore = false;
    private TextView loadMoreView;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_recycler_view;
    }

    RecyclerView.AdapterDataObserver dataObserver;

    @Override
    protected void initView() {
        mSwipeRefreshLayout = bind(R.id.mSwipeRefreshLayout);
        recyclerView = bind(R.id.mRecyclerView);
        emptyView = bind(R.id.emptyView);
        tv_empty_data = bind(R.id.tv_empty_data);
        recyclerView.setHasFixedSize(true);
        defaultItemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.dividing_line_color)
                //.marginResId(R.dimen.activity_horizontal_margin, R.dimen.activity_horizontal_margin)
                .size(1)
                .build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnScrollListener(onScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshing(true);
                //重置可见item值。避免不足一页刷新后无法上拉加载
                pullDownRefresh();
            }
        });
        if (setAdapter() != null) {
            RecyclerView.Adapter adapter = setAdapter();
            if (adapter instanceof HeaderAndFooterRecyclerAdapter) {
                loadMoreView = new TextView(this);
                loadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                loadMoreView.setHeight(DensityUtil.dip2px(this,48));
                loadMoreView.setBackgroundColor(Color.WHITE);
                loadMoreView.setText("载入更多...");
                loadMoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                loadMoreView.setTextColor(getResources().getColor(R.color.textColor));
                loadMoreView.setGravity(Gravity.CENTER);
                loadMoreView.setPadding(0, DensityUtil.dip2px(this, 16), 0, DensityUtil.dip2px(this, 16));
                ((HeaderAndFooterRecyclerAdapter) adapter).setFooterView(loadMoreView);
            }
            recyclerView.setAdapter(adapter);

            setObserver(adapter);
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pullDownRefresh();
                }
            });
        }

    }

    private void setObserver(RecyclerView.Adapter adapter) {
        dataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                RecyclerView.Adapter a = recyclerView.getAdapter();
                if (a == null) {
                    return;
                }
                int size = a.getItemCount();
                if (a instanceof HeaderAndFooterRecyclerAdapter) {
                    size = ((HeaderAndFooterRecyclerAdapter) a).getDataItemCount();
                }
                if (size <= 0) {
                    if (useDefaultEmptyView()) {
                        emptyView.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    }

                } else {
                    emptyView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                }
            }
        };
        adapter.registerAdapterDataObserver(dataObserver);
    }

    protected void setEmptyTxt(String data, @NonNull int drawable) {
        tv_empty_data.setText(data);
        tv_empty_data.setCompoundDrawablesWithIntrinsicBounds(null,mContext.getResources().getDrawable(drawable),null,null);
    }


    protected boolean useDefaultEmptyView() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recyclerView != null && onScrollListener != null) {
            recyclerView.removeOnScrollListener(onScrollListener);
        }
    }

    /**
     * listView滑动监听
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView view, int newState) {

            RecyclerView.Adapter adapter = view.getAdapter();
            resetLastVisibleItem();
            if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter != null
                    && lastVisibleItem + 1 == adapter.getItemCount() && !mSwipeRefreshLayout.isRefreshing() && !isLoadMore) {
                if (adapter instanceof HeaderAndFooterRecyclerAdapter) {

                    if (loadMoreView != null) {
                        loadMoreView.setText("载入更多...");
                        loadMoreView.setVisibility(View.VISIBLE);
                    }
                    isLoadMore = true;
                    loadMore();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
        }
    };

    /**
     * 重新获取可见item的位置
     */
    private void resetLastVisibleItem() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
            lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
    }

    /**
     * 设置刷新状态
     *
     * @param refreshing 刷新状态
     */
    protected void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
        this.isLoadMore = refreshing;
        if (loadMoreView != null) {
            loadMoreView.setVisibility(View.GONE);
        }
    }

    protected void setLoadMoreText(CharSequence text) {
        if (loadMoreView != null) {
            loadMoreView.setText(text);
            if (loadMoreView.getVisibility() != View.VISIBLE) {
                loadMoreView.setVisibility(View.VISIBLE);
            }
        }
    }

//    /**
//     * 设置加载更多状态
//     * @param isLoadMore 是否在加载更多
//     */
//    protected void setLoadMore(boolean isLoadMore){
//        this.isLoadMore = isLoadMore;
//    }

    /**
     * 设置分割线
     *
     * @param itemDecoration 样式
     */
    protected void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration == null) {
            recyclerView.addItemDecoration(defaultItemDecoration);
            return;
        }
        if (defaultItemDecoration != null) {
            recyclerView.removeItemDecoration(defaultItemDecoration);
        }
        defaultItemDecoration = itemDecoration;
        recyclerView.addItemDecoration(defaultItemDecoration);
    }

    protected int DEFAULT_MARGIN_DIVIDER = 0;
    protected int DEFAULT_DIVIDER = 1;

    protected void setItemDecoration(int type, int size) {
        HorizontalDividerItemDecoration itemDecoration;
        if (type == DEFAULT_MARGIN_DIVIDER) {
            itemDecoration = new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                    .margin(DensityUtil.dip2px(this, 16))
                    .size(size)
                    .color(this.getResources().getColor(R.color.dividing_line_color))
                    .build();
            setItemDecoration(itemDecoration);
        } else if (type == DEFAULT_DIVIDER) {
            itemDecoration = new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                    .size(size)
                    .color(this.getResources().getColor(R.color.dividing_line_color))
                    .build();
            setItemDecoration(itemDecoration);
        }
    }
}
