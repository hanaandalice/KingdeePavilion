package com.zipingfang.jindiexuan.module_login.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.view.RoundAngleImageView;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/18.
 */

public class RegisterPersonalInformationActivity extends TitleBarActivity {
    @BindView(R.id.iv_head_img)
    RoundAngleImageView iv_head_img;
    @BindView(R.id.layout_selected_head_img)
    LinearLayout layout_selected_head_img;
    @BindView(R.id.layout_selected_sex)
    LinearLayout layout_selected_sex;
    @BindView(R.id.layout_selected_delivery_area)
    LinearLayout layout_selected_delivery_area;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_delivery_area)
    TextView tv_delivery_area;
    @BindView(R.id.tv_next)
    TextView tv_next;


    private Unbinder unbinder;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_register_personal_information;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("填写个人信息");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);

    }
    @OnClick({R.id.layout_selected_head_img
            ,R.id.layout_selected_sex
            ,R.id.layout_selected_delivery_area
            ,R.id.tv_next})
    void onclick(View view){
        switch (view.getId()) {
            case R.id.layout_selected_head_img:

                break;
            case R.id.layout_selected_sex:

                break;
            case R.id.layout_selected_delivery_area:

                break;
            case R.id.tv_next:

                break;
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
