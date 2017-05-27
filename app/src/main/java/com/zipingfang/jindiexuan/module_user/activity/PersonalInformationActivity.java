package com.zipingfang.jindiexuan.module_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xilada.xldutils.activitys.SelectPhotoDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.Api;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_login.activity.SelectDriverActivity;
import com.zipingfang.jindiexuan.module_login.activity.SelectSexActivity;
import com.zipingfang.jindiexuan.module_login.activity.SelectedDeliveryActivity;
import com.zipingfang.jindiexuan.utils.Const;

import org.json.JSONException;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/19.
 */
public class PersonalInformationActivity extends TitleBarActivity {
    @BindView(R.id.layout_part_time_driver)
    LinearLayout layout_part_time_driver;
    @BindView(R.id.layout_selected_head_img)
    LinearLayout layout_selected_head_img;
    @BindView(R.id.layout_select_nickname)
    LinearLayout layout_select_nickname;
    @BindView(R.id.layout_select_sex)
    LinearLayout layout_select_sex;
    @BindView(R.id.layout_select_phone)
    LinearLayout layout_select_phone;
    @BindView(R.id.layout_select_area)
    LinearLayout layout_select_area;
    @BindView(R.id.iv_head_img)
    ImageView iv_head_img;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_part_time)
    TextView tv_part_time;
    @BindView(R.id.tv_full_time)
    TextView tv_full_time;
    @BindView(R.id.layout_full_time_driver)
    LinearLayout layout_full_time_driver;

    private Unbinder unbinder;
    private static  final int PICPHOTO =15;
    private static  final int SELECT_NICKNAME =16;
    private static  final int SELECT_SEX =17;
    private static  final int SELECT_PHONE =18;
    private static final int SEX_DELIVERY =19;
    private String imgPath;
    private String sex;
    private String area_id;

    private static final String TAG = "PersonalInformationActi";

    @Override
    protected int setContentLayout() {
        return R.layout.activity_personal_information;
    }
    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("个人信息");
        setRightButton("提交", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(area_id)) {
                    Toast.create(PersonalInformationActivity.this).show("请选择区域");
                    return;
                }
                if (TextUtils.equals("男",tv_sex.getText().toString())){
                    sex ="1";
                }else{
                    sex ="2";
                }
                sava();
            }
        });
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        if (TextUtils.equals("1", SharedPreferencesUtils.getString(Const.User.USER_DRIVER))) {
            tv_part_time.setText("");
            tv_full_time.setText("");
            layout_full_time_driver.setClickable(false);
        }else if (TextUtils.equals("2", SharedPreferencesUtils.getString(Const.User.USER_DRIVER))){
            if (TextUtils.equals("1", SharedPreferencesUtils.getString(Const.User.USER_DRIVER_STATUS))) {
                tv_part_time.setText("已认证");
                layout_full_time_driver.setClickable(true);
            }else{
                tv_part_time.setText("审核中...");
                layout_full_time_driver.setClickable(false);
            }
        }else if (TextUtils.equals("3", SharedPreferencesUtils.getString(Const.User.USER_DRIVER))){
            tv_part_time.setText("已认证");
            tv_full_time.setClickable(true);
            if (TextUtils.equals("1", SharedPreferencesUtils.getString(Const.User.USER_DRIVER_STATUS))) {
                tv_full_time.setText("已认证");
            }else{
                tv_full_time.setText("审核中...");
            }
        }
        Glide.with(this).load(SharedPreferencesUtils.getString(Const.User.USER_HEAD_IMG)).error(R.mipmap.icon_default_head).into(iv_head_img);
        tv_nickname.setText(SharedPreferencesUtils.getString(Const.User.NAME));
        if (TextUtils.equals("1", SharedPreferencesUtils.getString(Const.User.USER_SEX))) {
            tv_sex.setText("男");
        }else{
            tv_sex.setText("女");
        }
        tv_phone.setText(SharedPreferencesUtils.getString(Const.User.USER_PHONE));
        tv_area.setText(SharedPreferencesUtils.getString(Const.User.USER_AREA_ID));
        area_id =SharedPreferencesUtils.getString(Const.User.USER_AREA_ID);
    }

    private void sava() {
        showDialog();
        RequestManager.updateUser(SharedPreferencesUtils.getString(Const.User.TOKEN)
                , SharedPreferencesUtils.getString(Const.User.USER_PHONE)
                , tv_nickname.getText().toString()
                , sex
                , area_id
                , new HttpUtils.ResultCallback<ResultData>() {
                    @Override
                    public void onResponse(ResultData response) {
                        Log.d(TAG, "onResponse: ------>"+response.toString());

                    }
                    @Override
                    public void onError(Call call, String e) {
                        super.onError(call, e);
                        Toast.create(PersonalInformationActivity.this).show(""+e);
                    }

                    @Override
                    public void onResult() {
                        super.onResult();
                        dismissDialog();
                    }
                });
    }

    @OnClick({R.id.layout_part_time_driver
            ,R.id.layout_selected_head_img
            ,R.id.layout_select_nickname
            ,R.id.layout_select_phone
            ,R.id.layout_select_area
            ,R.id.layout_select_sex
            ,R.id.layout_full_time_driver
    })
    void onClicks(View view){
        switch (view.getId()) {
            case R.id.layout_part_time_driver:
                goActivity(PartTimeDriverStatueActivity.class);
                break;
            case R.id.layout_selected_head_img:
                goActivityForResult(SelectPhotoDialog.class,PICPHOTO);
                break;
            case R.id.layout_select_nickname:
                goActivityForResult(SelectNickNameActivity.class,SELECT_NICKNAME);
                break;
            case R.id.layout_select_sex:
//                goActivityForResult(SelectSexActivity.class,SELECT_SEX);
                goActivity(FullTimeDriverAuthenticateActivity.class);
                break;
            case R.id.layout_select_phone:
                goActivityForResult(ModifyPhoneActivity.class,SELECT_PHONE);
                break;
            case R.id.layout_select_area:
                Bundle bundle =new Bundle();
                bundle.putString("type","1");
                bundle.putString("delivery","南山区");
                goActivityForResult(SelectedDeliveryActivity.class,bundle,SEX_DELIVERY);
                break;
            case R.id.layout_full_time_driver:
                if (TextUtils.equals("3", SharedPreferencesUtils.getString(Const.User.USER_DRIVER))){
                    goActivity(FullTimeDriverAuthenticateScheduleActivity.class);
                }else{
                    goActivity(FullTimeDriverAuthenticateActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case PICPHOTO:
                    imgPath =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(imgPath)) {
                        Glide.with(PersonalInformationActivity.this).load(new File(imgPath)).into(iv_head_img);
                    }
                    break;
                case SELECT_NICKNAME:
                    String name =data.getStringExtra(SelectNickNameActivity.DATA);
                    if (!TextUtils.isEmpty(name)) {
                        tv_nickname.setText(name);
                    }
                    break;
                case SELECT_SEX:
                    sex =data.getStringExtra(SelectSexActivity.DATA);
                    if (!TextUtils.isEmpty(sex)) {
                        tv_sex.setText(sex);
                    }
                    break;
                case SELECT_PHONE:
                    String phone =data.getStringExtra(SelectNickNameActivity.DATA);
                    if (!TextUtils.isEmpty(phone)) {
                        tv_phone.setText(phone);
                    }
                    break;
                case SEX_DELIVERY:
                    String delivery =data.getStringExtra(SelectedDeliveryActivity.DATA);
                    String delivery_id =data.getStringExtra(SelectedDeliveryActivity.DATA_ID);
                    if (!TextUtils.isEmpty(delivery)) {
                        tv_area.setText("深圳-"+delivery);
                        area_id =delivery_id;
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
