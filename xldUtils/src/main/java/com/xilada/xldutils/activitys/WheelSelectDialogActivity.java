package com.xilada.xldutils.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xilada.xldutils.R;
import com.xilada.xldutils.utils.DiaLogBuider;
import com.xilada.xldutils.view.wheelview.LoopListener;
import com.xilada.xldutils.view.wheelview.LoopView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/29.
 */
public class WheelSelectDialogActivity extends DialogActivity {
    private RelativeLayout rootview;
    private RelativeLayout.LayoutParams layoutParams;
    private DiaLogBuider mSelectorSexDiaLog;
    private TextView
            //确定
            mConfirm,
    //取消
    mCancle;

    private String str="";
    private boolean isFirst =false;
    private ArrayList<String> list =new ArrayList<>();
    public static final String WHEELSELECT = "wheelview";
    public static final String WHEELLIST = "wheelList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity_sex);
        list = getIntent().getStringArrayListExtra(WHEELLIST);
        if (list!=null&&list.size()>0){
            showSelectorDiaLog(list);
        }
        mSelectorSexDiaLog.setDiaLogDismissLister(new DiaLogBuider.DismissLisenter() {
            @Override
            public void onDiaLogDismss() {
                finish();
            }
        });

    }
    public void showSelectorDiaLog(ArrayList<String> datas) {
        View wheelview = LayoutInflater.from(this).inflate(R.layout.activity_wheel_view, null);
        mSelectorSexDiaLog = new DiaLogBuider(this);
        mSelectorSexDiaLog.setContentView(wheelview)
                .setFullScreen()
                .setGrvier(Gravity.BOTTOM)
                .setAniMo(R.style.dialog_anim_bottom)
                .setShow();
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        rootview = (RelativeLayout) wheelview.findViewById(R.id.root_wheelview);
        mConfirm = (TextView) wheelview.findViewById(R.id.wheel_ok);
        mCancle = (TextView) wheelview.findViewById(R.id.wheel_cancle);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str!=null){
                    if ("".equals(str)&&list.size()>0){
                        str =list.get(0);
                    }
                    Intent intent=new Intent();
                    intent.putExtra(WHEELSELECT,str);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    finish();
                }

            }
        });
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LoopView loopView = new LoopView(this);

        //设置是否循环播放
        loopView.setNotLoop();
        //滚动监听
        loopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                for (int i = 0; i < list.size();i++) {
                    str = list.get(item);
                }
            }
        });
        //设置原始数据
        loopView.setArrayList(datas);
        //设置初始位置
        loopView.setPosition(0);
        //设置字体大小
        loopView.setTextSize(20);
        rootview.addView(loopView, layoutParams);
    }

//        public void setDatas(ArrayList<String> datas){
//                this.list =datas;
//        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (list!=null){
            list.clear();
        }
    }
}
