package com.xilada.xldutils.view.controller;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.xilada.xldutils.R;

/**
 *
 * Created by liaoxiang on 16/3/18.
 */
public abstract class AbstractTitlebarController implements TitlebarController{

    public AbstractTitlebarController(Context context){

    }

    protected int getContentView(){
        return R.layout.activity_title_bar;
    }

    protected void setTitle(){}
    protected void setLeftButton(String name){
        setLeftButton(name,null);
    }
    protected void setLeftButton(String name, View.OnClickListener onClickListener){
        setLeftButton(name,null,onClickListener);
    }
    protected void setLeftButton(String name, Drawable leftDrawable, View.OnClickListener onClickListener){}
    protected void hideLeftButton(){}
}
