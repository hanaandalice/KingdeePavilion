package com.zipingfang.jindiexuan;

import android.content.SharedPreferences;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.zipingfang.jindiexuan.module_login.activity.AuthenticateActivity;
import com.zipingfang.jindiexuan.module_login.activity.LoginActivity;
import com.zipingfang.jindiexuan.module_login.activity.RegisterPersonalInformationActivity;
import com.zipingfang.jindiexuan.module_user.activity.PersonalInformationActivity;
import com.zipingfang.jindiexuan.utils.Const;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/8/15.
 */
public class WelComeActivity extends TitleBarActivity {
    /*跳过*/
    private TextView tv_jump_over;
    /*启动页面*/
    private ImageView first_page;
    private Boolean isLogin=false;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_welcome;
    }
    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideTitle(true);
//        /**
//         * 请求所有必要的权限----原理就是获取清单文件中申请的权限
//         */
//        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
//            @Override
//            public void onGranted() {
////              Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onDenied(String permission) {
////                Toast.makeText(WelComeActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
//            }
//        });
        initV();
        initData();
    }
    private void initV() {
        tv_jump_over =bind(R.id.tv_jump_over);
        first_page =bind(R.id.first_page);
    }
    private static final String TAG = "WelComeActivity";
    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirst()){
                    goActivity(StartActivity.class);
                }else{
                    if (SharedPreferencesUtils.getBoolean(Const.User.IS_LOGIN)){
                        goActivity(MainActivity.class);
                    }else{
                        goActivity(LoginActivity.class);
                    }
//                    goActivity(RegisterPersonalInformationActivity.class);
                }
                finish();
            }
        },2000);
//        RequestManager.getStartPageOrPlatformProtocol("1",new HttpUtils.ResultCallback<ResultData>() {
//            @Override
//            public void onResponse(ResultData response) {
//                JSONObject object =response.getJsonObject();
//                if (!TextUtils.isEmpty(object.optString("url"))){
//                    Glide.with(WelComeActivity.this).load(object.optString("url")).into(first_page);
//                }
//            }
//            @Override
//            public void onResult() {
//                super.onResult();
//            }
//            @Override
//            public void onError(Call call, String e) {
//                super.onError(call, e);
//            }
//        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    /**判断app是否第一次登陆*/
    private boolean isFirst(){
        SharedPreferences preferences =getSharedPreferences("target",MODE_PRIVATE);
        boolean isFirst =preferences.getBoolean("app_start",true);
        if (isFirst){
            preferences.edit().putBoolean("app_start",false).commit();
        }
        return isFirst;
    }

    private Handler handler = new Handler();


}
