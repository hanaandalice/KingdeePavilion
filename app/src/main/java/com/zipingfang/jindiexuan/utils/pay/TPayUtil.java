package com.zipingfang.jindiexuan.utils.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Message;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 第三方支付工具类
 */
public class TPayUtil {

    public static IWXAPI iwxapi;
    public static final int SDK_PAY_FLAG = 1;

   public static IWXAPI initWeChatPay(Context context, String appId) {
        //注册微信支付
        iwxapi = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        iwxapi.registerApp(appId);
        return iwxapi;
    }

    public static boolean checkSupportWeChat(Context context) {
        if (iwxapi == null) {
            try {
                throw new Exception("please init first");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return iwxapi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    public static void weChatPay(Context context, JSONObject response) {
        if (iwxapi == null) {
            try {
                throw new Exception("please init first");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        PayReq req = new PayReq();
//        req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        req.appId = response.optString("appid");
        req.partnerId = response.optString("partnerId");
        req.prepayId = response.optString("prepayId");
        req.nonceStr = response.optString("nonceStr");
        req.timeStamp = response.optString("timeStamp");
        req.packageValue = response.optString("package");
        req.sign = response.optString("sign");
        iwxapi.sendReq(req);
    }

    public static boolean checkAliPayState(Context context) {
        String packageName = "com.eg.android.AlipayGphone";
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = context.getPackageManager();
            List apps = pManager.queryIntentActivities(
                    resolveIntent, 0);

            ResolveInfo ri = (ResolveInfo) apps.iterator().next();
            return ri != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void aliPay(final Context context, final String payInfo, final Handler handler) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
//                PayTask alipay = new PayTask((Activity) context);
//                // 调用支付接口，获取支付结果
//                Map<String, String> result = alipay.payV2(payInfo, true);
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
