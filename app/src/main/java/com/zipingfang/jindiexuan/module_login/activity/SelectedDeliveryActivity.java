package com.zipingfang.jindiexuan.module_login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.flyco.dialog.widget.NormalDialog;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.adapter.BaseRecyclerAdapter;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.tool.Densityuitl;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.MainActivity;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_login.adapter.DeliveryAdapter;
import com.zipingfang.jindiexuan.module_login.model.SelectDeliveryModel;
import com.zipingfang.jindiexuan.view.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/19.
 */

public class SelectedDeliveryActivity extends TitleBarActivity {
    @BindView(R.id.recyclerView_delivery)
    RecyclerView recyclerView_delivery;
    private DeliveryAdapter deliveryAdapter;
    private Unbinder unbinder;
    private  List<SelectDeliveryModel> stringList=new ArrayList<>();
    public static  final String DATA_ID ="data_id";
    public static  final String DATA ="data";
    private String type;
    private String delivery;
    private int selectPosition=-1;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_selected_delivery;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("选择区域");
        showTitleBarLine(true);
        setRightButton("确定", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deliveryAdapter.getSelectPosition()>=0) {
                    Intent intent=new Intent();
                    Bundle bundle =new Bundle();
                    bundle.putString(DATA, stringList.get(deliveryAdapter.getSelectPosition()).getName());
                    bundle.putString(DATA_ID, stringList.get(deliveryAdapter.getSelectPosition()).getArea_id());
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.create(SelectedDeliveryActivity.this).show("请选择区域");
                }
            }
        });
        type =getIntent().getStringExtra("type");
        delivery = getIntent().getStringExtra("delivery");
        if (!TextUtils.isEmpty(type)) {
            final NormalDialog dialog2 = new NormalDialog(this);
            dialog2.content("修改区域之后需要后台审核，\n审核通过之后根据原区域抢单。")
                    .contentTextSize(14)
                    .cornerRadius(15)
                    .contentTextColor(getResources().getColor(R.color.black))
                    .style(NormalDialog.STYLE_TWO)
                    .titleTextColor(getResources().getColor(R.color.black))
                    .titleTextSize(18)
                    .btnNum(1)
                    .btnTextSize(16)
                    .btnTextColor(getResources().getColor(R.color.textAccent))
                    .btnText("知道啦")
                    .showAnim(null)
                    .dismissAnim(null)
                    .show();
        }
        unbinder = ButterKnife.bind(this);
        recyclerView_delivery.setLayoutManager(new GridLayoutManager(this,4));

        deliveryAdapter =new DeliveryAdapter(stringList,this);
        recyclerView_delivery.setAdapter(deliveryAdapter);
        recyclerView_delivery.addItemDecoration(new GridSpacingItemDecoration(4, Densityuitl.dip2px(this,16),Densityuitl.dip2px(this,10),false));
        deliveryAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                deliveryAdapter.setSelectPosition(position);
            }
        });
        for (int i = 0; i < stringList.size(); i++) {
            if (TextUtils.equals(stringList.get(i).getName(),delivery)) {
                selectPosition =i;
                deliveryAdapter.setSelectPosition(selectPosition);
            }
        }
        getArea();
    }

    private static final String TAG = "SelectedDeliveryActivit";
    private void getArea() {
        RequestManager.getArea("", new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                stringList.clear();
                JSONArray array =response.getJsonArray();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object =array.optJSONObject(i);
                    SelectDeliveryModel selectDeliveryModel =new SelectDeliveryModel();
                    selectDeliveryModel.setArea_id(object.optString("area_id"));
                    selectDeliveryModel.setName(object.optString("name"));
                    selectDeliveryModel.setPid(object.optString("pid"));
                    stringList.add(selectDeliveryModel);
                }
                refreshData();
            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                Toast.create(SelectedDeliveryActivity.this).show(""+e);
            }

            @Override
            public void onResult() {
                super.onResult();
            }
        });
    }

    private void refreshData() {
        if (null!=deliveryAdapter) {
            deliveryAdapter.notifyDataSetChanged();
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
