package com.taxidriver.santos.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 自定的 json 转换器，将数据装换为 ResponseBody
 */

public class MyGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    public static MyGsonConverterFactory create() {
        return create(new Gson());
    }


    public static MyGsonConverterFactory create(Gson gson) {
        return new MyGsonConverterFactory(gson);
    }


    private MyGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }


    /**
     * 该方法用来转换服务器返回数据
     *
     * @param type
     * @param annotations
     * @param retrofit
     * @return
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MyGsonResponseBodyConverter<>(gson, adapter);
    }

    /**
     * 该方法用来转换发送给服务器的数据
     *
     * @param type
     * @param parameterAnnotations
     * @param methodAnnotations
     * @param retrofit
     * @return
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MyGsonRequestBodyConverter<>(gson, adapter);
    }

}
