package com.zipingfang.jindiexuan.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xilada.xldutils.BaseApplication;


/**
 * Created by Administrator on 2017/4/26.
 */

public class BApplication extends BaseApplication {

    public static final String SP_NAME = "com.zipingfang.jindiexuan";
    private static BApplication instance;
    @Override
    protected String setSharedPreferencesName() {
        return SP_NAME;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
        SDKInitializer.initialize(getApplicationContext());
        //建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(getApplicationContext(), "0b903839f9", true);
//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
//                .setDownsampleEnabled(true)
//                .build();
//        Fresco.initialize(this, config);
        com.umeng.socialize.Config.DEBUG =true;
        UMShareAPI.get(this);
//        com.umeng.socialize.Config.DEBUG =true;
//        PlatformConfig.setWeixin("wx6341a7b4bc534d3e", "7c2cfb1a02d9d2031142e74bc3fa310e");//微信 appid appsecret
//        PlatformConfig.setSinaWeibo("3737857478", "236f8e7aad197ac3a29b01fab1658474","http://sns.whalecloud.com");//新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1106008918", "PWEJx8rK5ifQcP8J"); // QQ和Qzone appid appkey
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
    public static BApplication getInstance() {
        return instance;
    }
}
