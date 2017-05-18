/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.xilada.xldutils.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义三个TabView 的Fragment ,将三个TabView共同属性方法
 * 统一处理
 * 三个TabView 滑动页面需要继承该基类，
 * Created by Jorstin on 2015/3/18.
 */
public abstract class TabFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId()!=-1){
            return inflater.inflate(getLayoutId(),null);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        onReleaseTabUI();
        super.onDestroy();
    }

    /**
     * 当前TabFragment被点击
     */
    protected abstract void onTabFragmentClick();

    /**
     * 当前TabFragment被释放
     */
    protected abstract void onReleaseTabUI();

    /**
     * 每个页面需要实现该方法返回一个该页面所对应的资源ID
     * @return 页面资源ID
     */
    protected abstract int getLayoutId();

}

