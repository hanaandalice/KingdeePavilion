package com.zipingfang.jindiexuan.module_login.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.xilada.xldutils.activitys.SelectPhotoDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.utils.Toast;
import com.xilada.xldutils.view.RoundAngleImageView;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.functions.Action1;

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
    private static final int PICPHOTO = 16;
    private static final int SEX_DATA = 17;
    private static final int SEX_DELIVERY = 18;
    private String imagePath = "";
    private String sex="";
    private String delivery;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_register_personal_information;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("填写个人信息");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        RxView.clicks(tv_next)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (TextUtils.isEmpty(et_nickname.getText().toString())) {
                            Toast.create(RegisterPersonalInformationActivity.this).show("请输入昵称");
                            return;
                        }
                        if (TextUtils.isEmpty(tv_sex.getText().toString())) {
                            Toast.create(RegisterPersonalInformationActivity.this).show("请选择性别");
                            return;
                        }
                        if (TextUtils.isEmpty(tv_delivery_area.getText().toString())) {
                            Toast.create(RegisterPersonalInformationActivity.this).show("请选择配送区域");
                            return;
                        }
                        goActivity(AuthenticateActivity.class);
                    }
                });
    }
    @OnClick({R.id.layout_selected_head_img
            ,R.id.layout_selected_sex
            ,R.id.layout_selected_delivery_area
            })
    void onclick(View view){
        switch (view.getId()) {
            case R.id.layout_selected_head_img:
                goActivityForResult(SelectPhotoDialog.class,PICPHOTO);
                break;
            case R.id.layout_selected_sex:
                goActivityForResult(SelectSexActivity.class,SEX_DATA);
                break;
            case R.id.layout_selected_delivery_area:
                goActivityForResult(SelectedDeliveryActivity.class,SEX_DELIVERY);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case PICPHOTO:
                    imagePath =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(imagePath)) {
                        Glide.with(RegisterPersonalInformationActivity.this).load(new File(imagePath)).into(iv_head_img);
                    }
                    break;
                case SEX_DATA:
                    sex =data.getStringExtra(SelectSexActivity.DATA);
                    if (!TextUtils.isEmpty(sex)) {
                        tv_sex.setText(sex);
                    }
                    break;
                case SEX_DELIVERY:
                    delivery =data.getStringExtra(SelectedDeliveryActivity.DATA);
                    if (!TextUtils.isEmpty(delivery)) {
                        tv_delivery_area.setText(delivery);
                    }
                    break;
            }
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
