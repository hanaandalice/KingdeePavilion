package com.zipingfang.jindiexuan.module_user.activity;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

/**
 * Created by Administrator on 2017/5/23.
 */

public class FullTimeDriverAuthenticateActivity extends TitleBarActivity {
    @Override
    protected int setContentLayout() {
        return R.layout.activity_full_time_driver_authenticate;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
            setTitle("");
    }
}
