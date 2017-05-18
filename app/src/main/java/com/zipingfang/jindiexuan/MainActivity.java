package com.zipingfang.jindiexuan;

import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.utils.PermissionManager;
import com.zipingfang.jindiexuan.entity.TabEntity;
import com.zipingfang.jindiexuan.module_grabone.GraboneFragment;
import com.zipingfang.jindiexuan.module_home.HomeFragment;
import com.zipingfang.jindiexuan.module_orderform.OrderFormFragment;
import com.zipingfang.jindiexuan.module_user.UserFragment;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends TitleBarActivity {

    @BindView(R.id.bottom_common_layout)
    CommonTabLayout bottom_common_layout;
    private int[] mIconUnselectIds = {
            R.mipmap.icon_kp_unselect_home, R.mipmap.icon_kp_unselect_grabone,
            R.mipmap.icon_kp_unselect_order_form, R.mipmap.icon_kp_unselect_user};
    private int[] mIconSelectIds = {
            R.mipmap.icon_kp_select_home, R.mipmap.icon_kp_select_grabone,
            R.mipmap.icon_kp_select_order_form, R.mipmap.icon_kp_select_user};
    private String[] mTitles =new String[4];
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private Unbinder unbinder;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("首页");
        hideLeftButton();
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        PermissionManager.request(this, new String[]{
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, "正常使用必需的权限", 24);
        mTitles[0]="首页";
        mTitles[1]="抢单";
        mTitles[2]="订单";
        mTitles[3]="我的";
        mFragments.add(new HomeFragment());
        mFragments.add(new GraboneFragment());
        mFragments.add(new OrderFormFragment());
        mFragments.add(new UserFragment());
        for (int i = 0; i < mFragments.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        bottom_common_layout.setTabData(mTabEntities, this, R.id.ct_fragment, mFragments);
        bottom_common_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }
            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
