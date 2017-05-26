package com.zipingfang.jindiexuan.module_user;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.xilada.xldutils.fragment.BaseLazyFragment;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.tool.CacheActivity;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.xilada.xldutils.utils.Toast;
import com.xilada.xldutils.utils.Version;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.module_login.activity.LoginActivity;
import com.zipingfang.jindiexuan.module_user.activity.AboutActivity;
import com.zipingfang.jindiexuan.module_user.activity.FeedBackActivity;
import com.zipingfang.jindiexuan.module_user.activity.LeaderBoardActivity;
import com.zipingfang.jindiexuan.module_user.activity.OrdersSettingActivity;
import com.zipingfang.jindiexuan.module_user.activity.MyWallectActivity;
import com.zipingfang.jindiexuan.module_user.activity.NoticeMesssageActivity;
import com.zipingfang.jindiexuan.module_user.activity.PersonalInformationActivity;
import com.zipingfang.jindiexuan.module_user.activity.PunchActivity;
import com.zipingfang.jindiexuan.module_user.activity.RecommendActivity;
import com.zipingfang.jindiexuan.module_user.activity.RevisePasswordActivity;
import com.zipingfang.jindiexuan.module_user.model.UserModel;
import com.zipingfang.jindiexuan.utils.Const;
import com.zipingfang.jindiexuan.view.gradation.GradationScrollView;

import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/18.
 */

public class UserFragment extends BaseLazyFragment implements View.OnClickListener{
    private RelativeLayout layout_title;
    private GradationScrollView gradationScrollview;
    private RelativeLayout layout_banner;
    private ImageView iv_head_img,
            iv_message;
    private TextView tv_punch,
            tv_my_wallect,
            tv_recommend,
            tv_leader_board,
            tv_orders_setting,
            tv_feedback,
            tv_modify_password,
            tv_user_share,
            tv_about,
            tv_test_updata,
            tv_out_login,
            tv_name,
            tv_driver,
            tv_driver_statues;


    private int titleHeight;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_user;
    }

    @Override
    protected void onFirstVisibleToUser() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layout_title =findViewById(R.id.layout_title);
        gradationScrollview =findViewById(R.id.gradationScrollview);
        layout_banner =findViewById(R.id.layout_banner);
        iv_head_img =findViewById(R.id.iv_head_img);
        tv_punch =findViewById(R.id.tv_punch);
        tv_my_wallect =findViewById(R.id.tv_my_wallect);
        tv_recommend =findViewById(R.id.tv_recommend);
        tv_leader_board =findViewById(R.id.tv_leader_board);
        tv_orders_setting =findViewById(R.id.tv_orders_setting);
        tv_feedback =findViewById(R.id.tv_feedback);
        tv_modify_password =findViewById(R.id.tv_modify_password);
        tv_user_share =findViewById(R.id.tv_user_share);
        tv_about =findViewById(R.id.tv_about);
        tv_test_updata =findViewById(R.id.tv_test_updata);
        tv_out_login =findViewById(R.id.tv_out_login);
        iv_message =findViewById(R.id.iv_message);
        tv_name =findViewById(R.id.tv_name);
        tv_driver =findViewById(R.id.tv_driver);
        tv_driver_statues =findViewById(R.id.tv_driver_statues);

        ViewTreeObserver vto = layout_banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout_title.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int intw= View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int inth=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                layout_banner.measure(intw, inth);
                titleHeight = layout_banner.getMeasuredHeight();
                gradationScrollview.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
                        // TODO Auto-generated method stub
                        if (y <= 0) {   //设置标题的背景颜色
                            layout_title.setBackgroundColor(Color.argb((int) 0, 138,0,0));
                        } else if (y > 0 && y <= titleHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            float scale = (float) y / titleHeight;
                            float alpha = (255 * scale);
                            layout_title.setBackgroundColor(Color.argb((int) alpha, 138,0,0));
                        } else {    //滑动到banner下面设置普通颜色
                            layout_title.setBackgroundColor(Color.argb((int) 255, 138,0,0));
                        }
                    }
                });
            }
        });
        iv_head_img.setOnClickListener(this);
        tv_punch.setOnClickListener(this);
        tv_my_wallect.setOnClickListener(this);
        tv_recommend.setOnClickListener(this);
        tv_leader_board.setOnClickListener(this);
        tv_orders_setting.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        tv_modify_password.setOnClickListener(this);
        tv_user_share.setOnClickListener(this);
        tv_about.setOnClickListener(this);
        tv_test_updata.setOnClickListener(this);
        tv_out_login.setOnClickListener(this);
        iv_message.setOnClickListener(this);

        retrieveData();
    }

    private static final String TAG = "UserFragment";
    private void retrieveData() {
        RequestManager.myHomePage(SharedPreferencesUtils.getString(Const.User.TOKEN)
                , new HttpUtils.ResultCallback<ResultData>() {
                    @Override
                    public void onResponse(ResultData response) {
                        Log.d(TAG, "onResponse: ----------->"+response.toString());
                        JSONObject object =response.getJsonObject();
                        UserModel userModel =new UserModel();
                        userModel.setHead_pic(object.optString("head_pic"));
                        userModel.setUsername(object.optString("username"));
                        userModel.setDriver(object.optString("driver"));
                        userModel.setDriver_type(object.optString("driver_type"));
                        userModel.setDriver_status(object.optString("driver_status"));
                        userModel.setNoti_num(object.optString("noti_num"));
                        refreshData(userModel);
                    }
                    @Override
                    public void onResult() {
                        super.onResult();
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

    private void refreshData(UserModel userModel) {
        if (null!=getActivity()) {
            SharedPreferencesUtils.save(Const.User.USER_HEAD_IMG,userModel.getHead_pic());
            Glide.with(getActivity()).load(userModel.getHead_pic()).error(R.mipmap.icon_default_head).into(iv_head_img);
            tv_name.setText(userModel.getUsername());
            if (TextUtils.equals("1",userModel.getDriver())) {
                tv_driver.setText("未认证");
            }else if (TextUtils.equals("2",userModel.getDriver())){
                tv_driver.setText("兼职汽车司机");
            }else if (TextUtils.equals("3",userModel.getDriver())){
                tv_driver.setText("专职汽车司机");
            }
            if (TextUtils.equals("1",userModel.getDriver_status())) {
                tv_driver_statues.setText("已审核");
            }else  if (TextUtils.equals("2",userModel.getDriver_status())) {
                tv_driver_statues.setText("审核中");
            }else if (TextUtils.equals("3",userModel.getDriver_status())) {
                tv_driver_statues.setText("未通过");
            }
        }
        SharedPreferencesUtils.save(Const.User.NAME,userModel.getUsername());
        SharedPreferencesUtils.save(Const.User.USER_DRIVER,userModel.getDriver());
        SharedPreferencesUtils.save(Const.User.USER_DRIVER_TYPE,userModel.getDriver_type());
        SharedPreferencesUtils.save(Const.User.USER_DRIVER_STATUS,userModel.getDriver_status());
    }

    @Override
    protected void onVisibleToUser() {
        Log.d(TAG, "onVisibleToUser: ");
    }

    @Override
    protected void onInvisibleToUser() {
        Log.d(TAG, "onInvisibleToUser: ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head_img:
                goActivity(PersonalInformationActivity.class);
                break;
            case R.id.tv_punch:
                goActivity(PunchActivity.class);
                break;
            case R.id.tv_my_wallect:
                goActivity(MyWallectActivity.class);
                break;
            case R.id.tv_recommend:
                goActivity(RecommendActivity.class);
                break;
            case R.id.tv_leader_board:
                goActivity(LeaderBoardActivity.class);
                break;
            case R.id.tv_orders_setting:
                goActivity(OrdersSettingActivity.class);
                break;
            case R.id.tv_feedback:
                goActivity(FeedBackActivity.class);
                break;
            case R.id.tv_modify_password:
                goActivity(RevisePasswordActivity.class);
                break;
            case R.id.tv_user_share:
                if (null!=shareClickListener) {
                    shareClickListener.onClick();
                }
                break;
            case R.id.tv_about:
                goActivity(AboutActivity.class);
                break;
            case R.id.tv_test_updata:
                Toast.create(getActivity()).show("当前版本："+ Version.getVersion(getActivity())[0]);
                break;
            case R.id.tv_out_login:
                final NormalDialog dialog2 = new NormalDialog(getActivity());
                dialog2.content("确定要退出当前账号？")
                        .contentTextSize(14)
                        .cornerRadius(15)
                        .contentTextColor(getResources().getColor(R.color.black))
                        .style(NormalDialog.STYLE_TWO)
                        .titleTextColor(getResources().getColor(R.color.black))
                        .titleTextSize(18)
                        .btnNum(2)
                        .btnTextSize(18)
                        .btnTextColor(getResources().getColor(R.color.hintColor_66),getResources().getColor(R.color.textAccent))
                        .btnText("取消","确定")
                        .showAnim(null)
                        .dismissAnim(null)
                        .show();
                dialog2.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog2.dismiss();
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog2.dismiss();
                                CacheActivity.finishActivity();
                                SharedPreferencesUtils.clear();
                                goActivity(LoginActivity.class);
                            }
                        });
                break;
            case R.id.iv_message:
                goActivity(NoticeMesssageActivity.class);
                break;
        }
    }
    public interface ShareClickListener{
        void onClick();
    }
    private ShareClickListener shareClickListener;
    public void setShareClickListener(ShareClickListener shareClickListener){
        this.shareClickListener = shareClickListener;
    }
}
