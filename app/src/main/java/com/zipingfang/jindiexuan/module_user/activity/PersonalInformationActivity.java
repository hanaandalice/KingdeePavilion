package com.zipingfang.jindiexuan.module_user.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.xilada.xldutils.activitys.SelectPhotoDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.zipingfang.jindiexuan.R;

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
    @BindView(R.id.layout_select_phont)
    LinearLayout layout_select_phont;
    @BindView(R.id.iv_head_img)
    ImageView iv_head_img;

    private Unbinder unbinder;
    private static  final int PICPHOTO =15;
    private static  final int SELECT_NICKNAME =16;
    private String imgPath;
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
            ,R.id.layout_select_phont
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

                break;
            case R.id.layout_select_phont:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==PICPHOTO) {
            switch (requestCode) {
                case PICPHOTO:
                    imgPath =data.getStringExtra(SelectPhotoDialog.DATA);
                    if (!TextUtils.isEmpty(imgPath)) {
                        Glide.with(PersonalInformationActivity.this).load(new File(imgPath)).into(iv_head_img);
                    }
                    break;
                case SELECT_NICKNAME:

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
