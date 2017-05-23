package com.zipingfang.jindiexuan.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.zipingfang.jindiexuan.R;


/**
 * Created by Administrator on 2016/10/17.
 */
public class XEditText extends EditText {
    private DrawableLeftListener mLeftListener;
    private DrawableRightListener mRightListener;

    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    public XEditText(Context context) {
        super(context);
        init();
    }
    public XEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDrawableLeftListener(DrawableLeftListener listener) {
        this.mLeftListener = listener;
    }

    public void setDrawableRightListener(DrawableRightListener listener) {
        this.mRightListener = listener;
    }

    public interface DrawableLeftListener {
        void onDrawableLeftClick(View view);
    }

    public interface DrawableRightListener {
        void onDrawableRightClick(View view);
    }
    private void init() {
        addTextChangedListener(textWatcher);

    }

    private static final String TAG = "XEditText";
    @Override
    public boolean onTextContextMenuItem(int id) {
        Log.d(TAG, "onTextContextMenuItem: ----------->"+id);
//        id:16908319  --- 全选
//        id:16908328  --- 选择
//        id:16908320  --- 剪贴
//        id:16908321  --- 复制
//        id:16908322  --- 粘贴
//        id:16908324  --- 输入法
        return super.onTextContextMenuItem(id);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mRightListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT] ;
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                        mRightListener.onDrawableRightClick(this) ;
                        return true ;
                    }
                }
                if (mLeftListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT] ;
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        mLeftListener.onDrawableLeftClick(this) ;
                        return true ;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    TextWatcher textWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length()>0){
                Drawable drawable= getResources().getDrawable(R.mipmap.icon_clear);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                setCompoundDrawables(null, null, drawable, null);
            }else{
                //隐藏Drawables
                setCompoundDrawables(null, null, null, null);
            }
        }
    };
}
