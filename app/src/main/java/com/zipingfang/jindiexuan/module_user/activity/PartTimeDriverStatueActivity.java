package com.zipingfang.jindiexuan.module_user.activity;

import android.text.TextUtils;
import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.utils.Const;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/19.
 */

public class PartTimeDriverStatueActivity extends TitleBarActivity {

    @BindView(R.id.tv_check_status)
    TextView tv_check_status;

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

        if (TextUtils.equals("1", SharedPreferencesUtils.getString(Const.User.USER_DRIVER_STATUS))) {
            tv_check_status.setText("已通过");
            tv_check_status.setTextColor(getResources().getColor(R.color.textAccent));
        }else{
            tv_check_status.setText("审核中...");
            tv_check_status.setTextColor(getResources().getColor(R.color.greenColor_4c));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
