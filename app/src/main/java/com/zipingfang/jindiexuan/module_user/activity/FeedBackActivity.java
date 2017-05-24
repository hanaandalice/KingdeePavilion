package com.zipingfang.jindiexuan.module_user.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/24.
 */

public class FeedBackActivity extends TitleBarActivity {
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_length)
    TextView tv_length;

    private Unbinder unbinder;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("意见反馈");
        setRightButton("提交", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_remark.getText().toString())) {
                    Toast.create(FeedBackActivity.this).show("请输入您的意见或建议~");
                    return;
                }
                Toast.create(FeedBackActivity.this).show("提交成功");
                save();
            }
        });
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);

    }
    @OnTextChanged(R.id.et_remark)
    void changed(){
        tv_length.setText(et_remark.getText().toString().length()+"-100字");
    }
    private void save() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null) {
            unbinder.unbind();
        }
    }
}
