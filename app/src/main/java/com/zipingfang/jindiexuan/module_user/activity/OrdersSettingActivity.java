package com.zipingfang.jindiexuan.module_user.activity;

import android.text.TextUtils;
import android.util.Log;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.utils.Const;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/22.
 */

public class OrdersSettingActivity extends TitleBarActivity {
    @BindView(R.id.switchButton)
    SwitchButton switchButton;

    private Unbinder unbinder;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_orders_setting;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("接单设置");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);

        if (TextUtils.equals("0", SharedPreferencesUtils.getString(Const.User.USER_RECEIVE))) {
            switchButton.setChecked(false);
        }else{
            switchButton.setChecked(true);
        }
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: --------->"+isChecked);
                if (isChecked) {
                    orderSetting("1");
                }else{
                    orderSetting("0");
                }
            }
        });
    }

    private static final String TAG = "OrdersSettingActivity";
    private void orderSetting(final String s) {
        showDialog();
        RequestManager.receiveCfg(SharedPreferencesUtils.getString(Const.User.TOKEN)
                , s
                , new HttpUtils.ResultCallback<ResultData>() {
                    @Override
                    public void onResponse(ResultData response) {
                        Toast.create(OrdersSettingActivity.this).show("设置成功");
                        SharedPreferencesUtils.save(Const.User.USER_RECEIVE,s);
                    }

                    @Override
                    public void onError(Call call, String e) {
                        super.onError(call, e);
                        Toast.create(OrdersSettingActivity.this).show(""+e);
                    }

                    @Override
                    public void onResult() {
                        super.onResult();
                        dismissDialog();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }
}
