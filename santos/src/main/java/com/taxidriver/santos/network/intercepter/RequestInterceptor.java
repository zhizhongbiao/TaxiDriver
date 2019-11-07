package com.taxidriver.santos.network.intercepter;




import com.taxidriver.santos.utils.log.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>
 * 网络请求的固定参数
 * <p>
 * 添加请求头
 */

public class RequestInterceptor implements Interceptor {

    private static final String TAG = "RequestInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request.Builder builder = request
                .newBuilder();

        //添加固定请求头
        builder.addHeader("BODY-X-TYPE", "2")
                .addHeader("BODY-X-VERSION", "1.0")
                .build();

        Response response = chain.proceed(request);//执行请求

        LogUtils.e(TAG + "========request url========" + response.request().url());


        /**
         * ==========一次网络请求完成，此处可以对服务器返回的数据做处理，比如header，
         *
         * 但是这里最好只处理hear等信息，对具体返回的内容在retrofit的转换器中做统一处理=====================
         */

       LogUtils.e(TAG + "=======response code=======" + response.code());

        if (response.isSuccessful()) { //访问成功
           LogUtils.e(TAG + "=======isSuccessful=======");
        } else {                       //访问失败
           LogUtils.e(TAG + "=======失败了=======");
        }


        return response;
    }
}
