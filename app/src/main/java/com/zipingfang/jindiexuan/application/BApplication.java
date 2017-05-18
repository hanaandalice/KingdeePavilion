package com.zipingfang.jindiexuan.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.tencent.bugly.crashreport.CrashReport;
import com.xilada.xldutils.BaseApplication;


/**
 * Created by Administrator on 2017/4/26.
 */

public class BApplication extends BaseApplication {

    public static final String SP_NAME = "com.zipingfang.jindiexuan";
    @Override
    protected String setSharedPreferencesName() {
        return SP_NAME;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(getApplicationContext(), "0b903839f9", true);
        
//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
//                .setDownsampleEnabled(true)
//                .build();
//        Fresco.initialize(this, config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
