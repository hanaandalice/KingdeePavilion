package com.zipingfang.jindiexuan;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.umeng.socialize.UMShareAPI;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.tool.StatusBarUtils;
import com.xilada.xldutils.utils.PermissionManager;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.zipingfang.jindiexuan.entity.TabEntity;
import com.zipingfang.jindiexuan.module_grabone.GraboneFragment;
import com.zipingfang.jindiexuan.module_home.HomeFragment;
import com.zipingfang.jindiexuan.module_orderform.OrderFormFragment;
import com.zipingfang.jindiexuan.module_user.UserFragment;
import com.zipingfang.jindiexuan.umeng.ShareBottomDialog;

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

    private static final String TAG = "MainActivity";
    @Override
    protected void initView() throws JSONException, IllegalAccessException {
//        setTitle("首页");
        hideTitle(true);
        hideLeftButton();
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
        UserFragment userFragment = new UserFragment();
        userFragment.setShareClickListener(new UserFragment.ShareClickListener() {
            @Override
            public void onClick() {
              final ShareBottomDialog shareBottomDialog =new ShareBottomDialog(MainActivity.this,MainActivity.this);
                shareBottomDialog.show();
            }
        });
        mFragments.add(userFragment);
        for (int i = 0; i < mFragments.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        bottom_common_layout.setTabData(mTabEntities, this, R.id.ct_fragment, mFragments);
        bottom_common_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position==3) {
                    if (StatusBarUtils.isMeizuFlyme()){
                        StatusBarUtils.StatusBarDarkMode(MainActivity.this,2);
                    }else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            StatusBarUtils.transparencyBar(MainActivity.this);
                        }
                    }
                    StatusBarUtils.setStatusBarColor(MainActivity.this,R.color.colorAccent);
                }else{
                    if (StatusBarUtils.isMeizuFlyme()){
                        StatusBarUtils.StatusBarLightMode(MainActivity.this,2);
                    }else{
                        StatusBarUtils.StatusBarLightMode(MainActivity.this);
                    }
                    StatusBarUtils.setStatusBarColor(MainActivity.this,R.color.white);
                }
            }
            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length <= 0) {
            return;
        }
        SharedPreferencesUtils.save(permissions[0], false);
    }

}
