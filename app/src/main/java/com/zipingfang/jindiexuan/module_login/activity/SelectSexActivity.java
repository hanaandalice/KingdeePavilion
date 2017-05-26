package com.zipingfang.jindiexuan.module_login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xilada.xldutils.activitys.DialogActivity;
import com.xilada.xldutils.view.wheelview.LoopView;
import com.xilada.xldutils.view.wheelview.OnItemSelectedListener;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_login.model.SexUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class SelectSexActivity extends DialogActivity implements View.OnClickListener{
    private LoopView constellation_wheelview;
    private TextView btn_ok,btn_cancle;
    private List<String> constellationList =new ArrayList<>();
    public static  final String DATA ="data";
    private String sex="男";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sex);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        constellationList.add("男");
        constellationList.add("女");
        initView();
    }
    private void initView() {
        constellation_wheelview =bind(R.id.constellation_wheelview);
        constellation_wheelview.setItemsVisibleCount(2);
        constellation_wheelview.setNotLoop();
        btn_ok =bind(R.id.btn_ok);
        btn_cancle =bind(R.id.btn_cancle);
        btn_ok.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });

    }
    private void getData() {
        // 滚动监听
        constellation_wheelview.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                sex =constellationList.get(index);
            }
        });
        // 设置原始数据
        constellation_wheelview.setItems(constellationList);
    }
    @Override
    protected int exitAnim() {
        return 0;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                Intent intent=new Intent();
                Bundle bundle =new Bundle();
                bundle.putString(DATA, sex);
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
