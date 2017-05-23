package com.zipingfang.jindiexuan.module_user.activity;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

/**
 * Created by Administrator on 2017/5/22.
 */

public class PunchActivity extends TitleBarActivity {
    @Override
    protected int setContentLayout() {
        return R.layout.activity_punch;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("打卡");
        showTitleBarLine(true);
    }
}
