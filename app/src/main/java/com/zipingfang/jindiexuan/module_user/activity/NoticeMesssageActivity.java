package com.zipingfang.jindiexuan.module_user.activity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.xilada.xldutils.utils.Toast;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_user.adapter.NoticeMessageAdapter;
import com.zipingfang.jindiexuan.module_user.model.NoticeMessageModel;
import com.zipingfang.jindiexuan.utils.Const;
import com.zipingfang.jindiexuan.view.MyRecyclerDetorration;
import com.zipingfang.jindiexuan.view.listener.OnItemClickListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/22.
 */

public class NoticeMesssageActivity extends TitleBarActivity {
    private int page=1;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recycler_view;
    private Unbinder unbinder;
    private NoticeMessageAdapter noticeMessageAdapter;
    private List<NoticeMessageModel> noticeMessageModelList =new ArrayList<>();
    @Override
    protected int setContentLayout() {
        return R.layout.activity_notice_message;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("通知");
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        for (int i = 0; i < 2; i++) {
            noticeMessageModelList.add(new NoticeMessageModel());
        }
        recycler_view.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        recycler_view.addItemDecoration(new MyRecyclerDetorration(this, LinearLayoutManager.VERTICAL, R.drawable.divider_line));// 添加分割线。
        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        recycler_view.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        recycler_view.setSwipeMenuItemClickListener(menuItemClickListener);
        noticeMessageAdapter =new NoticeMessageAdapter(noticeMessageModelList);
        noticeMessageAdapter.setOnItemClickListener(onItemClickListener);
        recycler_view.setAdapter(noticeMessageAdapter);
        retrieveData();

    }

    private static final String TAG = "NoticeMesssageActivity";
    private void retrieveData() {
        showDialog();
        RequestManager.getNotification(SharedPreferencesUtils.getString(Const.User.TOKEN)
                ,page+""
                ,new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                Log.d(TAG, "onResponse: -------->"+response.toString());

            }

            @Override
            public void onResult() {
                super.onResult();
                dismissDialog();
            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
                Toast.create(NoticeMesssageActivity.this).show(""+e);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=unbinder) {
            unbinder.unbind();
        }
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_60);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {

            }
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.color.red_ff)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

            }
        }
    };
    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                noticeMessageModelList.remove(adapterPosition);
                noticeMessageAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };
}
