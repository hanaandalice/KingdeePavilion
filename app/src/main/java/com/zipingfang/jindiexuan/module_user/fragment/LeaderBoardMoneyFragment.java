package com.zipingfang.jindiexuan.module_user.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.xilada.xldutils.fragment.VerticalLinearRecyclerViewFragment;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_user.adapter.LeaderBoardMoneyAdapter;
import com.zipingfang.jindiexuan.module_user.model.LeaderBoardMoneyModel;
import com.zipingfang.jindiexuan.view.MyRecyclerDetorration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class LeaderBoardMoneyFragment extends VerticalLinearRecyclerViewFragment {

    private LeaderBoardMoneyAdapter leaderBoardMoneyAdapter;
    private List<LeaderBoardMoneyModel> leaderBoardMoneyModelList =new ArrayList<>();
    private static final String TAG = "LeaderBoardMoneyFragmen";
    @Override
    protected void onFirstVisibleToUser() {

    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

    }

    @Override
    protected RecyclerView.Adapter setAdapter() {
        Bundle bundle = getArguments();
        String type = bundle.getString("type");

        for (int i = 0; i < 8; i++) {
            leaderBoardMoneyModelList.add(new LeaderBoardMoneyModel());
        }
        setItemDecoration(new MyRecyclerDetorration(getActivity(), LinearLayoutManager.VERTICAL, R.drawable.divider_line));
        leaderBoardMoneyAdapter =new LeaderBoardMoneyAdapter(leaderBoardMoneyModelList,getActivity(),type);
        return leaderBoardMoneyAdapter;
    }

    @Override
    protected void pullDownRefresh() {

    }

    @Override
    protected void loadMore() {

    }
}
