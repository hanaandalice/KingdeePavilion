package com.zipingfang.jindiexuan.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 *
 * Created by liaoxiang on 16/3/25.
 */
public class ResultData implements Serializable {
    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getJsonObject() {
        return data==null?new JSONObject(): (JSONObject) data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JSONArray getJsonArray() {
        return data==null?new JSONArray(): (JSONArray) data;
    }
    public String getString() {
        return data==null?"": (String) data;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                ", info=" + data +
                '}';
    }
}
