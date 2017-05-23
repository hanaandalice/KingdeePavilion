package com.zipingfang.jindiexuan.module_user.activity;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/22.
 */

public class RecommendActivity extends TitleBarActivity {

    private Unbinder unbinder;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("推荐有奖");
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
