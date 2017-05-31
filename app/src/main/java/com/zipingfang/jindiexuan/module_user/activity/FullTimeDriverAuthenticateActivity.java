package com.zipingfang.jindiexuan.module_user.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.jakewharton.rxbinding.view.RxView;
import com.xilada.xldutils.activitys.SelectPhotoDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.utils.Base64;
import com.xilada.xldutils.utils.BitmapUtils;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.xilada.xldutils.utils.Toast;
import com.xilada.xldutils.utils.luban.LubanGetFileProgressListener;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_login.activity.SelectDriverActivity;
import com.zipingfang.jindiexuan.utils.ClassifyManager;
import com.zipingfang.jindiexuan.utils.Const;
import com.zipingfang.jindiexuan.view.XEditText;

import org.json.JSONException;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/23.
 */

public class FullTimeDriverAuthenticateActivity extends TitleBarActivity {

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
            license_negative_img,
            driverStr,
            driverIndex;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_login_authenticate;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("专职司机认证");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);

        RxView.clicks(tv_next)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (TextUtils.isEmpty(et_name.getText().toString())) {
                            Toast.create(FullTimeDriverAuthenticateActivity.this).show("请输入姓名");
                            return;
                        }
                        if (TextUtils.isEmpty(et_id_card_number.getText().toString())) {
                            Toast.create(FullTimeDriverAuthenticateActivity.this).show("请输入身份证号码");
                            return;
                        }
                        if (et_id_card_number.getText().toString().length()!=15&& et_id_card_number.getText().toString().length()!=18) {
                            Toast.create(FullTimeDriverAuthenticateActivity.this).show("请输入15或18位身份证号码");
                            return;
                        }
                        if (TextUtils.isEmpty(tv_driver.getText().toString())) {
                            Toast.create(FullTimeDriverAuthenticateActivity.this).show("请选择司机种类");
                            return;
                        }
                        if (TextUtils.isEmpty(card_positive_img)) {
                            Toast.create(FullTimeDriverAuthenticateActivity.this).show("请选择身份证正面");
                            return;
                        }
                        if (TextUtils.isEmpty(card_negative_img)) {
                            Toast.create(FullTimeDriverAuthenticateActivity.this).show("请选择身份证反面");
                            return;
                        }
                        if (TextUtils.isEmpty(license_positive_img)) {
                            Toast.create(FullTimeDriverAuthenticateActivity.this).show("请选择驾驶证正面");
                            return;
                        }
                        if (TextUtils.isEmpty(license_negative_img)) {
                            Toast.create(FullTimeDriverAuthenticateActivity.this).show("请选择驾驶证反面");
                            return;
                        }
                        fullTimeAuthenticate();
                    }
                });
    }

    private static final String TAG = "FullTimeDriverAuthentic";
//    private File[] files =new File[4];
    private String[] imgs =new String[4];
    private void fullTimeAuthenticate() {
        showDialog();
        RequestManager.beDriver(SharedPreferencesUtils.getString(Const.User.TOKEN)
                , et_name.getText().toString()
                , et_id_card_number.getText().toString()
                , "2"
                , driverIndex
                , imgs
                , new HttpUtils.ResultCallback<ResultData>() {
                    @Override
                    public void onResponse(ResultData response) {
                        final NormalDialog dialog2 = new NormalDialog(FullTimeDriverAuthenticateActivity.this);
                        dialog2 .style(NormalDialog.STYLE_TWO)
                                .title("提交成功")
                                .titleTextColor(getResources().getColor(R.color.black))
                                .titleTextSize(18)
                                .content("你的申请已经提交成功,\n去看看审核进度吧!")
                                .contentTextSize(14)
                                .cornerRadius(15)
                                .contentTextColor(getResources().getColor(R.color.black))
                                .btnNum(2)
                                .btnTextSize(16)
                                .btnTextColor(getResources().getColor(R.color.hintColor_66),getResources().getColor(R.color.textAccent))
                                .btnText("取消","去查看")
                                .showAnim(null)
                                .dismissAnim(null)
                                .show();
                        dialog2.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog2.dismiss();
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog2.dismiss();
                                        goActivity(FullTimeDriverAuthenticateScheduleActivity.class);
                                        finish();
                                    }
                                });
                    }
                    @Override
                    public void onError(Call call, String e) {
                        super.onError(call, e);
                        Toast.create(FullTimeDriverAuthenticateActivity.this).show(""+e);
                    }

                    @Override
                    public void onResult() {
                        super.onResult();
                        dismissDialog();
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
                        Glide.with(FullTimeDriverAuthenticateActivity.this).load(new File(card_positive_img)).into(iv_card_positive);
                        compressFile(card_positive_img,0);

                    }
                    break;
                case CARD_NEGATIVE_PICPHOTO:
                    card_negative_img =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(card_negative_img)) {
                        Glide.with(FullTimeDriverAuthenticateActivity.this).load(new File(card_negative_img)).into(iv_card_negative);
                        compressFile(card_positive_img,1);
                    }
                    break;
                case LICENSE_POSITIVE_PICPHOTO:
                    license_positive_img =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(license_positive_img)) {
                        Glide.with(FullTimeDriverAuthenticateActivity.this).load(new File(license_positive_img)).into(iv_driver_license_positive);
                        compressFile(card_positive_img,2);
                    }
                    break;
                case LICENSE_NEGATIVE_PICPHOTO:
                    license_negative_img =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(license_negative_img)) {
                        Glide.with(FullTimeDriverAuthenticateActivity.this).load(new File(license_negative_img)).into(iv_driver_license_negative);
                        compressFile(card_positive_img,3);
                    }
                    break;
                case SELECT_DELIVERY_CODE:
                    driverStr=  data.getStringExtra(SelectDriverActivity.DATA);
                    if (!TextUtils.isEmpty(driverStr)) {
                        tv_driver.setText(driverStr);
                        if (TextUtils.equals(  ClassifyManager.getDriverList().get(0),driverStr)) {
                            driverIndex ="1";
                        }else{
                            driverIndex ="2";
                        }
                    }
                    break;
            }
        }
    }

    private void compressFile(String card_positive_img,final int i) {
        BitmapUtils.compressWithRx(this, new File(card_positive_img), new LubanGetFileProgressListener() {
            @Override
            public void onStart() {

            }
            @Override
            public void onError(Throwable throwable) {

            }
            @Override
            public void onSuccess(File file) {
                imgs[i] = Base64.encodeToString(file, Base64.DEFAULT);
                Log.d(TAG, "onSuccess: --------->"+ imgs[i] );
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
