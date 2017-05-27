package com.zipingfang.jindiexuan.module_login.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.tool.IsMobilieNum;
import com.xilada.xldutils.utils.PermissionManager;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.MainActivity;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_login.model.LoginUserModel;
import com.zipingfang.jindiexuan.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/18.
 */

public class LoginActivity extends TitleBarActivity {

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_forget_password)
    TextView tv_forget_password;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_prompt)
    TextView tv_prompt;

    private Unbinder unbinder;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        hideTitle(true);
        setViewHide(true);
        unbinder = ButterKnife.bind(this);
        PermissionManager.request(this, new String[]{
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, "正常使用必需的权限", 24);
        et_phone.setText(SharedPreferencesUtils.getString(Const.User.USER_PHONE));
    }
    @OnClick({R.id.tv_forget_password
            ,R.id.tv_login
            ,R.id.tv_register
    })
    void onClicks(View view){
        switch (view.getId()) {
            case R.id.tv_forget_password:
                goActivity(ForgetPasswordActivity.class);
                break;
            case R.id.tv_login:
                tv_login.setClickable(false);
                handler.postDelayed(runnable,2000);
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    tv_prompt.setText("请输入手机号码");
                    return;
                }
                if (!IsMobilieNum.isMobileNumber(et_phone.getText().toString())) {
                    tv_prompt.setText("手机格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    tv_prompt.setText("请输入密码");
                    return;
                }
                login();
                break;
            case R.id.tv_register:
                goActivity(RegisterActivity.class);
                break;
        }
    }
    private static final String TAG = "LoginActivity";
    private void login() {
        showDialog("登录中...");
        RequestManager.login(et_phone.getText().toString(), et_password.getText().toString(), new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                Log.d(TAG, "onResponse: --------->"+response.toString());
                JSONObject object =response.getJsonObject();
                LoginUserModel loginUserModel =new LoginUserModel();
//            "login_time":"1495504744","reg_time":"1495504503","head_pic":"","area_id":"","status":"1"}
                loginUserModel.setUid(object.optString("uid"));
                loginUserModel.setUsername(object.optString("username"));
                loginUserModel.setPassword(object.optString("password"));
                loginUserModel.setRole_id(object.optString("role_id"));
                loginUserModel.setName(object.optString("name"));
                loginUserModel.setSex(object.optString("sex"));
                loginUserModel.setPhone(object.optString("phone"));
                loginUserModel.setDriver(object.optString("driver"));
                loginUserModel.setDriver_type(object.optString("driver_type"));
                loginUserModel.setDriver_status(object.optString("driver_status"));
                loginUserModel.setMoney(object.optString("money"));
                loginUserModel.setIntegral(object.optString("integral"));
                loginUserModel.setReceive(object.optString("receive"));
                loginUserModel.setRecommend(object.optString("recommend"));
                loginUserModel.setWeixin(object.optString("weixin"));
                loginUserModel.setToken(object.optString("token"));
                loginUserModel.setLogin_time(object.optString("login_time"));
                loginUserModel.setReg_time(object.optString("reg_time"));
                loginUserModel.setHead_pic(object.optString("head_pic"));
                loginUserModel.setArea_id(object.optString("area_id"));
                loginUserModel.setStatus(object.optString("status"));


                SharedPreferencesUtils.save(Const.User.IS_LOGIN,true);
                SharedPreferencesUtils.save(Const.User.TOKEN,loginUserModel.getToken());
                SharedPreferencesUtils.save(Const.User.USER_PHONE,loginUserModel.getPhone());
                SharedPreferencesUtils.save(Const.User.NAME,loginUserModel.getName());
                SharedPreferencesUtils.save(Const.User.USER_HEAD_IMG,loginUserModel.getHead_pic());
                SharedPreferencesUtils.save(Const.User.USER_RECEIVE,loginUserModel.getReceive());
                SharedPreferencesUtils.save(Const.User.USER_DRIVER,loginUserModel.getDriver());
                SharedPreferencesUtils.save(Const.User.USER_DRIVER_TYPE,loginUserModel.getDriver_type());
                SharedPreferencesUtils.save(Const.User.USER_DRIVER_STATUS,loginUserModel.getDriver_status());
                SharedPreferencesUtils.save(Const.User.USER_SEX,loginUserModel.getSex());
                SharedPreferencesUtils.save(Const.User.USER_AREA_ID,loginUserModel.getArea_id());


                Toast.create(LoginActivity.this).show("登录成功");
                goActivity(MainActivity.class);
                finish();
            }
            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                Toast.create(LoginActivity.this).show(""+e);
            }

            @Override
            public void onResult() {
                super.onResult();
                dismissDialog();
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
    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            if (null!=tv_prompt) {
                tv_prompt.setText("");
                tv_login.setClickable(true);
            }
        }
    };
    private Handler handler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
}
