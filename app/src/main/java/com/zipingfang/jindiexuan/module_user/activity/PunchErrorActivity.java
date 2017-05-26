package com.zipingfang.jindiexuan.module_user.activity;

import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/26.
 */

public class PunchErrorActivity extends TitleBarActivity {
    @BindView(R.id.tv_error_message)
    TextView tv_error_message;


    private Unbinder unbinder;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_punch_error;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("打卡失败");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        String error_message = getIntent().getStringExtra("error_message");
        tv_error_message.setText(error_message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
