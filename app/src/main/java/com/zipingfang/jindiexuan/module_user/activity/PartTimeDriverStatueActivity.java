package com.zipingfang.jindiexuan.module_user.activity;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/19.
 */

public class PartTimeDriverStatueActivity extends TitleBarActivity {


    private Unbinder unbinder;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_part_time_driver_statue;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("兼职司机认证");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
