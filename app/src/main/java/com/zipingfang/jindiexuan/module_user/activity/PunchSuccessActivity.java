package com.zipingfang.jindiexuan.module_user.activity;

import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/24.
 */

public class PunchSuccessActivity extends TitleBarActivity {
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_position)
    TextView tv_position;

    private Unbinder unbinder ;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_punch_success;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("打卡成功");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        String time =  getIntent().getStringExtra("time");
        String position =  getIntent().getStringExtra("position");
        tv_time.setText("打卡"+time);
        tv_position.setText("打卡"+position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
