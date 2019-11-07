package com.taxidriver.santos.network.bean;

import android.text.TextUtils;


import com.google.gson.Gson;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;


public class BaseBean implements Serializable {



    public BaseBean() {

    }

    /**
     * 请求成功的回调方法，父类默认实现，使用反射方式获取每个field并赋值,field的名称必须和json数据中的名称一致才能有值
     *
     * @param jsonString
     */
    public static <T extends BaseBean> T parseDataVo(String jsonString, Class<T> dataClass) throws JSONException {

        if (TextUtils.isEmpty(jsonString) || dataClass == null) {
            return null;
        }

        T baseVo = null;
        try {
            Gson gson = new Gson();
            baseVo=   gson.fromJson(jsonString,dataClass);
        } catch (Exception ex) {
            throw new JSONException("failed to resolve data  Exception: " + ex.getMessage());
        }
        return baseVo;
    }







}

