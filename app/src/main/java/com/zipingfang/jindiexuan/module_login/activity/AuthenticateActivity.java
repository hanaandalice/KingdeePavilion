package com.zipingfang.jindiexuan.module_login.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.xilada.xldutils.activitys.SelectPhotoDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.MainActivity;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.view.XEditText;

import org.json.JSONException;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/19.
 */

public class AuthenticateActivity extends TitleBarActivity {

    @BindView(R.id.et_name)
    XEditText et_name;
    @BindView(R.id.et_id_card_number)
    XEditText et_id_card_number;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_driver)
    TextView tv_driver;
    @BindView(R.id.iv_card_positive)
    ImageView iv_card_positive;
    @BindView(R.id.iv_card_negative)
    ImageView iv_card_negative;
    @BindView(R.id.iv_driver_license_positive)
    ImageView iv_driver_license_positive;
    @BindView(R.id.iv_driver_license_negative)
    ImageView iv_driver_license_negative;
    @BindView(R.id.layout_select_driver)
    LinearLayout layout_select_driver;

    private Unbinder unbinder;
    private static final int CARD_POSITIVE_PICPHOTO = 16;
    private static final int CARD_NEGATIVE_PICPHOTO = 17;
    private static final int LICENSE_POSITIVE_PICPHOTO = 18;
    private static final int LICENSE_NEGATIVE_PICPHOTO = 19;
    private static final int SELECT_DELIVERY_CODE=20;
    private String card_positive_img,
            card_negative_img,
            license_positive_img,
            license_negative_img;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_login_authenticate;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("兼职司机认证");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);

        RxView.clicks(tv_next)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (TextUtils.isEmpty(et_name.getText().toString())) {
                            Toast.create(AuthenticateActivity.this).show("请输入姓名");
                            return;
                        }
                        if (TextUtils.isEmpty(et_id_card_number.getText().toString())) {
                            Toast.create(AuthenticateActivity.this).show("请输入身份证号码");
                            return;
                        }
                        if (et_id_card_number.getText().toString().length()!=15&& et_id_card_number.getText().toString().length()!=18) {
                            Toast.create(AuthenticateActivity.this).show("请输入15或18位身份证号码");
                            return;
                        }
                        if (TextUtils.isEmpty(tv_driver.getText().toString())) {
                            Toast.create(AuthenticateActivity.this).show("请选择司机种类");
                            return;
                        }
                        if (TextUtils.isEmpty(card_positive_img)) {
                            Toast.create(AuthenticateActivity.this).show("请选择身份证正面");
                            return;
                        }
                        if (TextUtils.isEmpty(card_negative_img)) {
                            Toast.create(AuthenticateActivity.this).show("请选择身份证反面");
                            return;
                        }
                        if (TextUtils.isEmpty(license_positive_img)) {
                            Toast.create(AuthenticateActivity.this).show("请选择驾驶证正面");
                            return;
                        }
                        if (TextUtils.isEmpty(license_negative_img)) {
                            Toast.create(AuthenticateActivity.this).show("请选择驾驶证反面");
                            return;
                        }
                        goActivity(MainActivity.class);
                        finish();
                    }
                });
    }
    @OnClick({R.id.iv_card_positive
            ,R.id.iv_card_negative
            ,R.id.iv_driver_license_positive
            ,R.id.iv_driver_license_negative
            ,R.id.layout_select_driver})
    void onClicks(View view){
        switch (view.getId()) {
            case R.id.iv_card_positive:
                goActivityForResult(SelectPhotoDialog.class,CARD_POSITIVE_PICPHOTO);
                break;
            case R.id.iv_card_negative:
                goActivityForResult(SelectPhotoDialog.class,CARD_NEGATIVE_PICPHOTO);
                break;
            case R.id.iv_driver_license_positive:
                goActivityForResult(SelectPhotoDialog.class,LICENSE_POSITIVE_PICPHOTO);
                break;
            case R.id.iv_driver_license_negative:
                goActivityForResult(SelectPhotoDialog.class,LICENSE_NEGATIVE_PICPHOTO);
                break;
            case R.id.layout_select_driver:
                goActivityForResult(SelectDriverActivity.class,SELECT_DELIVERY_CODE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case CARD_POSITIVE_PICPHOTO:
                    card_positive_img =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(card_positive_img)) {
                        Glide.with(AuthenticateActivity.this).load(new File(card_positive_img)).into(iv_card_positive);
                    }
                    break;
                case CARD_NEGATIVE_PICPHOTO:
                    card_negative_img =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(card_negative_img)) {
                        Glide.with(AuthenticateActivity.this).load(new File(card_negative_img)).into(iv_card_negative);
                    }
                    break;
                case LICENSE_POSITIVE_PICPHOTO:
                    license_positive_img =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(license_positive_img)) {
                        Glide.with(AuthenticateActivity.this).load(new File(license_positive_img)).into(iv_driver_license_positive);
                    }
                    break;
                case LICENSE_NEGATIVE_PICPHOTO:
                    license_negative_img =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(license_negative_img)) {
                        Glide.with(AuthenticateActivity.this).load(new File(license_negative_img)).into(iv_driver_license_negative);
                    }
                    break;
                case SELECT_DELIVERY_CODE:
                    String driverStr =  data.getStringExtra(SelectDriverActivity.DATA);
                    if (!TextUtils.isEmpty(driverStr)) {
                        tv_driver.setText(driverStr);
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
