package com.zipingfang.jindiexuan.module_login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xilada.xldutils.activitys.DialogActivity;
import com.xilada.xldutils.utils.Toast;
import com.xilada.xldutils.view.wheelview.LoopView;
import com.xilada.xldutils.view.wheelview.OnItemSelectedListener;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.utils.ClassifyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class SelectDriverActivity extends DialogActivity implements View.OnClickListener{
    private LoopView constellation_wheelview;
    private TextView btn_ok,btn_cancle;
    public static  final String DATA ="data";
    private String driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_driver);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        initView();
    }

    private void initView() {
        constellation_wheelview =bind(R.id.constellation_wheelview);
        constellation_wheelview.setNotLoop();
        btn_ok =bind(R.id.btn_ok);
        btn_cancle =bind(R.id.btn_cancle);
        btn_ok.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
//         设置可见条目数量
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
    }

    private static final String TAG = "SelectDriverActivity";
    private void getData() {
        // 滚动监听
        constellation_wheelview.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                driver = ClassifyManager.getDriverList().get(index);
            }
        });
        // 设置原始数据
        constellation_wheelview.setItems(ClassifyManager.getDriverList());
    }

    @Override
    protected int exitAnim() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                if (TextUtils.isEmpty(driver)) {
                    driver =ClassifyManager.getDriverList().get(0);
                }
                Intent intent=new Intent();
                Bundle bundle =new Bundle();
                bundle.putString(DATA, driver);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_cancle:
                finish();
                break;
        }
    }
}
