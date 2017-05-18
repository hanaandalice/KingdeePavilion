package com.xilada.xldutils.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.xilada.xldutils.R;
/**
 * Created by Lenovo on 2016/4/2.
 */
public class DiaLogBuider{
    private Dialog dialog;
    private Window window;
    private Context context;
    private static boolean isDismiss;
    public DiaLogBuider(Context context) {
        super();
        this.dialog = new Dialog(context, R.style.dialog);
        this.context =context;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (selectSexDiaLogDismissLister != null) {
                    selectSexDiaLogDismissLister.onDiaLogDismss();
                }
            }
        });
    }
    /*
     * 设置从哪里弹出
     */
    public DiaLogBuider setGrvier(int grvier){
        if (window ==null) {
            window =dialog.getWindow();
        }
        window.setGravity(grvier);
        return this;
    }
    /*
     * 设置视图
     */
    public DiaLogBuider setContentView(View view){
        dialog.setContentView(view);
        return this;
    }

    /*
     * 设置是否全屏
     */
    public DiaLogBuider setFullScreen(){
        if (window ==null) {
            window =dialog.getWindow();
        }
        LayoutParams layouParams =window.getAttributes();
        layouParams.width =LayoutParams.MATCH_PARENT;
        return this;
    }
    /*
     * 显示Dialog
     */
    public DiaLogBuider setShow(){
        dialog.show();
        return this;
    }
    /*
     * 隐藏Dialog
     */
    public DiaLogBuider setDismiss(){
        dialog.dismiss();
        return this;
    }
    /*
     * Dialog的动画
     */
    public DiaLogBuider setAniMo(int anim){
        if (window==null) {
            window =dialog.getWindow();
        }
        window.setWindowAnimations(anim);
        return this;
    }
    public interface DismissLisenter{
        void onDiaLogDismss();
    }
    private DismissLisenter selectSexDiaLogDismissLister;
    public void setDiaLogDismissLister(DismissLisenter selectSexDiaLogDismissLister){
        this.selectSexDiaLogDismissLister =selectSexDiaLogDismissLister;
    }

}
