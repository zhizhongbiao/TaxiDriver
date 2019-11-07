package com.taxidriver.santos.network.converter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 *
 *
 * 统一返回格式 只返回data节点的数据
 */

final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final String TAG = "JsonConverter";

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        try {
            String body = value.string();
            JSONObject jsonObj = new JSONObject(body);
            int status = jsonObj.optInt("status");
            String msg = jsonObj.optString("msg", "服务器开小差了~~");

            Log.i(TAG, "======status=====" + String.valueOf(status));
            Log.i(TAG, "====== msg =====" + msg);

            if (status == 1) {
                if (jsonObj.has("data")) {
                    Object data = jsonObj.get("data");
                    body = data.toString();
                    Log.i(TAG, "======data=====" + body);
                    return adapter.fromJson(body);
                } else {
                    return (T) msg;
                }
            } else {
                throw new RuntimeException(msg);
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}
