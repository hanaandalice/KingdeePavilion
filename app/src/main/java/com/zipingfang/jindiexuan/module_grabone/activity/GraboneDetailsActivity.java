package com.zipingfang.jindiexuan.module_grabone.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.widget.NormalDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_grabone.adapter.GraboneDetailsCommodityAdapter;
import com.zipingfang.jindiexuan.module_grabone.model.GraboneDetailsCommodityModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/27.
 */

public class GraboneDetailsActivity extends TitleBarActivity {
    @BindView(R.id.recyclerView_orders)
    RecyclerView recyclerView_orders;
    @BindView(R.id.tv_punch)
    TextView tv_punch;
    @BindView(R.id.tv_status)
    TextView tv_status;

    private Unbinder unbinder;
    private String type;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_grabone_details;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("详情");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView_orders.setLayoutManager(layoutManager);
        List<GraboneDetailsCommodityModel> graboneDetailsCommodityModelList =new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            graboneDetailsCommodityModelList.add(new GraboneDetailsCommodityModel());
        }

        GraboneDetailsCommodityAdapter graboneDetailsCommodityAdapter =new GraboneDetailsCommodityAdapter(graboneDetailsCommodityModelList,this);
        recyclerView_orders.setAdapter(graboneDetailsCommodityAdapter);

        type = getIntent().getStringExtra("type");
        tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(NavigationActivity.class);
            }
        });
        switch (type) {
            case "0":
                tv_status.setText("待抢单");
                tv_status.setTextColor(getResources().getColor(R.color.greenColor_4e));
                tv_punch.setText("抢单");
                tv_punch.setTextColor(getResources().getColor(R.color.textAccent));
                tv_punch.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_white_bg_line_accent_7e_1dp));
                break;
            case "1":
                tv_status.setText("待取货");
                tv_status.setTextColor(getResources().getColor(R.color.greenColor_4e));
                tv_punch.setText("取货");
                tv_punch.setTextColor(getResources().getColor(R.color.textAccent));
                tv_punch.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_white_bg_line_accent_7e_1dp));
                break;
            case "2":
                tv_status.setText("待送达");
                tv_status.setTextColor(getResources().getColor(R.color.textAccent));
                tv_punch.setText("送达");
                tv_punch.setTextColor(getResources().getColor(R.color.textAccent));
                tv_punch.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_white_bg_line_accent_7e_1dp));
                break;
            case "3":
                tv_status.setText("待送达");
                tv_status.setTextColor(getResources().getColor(R.color.textAccent));
                tv_punch.setText("送达");
                tv_punch.setTextColor(getResources().getColor(R.color.white));
                tv_punch.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_hint_e0_bg));
                break;
            case "4":
                tv_status.setText("已送达");
                tv_status.setTextColor(getResources().getColor(R.color.textAccent));
                tv_punch.setVisibility(View.GONE);
                break;
            case "5":
                tv_status.setText("已送达");
                tv_status.setTextColor(getResources().getColor(R.color.textAccent));
                tv_punch.setVisibility(View.GONE);
                 break;
            case "6":
                tv_status.setText("已失效");
                tv_status.setTextColor(getResources().getColor(R.color.hintColor_e0));
                tv_punch.setVisibility(View.GONE);
                break;
            case "7":
                tv_status.setText("已取消");
                tv_status.setTextColor(getResources().getColor(R.color.hintColor_e0));
                tv_punch.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick({R.id.tv_punch})
    void onClicks(View view){
        switch (view.getId()) {
            case R.id.tv_punch:
                switch (type) {
                    case "0":
                        Toast.create(GraboneDetailsActivity.this).show("抢单成功");
                        tv_punch.setTextColor(getResources().getColor(R.color.white));
                        tv_punch.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_hint_e0_bg));
                        tv_punch.setClickable(false);
                        break;
                    case "3":
                        Toast.create(GraboneDetailsActivity.this).show("送达成功");
                        tv_punch.setTextColor(getResources().getColor(R.color.white));
                        tv_punch.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_25dp_hint_e0_bg));
                        tv_punch.setClickable(false);
                        break;
                    case "4":
                        final NormalDialog dialog2 = new NormalDialog(this);
                        dialog2.content("你尚未到达送货地址，请前往\n收货地址再点击送达。")
                                .contentTextSize(14)
                                .cornerRadius(15)
                                .contentTextColor(getResources().getColor(R.color.black))
                                .style(NormalDialog.STYLE_TWO)
                                .titleTextColor(getResources().getColor(R.color.black))
                                .titleTextSize(18)
                                .title("送货失败")
                                .btnNum(1)
                                .btnTextSize(16)
                                .btnTextColor(getResources().getColor(R.color.textAccent))
                                .btnText("好的")
                                .showAnim(null)
                                .dismissAnim(null)
                                .show();
                        break;
                }

                break;
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
