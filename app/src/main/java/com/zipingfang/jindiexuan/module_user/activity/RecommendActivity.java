package com.zipingfang.jindiexuan.module_user.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xilada.xldutils.activitys.TitleBarActivity;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/22.
 */

public class RecommendActivity extends TitleBarActivity {
    @BindView(R.id.tv_wechat)
    TextView tv_wechat;
    @BindView(R.id.tv_wechat_friend)
    TextView tv_wechat_friend;
    @BindView(R.id.tv_qq)
    TextView tv_qq;
    @BindView(R.id.tv_qzone)
    TextView tv_qzone;

    private Unbinder unbinder;
    private String title,
            content,
            url;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void initView() throws JSONException, IllegalAccessException {
        setTitle("推荐有奖");
        normalTypeFace();
        showTitleBarLine(true);
        unbinder = ButterKnife.bind(this);
        getShreContents();
    }

    @OnClick({R.id.tv_qq,R.id.tv_qzone})
    void onClicks(View view){
        switch (view.getId()) {
            case R.id.tv_qq:
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.tv_qzone:
                share(SHARE_MEDIA.QZONE);
                break;
        }
    }
    private static final String TAG = "RecommendActivity";
    private void getShreContents() {
        RequestManager.regShare(SharedPreferencesUtils.getString(Const.User.TOKEN), new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                Log.d(TAG, "onResponse: ------>"+response.toString());
                JSONObject object  =response.getJsonObject();
                title = object.optString("title");
                content = object.optString("content");
                url = object.optString("url");
            }

            @Override
            public void onResult() {
                super.onResult();
            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);

            }
        });
    }

    private void share(final SHARE_MEDIA i) {
        if (TextUtils.isEmpty(content)&&TextUtils.isEmpty(title)&&TextUtils.isEmpty(url))return;
        Glide.with(this).load(R.mipmap.icon_share_logo).asBitmap().override(200,200).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题
                web.setThumb(new UMImage(RecommendActivity.this,resource));  //缩略图
                web.setDescription(content);//描述
                new ShareAction(RecommendActivity.this)
                        .setPlatform(i)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();
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
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                android.widget.Toast.makeText(mContext, platform + " 收藏成功啦", android.widget.Toast.LENGTH_SHORT).show();
            }else{
                android.widget.Toast.makeText(mContext, platform + " 分享成功啦", android.widget.Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            android.widget.Toast.makeText(mContext, platform + " 分享失败啦", android.widget.Toast.LENGTH_SHORT).show();
            if(t!=null){
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            android.widget.Toast.makeText(mContext, platform + " 分享取消了", android.widget.Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}
