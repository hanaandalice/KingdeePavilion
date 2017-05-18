package com.xilada.xldutils.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.xilada.xldutils.R;

import java.util.ArrayList;

/**
 * 权限管理工具
 * Created by liaoxiang on 16/3/17.
 */
public class PermissionManager {

    /**
     * 请求权限
     * @param activity 请求权限的页面上下文
     * @param permission 权限字符
     * @param dialogMessage 如果用户以前禁止了，展示提示窗体文字
     * @param requestCode 请求code。必须小于8bits，不然报错。
     */
    public static boolean request(final Activity activity, final String permission, String dialogMessage, final int requestCode){
        if (ContextCompat.checkSelfPermission(activity,permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                String title = String.format("请授予%s应用%s权限,否则无法正常工作！请于\"设置\"－\"应用\"-\"权限\"中配置权限。",activity.getResources().getString(R.string.app_name),dialogMessage);
                DialogUtils.createNoticeDialog(activity, dialogMessage, title, "确定", null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                }, null);
            }else {
                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        requestCode);
            }
            return false;
        }
        return true;
    }
    public static boolean request(final Activity activity, final String[] permission, String dialogMessage, final int requestCode){
        ArrayList<String> permissionList = new ArrayList<>();
        if (permission != null && permission.length > 0) {
            for (String s : permission) {
                if (ContextCompat.checkSelfPermission(activity, s)
                        != PackageManager.PERMISSION_GRANTED) {
                    //是否是第一次请求该权限
                    boolean isFirst = SharedPreferencesUtils.getBoolean(s, true);
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, s) && !isFirst) {
                        String title = String.format("请授予%s应用%s权限,否则无法正常工作！请于\"设置\"－\"应用\"-\"权限\"中配置权限。", activity.getResources().getString(R.string.app_name), dialogMessage);
                        DialogUtils.createNoticeDialog(activity, dialogMessage, title, "确定", null, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.finish();
                            }
                        }, null);
                        return false;
                    } else {
                        permissionList.add(s);
                    }
                }
            }

            if (permissionList.size() > 0) {
                ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[]{}),
                        requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
