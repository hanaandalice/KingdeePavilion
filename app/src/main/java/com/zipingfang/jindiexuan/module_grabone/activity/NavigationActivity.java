package com.zipingfang.jindiexuan.module_grabone.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.xilada.xldutils.activitys.BaseActivity;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.utils.LocationHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/31.
 */

public class NavigationActivity extends BaseActivity implements BaiduMap.OnMapLoadedCallback, BaiduMap.OnMapStatusChangeListener{
    private static final String APP_FOLDER_NAME = "kingdeePavilion_Navigation";
    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    @BindView(R.id.mapView)
    MapView mapView;
    private Unbinder unbinder;
    private BaiduMap baiduMap;
    private UiSettings mUiSettings;
    private LocationHelper helper;
    private static final int ACCURACY_CIRCLE_FILL_COLOR = 0x00000000;
    private static final int ACCURACY_CIRCLE_STROKE_COLOR = 0x00000000;
    private String mSDCardPath = null;
    private boolean hasInitSuccess = false;
    private static final String[] authBaseArr = { Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION };
    private static final String[] authComArr = { Manifest.permission.READ_PHONE_STATE };
    private static final int authBaseRequestCode = 1;
    private static final int authComRequestCode = 2;
    private BNRoutePlanNode.CoordinateType mCoordinateType = null;
    private boolean hasRequestComAuth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        unbinder = ButterKnife.bind(this);
        setBaiduMap();
        startLocation();
        BNOuterLogUtil.setLogSwitcher(true);
        if (initDirs()) {
            initNavi();
        }
    }
    private void startLocation() {
        helper = LocationHelper.getInstance();
        helper.setCallBack(new LocationHelper.LocationCallBack() {
            @Override
            public void callBack(final BDLocation bdLocation) {
                if (mapView == null)
                    return;
                // 此处设置开发者获取到的方向信息，顺时针0-360
//                        .direction(0)
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude()).build();
                baiduMap.setMyLocationData(locData);
                animateMap(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 17f);
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     if (BaiduNaviManager.isNaviInited()) {
                         showDialog("计算路线中...");
                         routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09LL,bdLocation.getLongitude(),bdLocation.getLatitude(),bdLocation.getAddrStr());
                     }
                 }
             },500);

            }
        });
        helper.start();
    }
    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType,Double longitude,Double latitude,String address) {
        mCoordinateType = coType;
        if (!hasInitSuccess) {
            dismissDialog();
            Toast.makeText(NavigationActivity.this, "还未初始化!", Toast.LENGTH_SHORT).show();
        }
        // 权限申请
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // 保证导航功能完备
            if (!hasCompletePhoneAuth()) {
                if (!hasRequestComAuth) {
                    hasRequestComAuth = true;
                    this.requestPermissions(authComArr, authComRequestCode);
                    return;
                } else {
                     dismissDialog();
                    Toast.makeText(NavigationActivity.this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        switch (coType) {
            case GCJ02: {
                sNode = new BNRoutePlanNode(116.30142, 40.05087, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(116.39750, 39.90882, "北京天安门", null, coType);
                break;
            }
            case WGS84: {
                sNode = new BNRoutePlanNode(116.300821, 40.050969, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(116.397491, 39.908749, "北京天安门", null, coType);
                break;
            }
            case BD09_MC: {
                sNode = new BNRoutePlanNode(12947471, 4846474, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(12958160, 4825947, "北京天安门", null, coType);
                break;
            }
            case BD09LL: {
                sNode = new BNRoutePlanNode(longitude, latitude, address, null, coType);
                eNode = new BNRoutePlanNode(104.073785,30.610816, "火车南站", null, coType);
                break;
            }
            default:
        }
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new RoutePlanListener(sNode));
        }
    }
    private boolean hasCompletePhoneAuth() {
        // TODO Auto-generated method stub
        PackageManager pm = this.getPackageManager();
        for (String auth : authComArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    public class RoutePlanListener implements BaiduNaviManager.RoutePlanListener {
        private BNRoutePlanNode mBNRoutePlanNode = null;
        public RoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }
        @Override
        public void onJumpToNavigator() {
//            /*
//             * 设置途径点以及resetEndNode会回调该接口
//             */
//            for (Activity ac : activityList) {
//
//                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {
//                    return;
//                }
//            }
            dismissDialog();
            Intent intent = new Intent(NavigationActivity.this, StartNavigationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            com.xilada.xldutils.utils.Toast.create(NavigationActivity.this).show("计算路线失败");
        }
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    String authinfo = null;

    /**
     * 移动地图
     *
     * @param latLng 移动位置
     * @param level  缩放级别： [3.0,19.0]
     */
    private void animateMap(LatLng latLng, float level) {
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(latLng, level);
        baiduMap.animateMapStatus(update);
    }
    private void setBaiduMap() {
        //去掉百度地图自带的放大缩小按钮，以及左下角的小图标
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        baiduMap = mapView.getMap();
        mUiSettings = baiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setOnMapLoadedCallback(this);
        baiduMap.setOnMapStatusChangeListener(this);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.location_point);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker,
                ACCURACY_CIRCLE_FILL_COLOR, ACCURACY_CIRCLE_STROKE_COLOR));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        helper.stop();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mapView != null) {
            mapView.onDestroy();
            mapView =null;
        }
        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }
    @Override
    public void onMapLoaded() {

    }
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }
    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }
    private static final String TAG = "NavigationActivity";
    private void initNavi() {
        BNOuterTTSPlayerCallback ttsCallback = null;
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
//             NavigationActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(NavigationActivity.this, authinfo, Toast.LENGTH_LONG).show();
//                    }
//                });
            }
            public void initSuccess() {
//                com.xilada.xldutils.utils.Toast.create(NavigationActivity.this).show("导航启动成功");
                hasInitSuccess = true;
                initSetting();
            }

            public void initStart() {
//                Toast.makeText(NavigationActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
//                com.xilada.xldutils.utils.Toast.create(NavigationActivity.this).show("导航启动失败,请重新启动");
            }

        }, null, ttsHandler, ttsPlayStateListener);
    }
    private boolean hasBasePhoneAuth() {
        // TODO Auto-generated method stub
        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    private void initSetting() {
        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        Bundle bundle = new Bundle();
        // 必须设置APPID，否则会静音
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "9354030");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }
    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    // showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    // showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };
    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {
        @Override
        public void playEnd() {
            // showToastMsg("TTSPlayStateListener : TTS play end");
        }
        @Override
        public void playStart() {
            // showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };
}
