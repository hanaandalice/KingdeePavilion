package com.zipingfang.jindiexuan.module_user.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xilada.xldutils.fragment.VerticalLinearRecyclerViewFragment;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.module_user.adapter.LeaderBoardStarAdapter;
import com.zipingfang.jindiexuan.module_user.model.LeaderBoardStarModel;
import com.zipingfang.jindiexuan.view.MyRecyclerDetorration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class LeaderBoardStarFragment extends VerticalLinearRecyclerViewFragment {

    private LeaderBoardStarAdapter leaderBoardStarAdapter;
    List<LeaderBoardStarModel> leaderBoardStarModelList =new ArrayList<>();
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
        for (int i = 0; i < 8; i++) {
            leaderBoardStarModelList.add(new LeaderBoardStarModel());
        }
        setItemDecoration(new MyRecyclerDetorration(getActivity(), LinearLayoutManager.VERTICAL, R.drawable.divider_line));
        leaderBoardStarAdapter =new LeaderBoardStarAdapter(leaderBoardStarModelList,getActivity());
        return leaderBoardStarAdapter;
    }

    @Override
    protected void pullDownRefresh() {

    }

    @Override
    protected void loadMore() {

    }
}
