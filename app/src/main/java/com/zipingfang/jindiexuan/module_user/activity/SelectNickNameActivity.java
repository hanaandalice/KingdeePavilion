package com.zipingfang.jindiexuan.module_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.view.XEditText;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/22.
 */

public class SelectNickNameActivity extends TitleBarActivity {
    @BindView(R.id.et_nickname)
    XEditText et_nickname;
    private Unbinder unbinder;
    public static final String DATA ="data";
    @Override
    protected int setContentLayout() {
        return R.layout.activity_select_nickname;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("修改昵称");
        setRightButton("保存", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_nickname.getText().toString())) {
                    Toast.create(SelectNickNameActivity.this).show("请输入昵称");
                    return;
                }
                Intent intent =new Intent();
                Bundle bundle =new Bundle();
                bundle.putString(DATA,et_nickname.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        showTitleBarLine(true);
        unbinder = ButterKnife .bind(this);
        et_nickname.setDrawableRightListener(new XEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                et_nickname.setText("");
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
