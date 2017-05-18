package com.xilada.xldutils.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xilada.xldutils.view.utils.ViewHolder;

import java.lang.reflect.Field;

/**
 * fragment
 * Created by liaoxiang on 15/12/28.
 */
public abstract class BaseLazyFragment extends BaseFragment{

    protected abstract int getContentViewLayoutID();
    protected abstract void onFirstVisibleToUser();
    protected abstract void onVisibleToUser();
    protected abstract void onInvisibleToUser();

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;
    protected ViewHolder mHolder;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume){
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()){
            onVisibleToUser();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onInvisibleToUser();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onVisibleToUser();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
//                onFirstUserInvisible();
            } else {
               onInvisibleToUser();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        //noinspection TryWithIdenticalCatches
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstVisibleToUser();
        } else {
            isPrepared = true;
        }
    }
}
