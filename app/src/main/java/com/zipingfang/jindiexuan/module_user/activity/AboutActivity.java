package com.zipingfang.jindiexuan.module_user.activity;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

/**
 * Created by Administrator on 2017/5/24.
 */

public class AboutActivity extends TitleBarActivity {
    @Override
    protected int setContentLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("关于我们");
        showTitleBarLine(true);

    }
}
