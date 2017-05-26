package com.zipingfang.jindiexuan.umeng;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.flyco.animation.FlipEnter.FlipVerticalEnter;
import com.flyco.animation.FlipExit.FlipVerticalExit;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xilada.xldutils.network.HttpUtils;
import com.xilada.xldutils.utils.SharedPreferencesUtils;
import com.zipingfang.jindiexuan.R;
import com.zipingfang.jindiexuan.api.Api;
import com.zipingfang.jindiexuan.api.RequestManager;
import com.zipingfang.jindiexuan.api.ResultData;
import com.zipingfang.jindiexuan.utils.Const;

import org.json.JSONObject;

import okhttp3.Call;


public class ShareBottomDialog extends BottomBaseDialog<ShareBottomDialog> {
    private TextView tv_wx;
    private TextView tv_wx_f;
    private TextView tv_qq;
    private TextView tv_qq_z;
    private TextView tv_wb;
    private TextView tv_cancle;
    private Context context;
    private RelativeLayout layout_dismiss;
    private Activity activity;
    private String title,
            content,
            url;
    public ShareBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public ShareBottomDialog(Context context, Activity activity) {
        super(context);
        this.context =context;
        this.activity =activity;
    }
    @Override
    public View onCreateView() {
        showAnim(new FlipVerticalEnter());
        dismissAnim(new FlipVerticalExit());
        View inflate = View.inflate(mContext, R.layout.dialog_share, null);
        tv_wx = (TextView) inflate.findViewById(R.id.tv_wx);
        tv_wx_f = (TextView) inflate.findViewById(R.id.tv_wx_f);
        tv_qq_z = (TextView) inflate.findViewById(R.id.tv_qq_z);
        tv_wb = (TextView) inflate.findViewById(R.id.tv_wb);
        tv_qq = (TextView) inflate.findViewById(R.id.tv_qq);
        tv_cancle = (TextView) inflate.findViewById(R.id.tv_cancle);
        layout_dismiss = (RelativeLayout) inflate.findViewById(R.id.layout_dismiss);
        getShareContents();
        return inflate;
    }

    private void getShareContents() {
        RequestManager.share(SharedPreferencesUtils.getString(Const.User.TOKEN), new HttpUtils.ResultCallback<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                JSONObject object  =response.getJsonObject();
                title = object.optString("title");
                content = object.optString("content");
                url = object.optString("url");
            }

            @Override
            public void onError(Call call, String e) {
                super.onError(call, e);
            }

            @Override
            public void onResult() {
                super.onResult();
            }
        });
    }

    @Override
    public void setUiBeforShow() {
//        tv_wx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share(SHARE_MEDIA.WEIXIN);
//                dismiss();
//            }
//        });
//        tv_wx_f.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share(SHARE_MEDIA.WEIXIN_CIRCLE);
//                dismiss();
//            }
//        });
        tv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.QQ);
            }
        });
        tv_qq_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.QZONE);
            }
        });
//        tv_wb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share(SHARE_MEDIA.SINA);
//                dismiss();
//            }
//        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        layout_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    private void share(final SHARE_MEDIA i) {
        Glide.with(context).load(R.mipmap.ic_launcher).asBitmap().override(200,200).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题
                web.setThumb(new UMImage(getContext(),resource));  //缩略图
                web.setDescription(content);//描述
                new ShareAction(activity)
                        .setPlatform(i)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();
            }
        });
    }

    private void shareAddLetter() {

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
            dismiss();
        }
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            android.widget.Toast.makeText(mContext, platform + " 分享失败啦", android.widget.Toast.LENGTH_SHORT).show();
            if(t!=null){
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
            dismiss();
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            android.widget.Toast.makeText(mContext, platform + " 分享取消了", android.widget.Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };
}
