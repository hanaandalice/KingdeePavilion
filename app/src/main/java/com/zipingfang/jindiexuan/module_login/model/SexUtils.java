package com.zipingfang.jindiexuan.module_login.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/2.
 */
public class SexUtils implements Serializable {
    private String data;
    public SexUtils(String data){
        this.data =data;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if( TextUtils.isEmpty(getData()))return"";
        return getData();
    }
}
