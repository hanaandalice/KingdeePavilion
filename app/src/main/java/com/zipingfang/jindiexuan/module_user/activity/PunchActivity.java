package com.zipingfang.jindiexuan.module_user.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.jakewharton.rxbinding.view.RxView;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.xilada.xldutils.utils.TimeUtils;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.utils.Const;
import com.zipingfang.jindiexuan.utils.LocationHelper;
import com.zipingfang.jindiexuan.utils.UpLocationHelper;

import org.json.JSONException;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/22.
 */

public class PunchActivity extends TitleBarActivity implements BaiduMap.OnMapLoadedCallback, BaiduMap.OnMapStatusChangeListener {

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tv_clock)
    TextView tv_clock;
    @BindView(R.id.tv_position)
    TextView tv_position;
    @BindView(R.id.tv_punch)
    TextView tv_punch;

    private Unbinder unbinder;
    private BaiduMap baiduMap;
    private UiSettings mUiSettings;
    private LocationHelper helper;
    private UpLocationHelper upHelper;
    private GeoCoder mGeoCoder;
    private static final int ACCURACY_CIRCLE_FILL_COLOR = 0x00000000;
    private static final int ACCURACY_CIRCLE_STROKE_COLOR = 0x00000000;
    private String type;
    private String address,
            longitude,
            lagitude;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_punch;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("打卡");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        setBaiduMap();
        startLocation();
        RxView.clicks(tv_punch)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (!TextUtils.isEmpty(type)) {
                            if (TextUtils.equals("1",type)) {
                                punch("2");
                            }else if (TextUtils.equals("2",type)){
                                punch("1");
                            }else if (TextUtils.equals("3",type)){

                            }
                        }
                    }
                });
        tv_clock.setText("时间："+TimeUtils.getTimeNotSecond(System.currentTimeMillis()));
        getDakaType();
    }

    private void punch(String type) {
        showDialog("打卡中...");
        RequestManager.daka(SharedPreferencesUtils.getString(Const.User.TOKEN)
                , type
                , address
                , longitude
                , lagitude
                , new HttpUtils.ResultCallback<ResultData>() {
                    @Override
                    public void onResponse(ResultData response) {
                        Bundle bundle =new Bundle();
                        bundle.putString("time",tv_clock.getText().toString());
                        bundle.putString("position",tv_position.getText().toString());
                        goActivity(PunchSuccessActivity.class,bundle);
                    }
                    @Override
                    public void onError(Call call, String e) {
                        super.onError(call, e);
                        Bundle bundle1 =new Bundle();
                        bundle1.putString("error_message",e);
                        goActivity(PunchErrorActivity.class,bundle1);
                    }

                    @Override
                    public void onResult() {
                        super.onResult();
                    }
                });
    }

    private void getDakaType() {
        showDialog();
        RequestManager.getDakaType(SharedPreferencesUtils.getString(Const.User.TOKEN), new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                tv_punch.setText(response.getMessage());
                type =response.getString();
            }

            @Override
            public void onResult() {
                super.onResult();
                dismissDialog();
            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                Toast.create(PunchActivity.this).show(""+e);
            }
        });
    }

    private static final String TAG = "PunchActivity";
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_position.setText("地点："+bdLocation.getAddrStr());
                    }
                });
                animateMap(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 17f);
                longitude =bdLocation.getLongitude()+"";
                lagitude =bdLocation.getLatitude()+"";

            }
        });
        helper.start();
    }

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
        Log.d(TAG, "onDestroy: ------->"+(helper==null));
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
}
