package com.zipingfang.jindiexuan.module_login.activity;

import android.os.CountDownTimer;
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
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.view.gradation.StatusBarUtil;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/18.
 */

public class RegisterActivity extends TitleBarActivity {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.tv_service_agreement)
    TextView tv_service_agreement;
    @BindView(R.id.tv_prompt)
    TextView tv_prompt;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_login)
    TextView tv_login;

    private Unbinder unbinder;
    private int num =0;
    private TimeCount timeCount;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        hideTitle(true);
        unbinder = ButterKnife.bind(this);
        timeCount =new TimeCount(60000,1000);

    }
    @OnClick({R.id.tv_get_code
            ,R.id.tv_service_agreement
            ,R.id.tv_register
            ,R.id.tv_login})
    void onClicks(View view){
        switch (view.getId()) {
            case R.id.tv_get_code:
                tv_get_code.setClickable(false);
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    tv_prompt.setText("请输入手机号");
                    handler.postDelayed(runnable,2000);
                    tv_get_code.setClickable(true);
                    return;
                }
                if (!IsMobilieNum.isMobileNumber(et_phone.getText().toString())) {
                    tv_prompt.setText("手机格式不正确");
                    handler.postDelayed(runnable,2000);
                    tv_get_code.setClickable(true);
                    return;
                }
                num =60;
                timeCount.start();
                tv_get_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_hint_a5_bg));
                getCode();
                break;
            case R.id.tv_service_agreement:
                goActivity(ServiceAgreementActivity.class);
                break;
            case R.id.tv_register:
                tv_register.setClickable(false);
                handler.postDelayed(runnable,2000);
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    tv_prompt.setText("请输入手机号");
                    return;
                }
                if (!IsMobilieNum.isMobileNumber(et_phone.getText().toString())) {
                    tv_prompt.setText("手机格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(et_code.getText().toString())) {
                    tv_prompt.setText("请输验证码");
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    tv_prompt.setText("请输密码");
                    return;
                }
                if (TextUtils.isEmpty(et_confirm_password.getText().toString())) {
                    tv_prompt.setText("请输确认密码");
                    return;
                }
                if (!TextUtils.equals(et_password.getText().toString(),et_confirm_password.getText().toString())) {
                    tv_prompt.setText("两次密码输入不同");
                    return;
                }
                goActivity(RegisterPersonalInformationActivity.class);
                finish();
//                registerInformation();
                break;
            case R.id.tv_login:
                finish();
                break;
        }
    }

    private void registerInformation() {
        RequestManager.register(et_phone.getText().toString(), et_code.getText().toString(), et_password.getText().toString(), et_confirm_password.getText().toString(), new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                Toast.create(RegisterActivity.this).show(""+response.toString());
                goActivity(RegisterPersonalInformationActivity.class);
                finish();
            }

            @Override
            public void onResult() {
                super.onResult();
            }
            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                Toast.create(RegisterActivity.this).show(""+e);
            }
        });
    }

    private static final String TAG = "RegisterActivity";
    private void getCode() {
        RequestManager.getMsg(et_phone.getText().toString(), new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                Toast.create(RegisterActivity.this).show(""+response.getString());
                Log.d(TAG, "onResponse: --------->"+response.getMessage());
            }
            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                Toast.create(RegisterActivity.this).show(""+e);
            }

            @Override
            public void onResult() {
                super.onResult();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
        if (null!=timeCount) {
            timeCount.cancel();
            timeCount =null;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (null!=timeCount) {
            timeCount.onFinish();
            timeCount.cancel();
        }
    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {// 计时完毕
            tv_get_code.setText(getResources().getString(R.string.get_code));
            tv_get_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_transparent_bg_line_white_1dp));
            tv_get_code.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            num--;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_get_code.setText(num+"s");
                }
            });
            tv_get_code.setClickable(false);
        }
    }
    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            if (null!=tv_prompt) {
                tv_prompt.setText("");
                tv_register.setClickable(true);
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
