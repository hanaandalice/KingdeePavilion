package com.zipingfang.jindiexuan.module_login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xilada.xldutils.activitys.DialogActivity;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_login.adapter.SexWheelAdapter;
import com.zipingfang.jindiexuan.module_login.model.SexUtils;
import com.zipingfang.jindiexuan.view.wheelview.MyOnWheelChangedListener;
import com.zipingfang.jindiexuan.view.wheelview.MyWheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class SelectSexActivity extends DialogActivity implements View.OnClickListener,MyOnWheelChangedListener {
    private MyWheelView constellation_wheelview;
    private TextView btn_ok,btn_cancle;
    private List<SexUtils> constellationList =new ArrayList<>();
    public static  final String DATA ="data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sex);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        constellationList.add(new SexUtils("男"));
        constellationList.add(new SexUtils("女"));
        initView();
    }
    private void initView() {
        constellation_wheelview =bind(R.id.constellation_wheelview);
        btn_ok =bind(R.id.btn_ok);
        btn_cancle =bind(R.id.btn_cancle);
        constellation_wheelview.addChangingListener(this);
        btn_ok.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
//         设置可见条目数量
        constellation_wheelview.setVisibleItems(5);
        constellation_wheelview.setShadowColor(0xefFFFFFF,
                0x72FFFFFF, 0x00FFFFFF);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
    }
    private void getData() {
        constellation_wheelview.setViewAdapter(new SexWheelAdapter(this, constellationList));

    }
    @Override
    public void onChanged(MyWheelView wheel, int oldValue, int newValue) {

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
                bundle.putString(DATA, constellationList.get(constellation_wheelview.getCurrentItem()).getData());
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
