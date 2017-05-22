package com.zipingfang.jindiexuan.module_home.activity;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

/**
 * Created by Administrator on 2017/5/22.
 */

public class InformationListActivity extends TitleBarActivity {
    @Override
    protected int setContentLayout() {
        return R.layout.activity_information_list;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("资讯列表");
        showTitleBarLine(true);
    }
}
