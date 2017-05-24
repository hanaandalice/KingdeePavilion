package com.zipingfang.jindiexuan.module_user.activity;

import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/22.
 */

public class RecommendActivity extends TitleBarActivity {
    @BindView(R.id.tv_wechat)
    TextView tv_wechat;
    @BindView(R.id.tv_wechat_friend)
    TextView tv_wechat_friend;
    @BindView(R.id.tv_qq)
    TextView tv_qq;
    @BindView(R.id.tv_qzone)
    TextView tv_qzone;

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
