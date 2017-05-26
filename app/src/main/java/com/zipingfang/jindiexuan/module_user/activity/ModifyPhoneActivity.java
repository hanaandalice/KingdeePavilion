package com.zipingfang.jindiexuan.module_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.tool.IsMobilieNum;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.view.XEditText;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ModifyPhoneActivity extends TitleBarActivity {

    @BindView(R.id.et_phone)
    XEditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.tv_prompt)
    TextView tv_prompt;

    private Unbinder unbinder;
    private int num =0;
    private TimeCount timeCount;
    public static final String DATA ="data";
    @Override
    protected int setContentLayout() {
        return R.layout.activity_modify_phone;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("修改手机号");
        normalTypeFace();
        setRightButton("确定", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent =new Intent();
                Bundle bundle =new Bundle();
                bundle.putString(DATA,et_phone.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        timeCount =new TimeCount(60000,1000);
        et_phone.setDrawableRightListener(new XEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                et_phone.setText("");
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
                    tv_get_code.setClickable(true);
                    return;
                }
                if (!IsMobilieNum.isMobileNumber(et_phone.getText().toString())) {
                    tv_prompt.setText("手机格式不正确");
                    tv_get_code.setClickable(true);
                    return;
                }
                num =60;
                timeCount.start();
                tv_get_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_hint_a5_bg));
                tv_get_code.setTextColor(getResources().getColor(R.color.white));
                break;
        }
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
