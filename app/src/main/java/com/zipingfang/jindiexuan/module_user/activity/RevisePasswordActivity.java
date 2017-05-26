package com.zipingfang.jindiexuan.module_user.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.tool.IsMobilieNum;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.utils.Const;

import org.json.JSONException;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/24.
 */

public class RevisePasswordActivity extends TitleBarActivity {

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.tv_prompt)
    TextView tv_prompt;

    private Unbinder unbinder;
    private int num =0;
    private TimeCount timeCount;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_revise_password;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("修改密码");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        timeCount =new TimeCount(60000,1000);
        RxView.clicks(tv_confirm)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        handler.postDelayed(runnable,2000);
                        if (TextUtils.isEmpty(et_phone.getText().toString())) {
                            tv_prompt.setText("手机号为空");
                            return;
                        }
                        if (!IsMobilieNum.isMobileNumber(et_phone.getText().toString())) {
                            tv_prompt.setText("手机格式不正确");
                            return;
                        }
                        if (TextUtils.isEmpty(et_code.getText().toString())) {
                            tv_prompt.setText("验证码为空");
                            return;
                        }
                        if (TextUtils.isEmpty(et_password.getText().toString())) {
                            tv_prompt.setText("密码不能为空");
                            return;
                        }
                        if (TextUtils.isEmpty(et_confirm_password.getText().toString())) {
                            tv_prompt.setText("确认密码不能为空");
                            return;
                        }
                        if (!TextUtils.equals(et_password.getText().toString(),et_confirm_password.getText().toString())) {
                            tv_prompt.setText("两次密码输入不同,请重新输入");
                            return;
                        }
                        restPassword();
                    }
                });
    }
    private void restPassword() {
        showDialog();
        RequestManager.updatePassword(SharedPreferencesUtils.getString(Const.User.TOKEN)
                , et_phone.getText().toString()
                , et_code.getText().toString()
                , et_password.getText().toString()
                , et_confirm_password.getText().toString()
                , new HttpUtils.ResultCallback<ResultData>() {
                    @Override
                    public void onResponse(ResultData response) {
                        Toast.create(RevisePasswordActivity.this).show("修改成功,请重新登录");
                        finish();
                    }
                    @Override
                    public void onError(Call call, String e) {
                        super.onError(call, e);
                        if (null!=tv_prompt) {
                            tv_prompt.setText(""+e);
                        }
                        if (null!=handler) {
                            handler.postDelayed(runnable,2000);
                        }
                    }

                    @Override
                    public void onResult() {
                        super.onResult();
                        dismissDialog();
                    }
                });
    }

    @OnClick({R.id.tv_get_code})
    void onClicks(View view){
        switch (view.getId()) {
            case R.id.tv_get_code:
                tv_get_code.setClickable(false);
                handler.postDelayed(runnable,2000);
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    tv_prompt.setText("手机号为空");
                    return;
                }
                if (!IsMobilieNum.isMobileNumber(et_phone.getText().toString())) {
                    tv_prompt.setText("手机格式不正确");
                    return;
                }
                num =60;
                timeCount.start();
                tv_get_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_hint_a5_bg));
                tv_get_code.setTextColor(getResources().getColor(R.color.white));
                getCode();
                break;
        }
    }

    private void getCode() {
        RequestManager.getMsg(et_phone.getText().toString(), new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                Toast.create(RevisePasswordActivity.this).show(""+response.toString());
            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                if (null!=tv_prompt) {
                    tv_prompt.setText(""+e);
                }
                if (null!=handler) {
                    handler.postDelayed(runnable,2000);
                }
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
            tv_get_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_white_bg_line_accent_7e_1dp));
            tv_get_code.setTextColor(getResources().getColor(R.color.textAccent));
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
                tv_get_code.setClickable(true);
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
