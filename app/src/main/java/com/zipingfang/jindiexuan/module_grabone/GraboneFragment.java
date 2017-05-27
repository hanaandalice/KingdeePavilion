package com.zipingfang.jindiexuan.module_grabone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.platform.comapi.map.B;
import com.bigkoo.pickerview.TimePickerView;
import com.flyco.tablayout.SlidingTabLayout;
import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.xilada.xldutils.tool.Densityuitl;
import com.xilada.xldutils.utils.TimeUtils;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_grabone.adapter.GraboneDeliveryAdapter;
import com.zipingfang.jindiexuan.module_grabone.fragment.GraboneOrdersFragment;
import com.zipingfang.jindiexuan.module_login.adapter.DeliveryAdapter;
import com.zipingfang.jindiexuan.module_login.model.SelectDeliveryModel;
import com.zipingfang.jindiexuan.module_user.adapter.LeaderBoarPagerAdapter;
import com.zipingfang.jindiexuan.module_user.fragment.LeaderBoardMoneyFragment;
import com.zipingfang.jindiexuan.module_user.fragment.LeaderBoardStarFragment;
import com.zipingfang.jindiexuan.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/18.
 */

public class GraboneFragment extends BaseLazyFragment implements View.OnClickListener{

    private SlidingTabLayout slding_tab;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private TextView tv_save,
            tv_start_time,
            tv_end_time,
            tv_order_all;
    private ImageView iv_filter;
    private LinearLayout layout_filter;
    private View v_gone_filter;

    private TimePickerView pvTime;
    private String type;
    private Boolean isStartTime=false;
    private Boolean isEndTime=false;
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
        recyclerView =findViewById(R.id.recyclerView);
        tv_save =findViewById(R.id.tv_save);
        iv_filter =findViewById(R.id.iv_filter);
        layout_filter =findViewById(R.id.layout_filter);
        v_gone_filter =findViewById(R.id.v_gone_filter);
        tv_start_time =findViewById(R.id.tv_start_time);
        tv_end_time =findViewById(R.id.tv_end_time);
        tv_order_all =findViewById(R.id.tv_order_all);

        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setRange(TimeUtils.getTimeYear(System.currentTimeMillis()), TimeUtils.getTimeYear(System.currentTimeMillis())+20);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                if (TextUtils.equals("1",type)){
                    isStartTime =true;
                    tv_start_time.setText(TimeUtils.getBaseTime(date));
                }else if (TextUtils.equals("2",type)){
                    if (TimeUtils.parseTimeMillisecond(tv_start_time.getText().toString())<date.getTime()){
                        isEndTime =true;
                        tv_end_time.setText(TimeUtils.getBaseTime(date));
                    }else{
                        Toast.create(getActivity()).show("结束时间需大于开始时间");
                    }
                }
            if (isStartTime&&isEndTime){
                tv_order_all.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_white_bg_line_1dp));
            }
            }
        });

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

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        List<SelectDeliveryModel> selectDeliveryModelList=new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            selectDeliveryModelList.add(new SelectDeliveryModel());
        }
        selectDeliveryModelList.add(new SelectDeliveryModel());
        final GraboneDeliveryAdapter deliveryAdapter =new GraboneDeliveryAdapter(selectDeliveryModelList,getActivity());
        recyclerView.setAdapter(deliveryAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, Densityuitl.dip2px(getActivity(),16),Densityuitl.dip2px(getActivity(),10),false));
        deliveryAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                deliveryAdapter.setSelectPosition(position);
            }
        });
        tv_save.setOnClickListener(this);
        iv_filter.setOnClickListener(this);
        v_gone_filter.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_order_all.setOnClickListener(this);
    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                layout_filter.setVisibility(View.GONE);
                break;
            case R.id.iv_filter:
                if (layout_filter.getVisibility()== View.VISIBLE) {
                    layout_filter.setVisibility(View.GONE);
                }else{
                    layout_filter.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.v_gone_filter:
                layout_filter.setVisibility(View.GONE);
                break;
            case R.id.tv_start_time:
                type ="1";
                pvTime.show();
                break;
            case R.id.tv_end_time:
                type ="2";
                pvTime.show();
                break;
            case R.id.tv_order_all:
                isStartTime =false;
                isEndTime=false;
                tv_order_all.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_white_bg_line_accent_7e_1dp));
                break;
        }
    }
}
