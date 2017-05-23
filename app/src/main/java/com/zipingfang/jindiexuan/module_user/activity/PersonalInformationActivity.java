package com.zipingfang.jindiexuan.module_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xilada.xldutils.activitys.SelectPhotoDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_login.activity.SelectDriverActivity;
import com.zipingfang.jindiexuan.module_login.activity.SelectSexActivity;
import com.zipingfang.jindiexuan.module_login.activity.SelectedDeliveryActivity;

import org.json.JSONException;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    @Override
    protected int setContentLayout() {
        return R.layout.activity_personal_information;
    }
    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("个人信息");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);

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
                goActivityForResult(SelectSexActivity.class,SELECT_SEX);
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
                goActivity(FullTimeDriverAuthenticateActivity.class);
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
                    if (!TextUtils.isEmpty(delivery)) {
                        tv_area.setText("深圳-"+delivery);
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
