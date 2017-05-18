package com.zipingfang.jindiexuan.module_login.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.tool.IsMobilieNum;
import com.xilada.xldutils.tool.StatusBarUtils;
import com.zipingfang.jindiexuan.MainActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    }
    @OnClick({R.id.tv_forget_password,R.id.tv_login,R.id.tv_register})
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
                goActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_register:
                goActivity(RegisterActivity.class);
                break;
        }
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
