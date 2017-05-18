package com.xilada.xldutils.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xilada.xldutils.R;
import com.xilada.xldutils.activitys.DialogActivity;
import com.xilada.xldutils.utils.DiaLogBuider;

/**
 * Created by Administrator on 2016/4/27.
 */
public class SelectSexView extends DialogActivity implements View.OnClickListener {
    private DiaLogBuider mSelectorSexDiaLog;
    private TextView
            mSexCancle,
            mSexOk,
            mSexMan,
            mSexWoman;
    private String sexstr="男";
    public static final String SEXSTR = "sex";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity_sex);
        showSelectorSexDiaLog("0");
        mSelectorSexDiaLog.setDiaLogDismissLister(new DiaLogBuider.DismissLisenter() {
            @Override
            public void onDiaLogDismss() {
                finish();
            }
        });
    }
    public void showSelectorSexDiaLog(String sexStr) {
        View sexLayout = LayoutInflater.from(this).inflate(R.layout.layout_selector_sex, null);
        mSelectorSexDiaLog = new DiaLogBuider(this);
        mSelectorSexDiaLog.setContentView(sexLayout)
                .setFullScreen()
                .setGrvier(Gravity.BOTTOM)
                .setAniMo(R.style.dialog_anim_bottom)
                .setShow();

        mSexCancle = (TextView) sexLayout.findViewById(R.id.sex_cancle);
        mSexOk = (TextView) sexLayout.findViewById(R.id.sex_ok);
        mSexMan = (TextView) sexLayout.findViewById(R.id.sex_man);
        mSexWoman = (TextView) sexLayout.findViewById(R.id.sex_woman);
        if ("0".equals(sexStr)){
            mSexMan.setTextColor(getResources().getColor(R.color.colorAccent));
        }else{
            mSexWoman.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        mSexCancle.setOnClickListener(this);
        mSexOk.setOnClickListener(this);
        mSexMan.setOnClickListener(this);
        mSexWoman.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       if (v==mSexCancle){
            finish();
       }else if(v==mSexOk){
           backActivity();
       }else if(v==mSexMan){
           sexstr ="男";
           mSexWoman.setTextColor(getResources().getColor(R.color.black));
           mSexMan.setTextColor(getResources().getColor(R.color.colorAccent));
       }else if(v==mSexWoman){
           sexstr ="女";
           mSexWoman.setTextColor(getResources().getColor(R.color.colorAccent));
           mSexMan.setTextColor(getResources().getColor(R.color.black));
       }
    }
//    public String getSelectSex(){
//       return this.sexstr;
//    }
//    public void setSelectSex(String sexstr){
//        this.sexstr =sexstr;
//    }

   private void backActivity(){
        Intent intent=new Intent();
        intent.putExtra(SEXSTR,sexstr);
        setResult(RESULT_OK,intent);
        finish();
    }
}
