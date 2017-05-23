package com.zipingfang.jindiexuan.module_home.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.xilada.xldutils.fragment.VerticalLinearRecyclerViewFragment;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.tool.Densityuitl;
import com.xilada.xldutils.tool.Srceen;
import com.xilada.xldutils.utils.Toast;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_home.adapter.HomeCommodityAdapter;
import com.zipingfang.jindiexuan.module_home.model.CateGoodsModel;
import com.zipingfang.jindiexuan.module_home.model.HomeCommodityModel;
import com.zipingfang.jindiexuan.view.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/18.
 */

public class CommodityFragment extends VerticalLinearRecyclerViewFragment{

    private static final String TAG = "CommodityFragment";
    private HomeCommodityAdapter homeCommodityAdapter;
    private String cate_id;
    private int page =1;
    @Override
    protected void onFirstVisibleToUser() {
        Bundle bundle =getArguments();
        cate_id = bundle.getString("cate_id");
        getRecyclerView().setLayoutManager(new GridLayoutManager(getActivity(),2));
        GridSpacingItemDecoration layoutDecoration = new GridSpacingItemDecoration(2, Densityuitl.dip2px(getActivity(),10),Densityuitl.dip2px(getActivity(),10),false);
        setItemDecoration(layoutDecoration);
        setVisibilityFooterView(true);
        retrieveData();
    }
    private void retrieveData() {
        if(TextUtils.isEmpty(cate_id))return;
        RequestManager.getCateGoods(cate_id, page, new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                parserCate(response.getJsonArray());
                if (page<=1){
                    if (response.getJsonArray().length()<=6) {
                        setRefreshing(false);
                    }
                }
                page++;
                refreshData();
            }
            @Override
            public void onResult() {
                super.onResult();
                setRefreshing(false);
            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                if (null!=getActivity()) {
                    Toast.create(getActivity()).show(""+e);
                }
            }
        });
    }

    private void refreshData() {
        if (null!=homeCommodityAdapter) {
            homeCommodityAdapter.notifyDataSetChanged();
        }

    }

    List<CateGoodsModel> cateGoodsModelList =new ArrayList<>();
    private void parserCate(JSONArray jsonArray) {
        cateGoodsModelList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object =jsonArray.optJSONObject(i);
            CateGoodsModel cateGoodsModel =new CateGoodsModel();
            cateGoodsModel.setGid(object.optString("gid"));
            cateGoodsModel.setCate_id(object.optString("cate_id"));
            cateGoodsModel.setGoods_name(object.optString("goods_name"));
            cateGoodsModel.setWeight(object.optString("weight"));
            cateGoodsModel.setPic(object.optString("pic"));
            cateGoodsModel.setIs_new(object.optString("is_new"));
            cateGoodsModel.setIs_hot(object.optString("is_hot"));
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);
            cateGoodsModelList.add(cateGoodsModel);

        }
    }
    @Override
    protected void onVisibleToUser() {

    }
    @Override
    protected void onInvisibleToUser() {

    }
    @Override
    protected RecyclerView.Adapter setAdapter() {
        int height = Srceen.getScreen(getActivity())[0];
        homeCommodityAdapter = new HomeCommodityAdapter(cateGoodsModelList,getActivity(),height);
        return homeCommodityAdapter;
    }

    @Override
    protected void pullDownRefresh() {
        page=1;
        retrieveData();
    }

    @Override
    protected void loadMore() {

    }

}
