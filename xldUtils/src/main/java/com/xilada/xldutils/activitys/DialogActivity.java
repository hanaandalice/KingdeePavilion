package com.xilada.xldutils.activitys;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import com.xilada.xldutils.R;

/**
 * 弹窗式activity，例如选择照片弹窗，自定义分享窗等
 */
public abstract class DialogActivity extends BaseActivity {
    /**
     * 退出动画
     * @return 动画资源id
     */
    protected int exitAnim(){
        return 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT>=11){
            setFinishOnTouchOutside(true);
        }
    }

    /**
     * 是否可点击空白区域消失，要求ApiLevel 11
     * @param cancel 是否取消
     * @see #setFinishOnTouchOutside
     */
    protected void setCancelOnTouchOutside(boolean cancel){
        if (Build.VERSION.SDK_INT>=11){
            setFinishOnTouchOutside(cancel);
        }
    }
    @Override
    public void finish() {
        super.finish();
        int exitAnim=exitAnim();
        System.err.println("-------"+exitAnim);
        if (exitAnim == 0)
            exitAnim = R.anim.popup_out;
        //设置退出动画，xml设置的无效
        overridePendingTransition(0, exitAnim);
    }
}
