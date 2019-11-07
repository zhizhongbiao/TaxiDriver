package com.taxidriver.santos.network.operation;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.taxidriver.santos.network.bean.BaseBean;
import com.taxidriver.santos.network.ResponseCallback;
import com.taxidriver.santos.utils.log.LogUtils;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import okhttp3.ResponseBody;

/**
 * @ProjectName: TaxiDriver
 * @Package: com.taxidriver.santos.network.operation
 * @ClassName: ResponseSubcriber
 * @Description:
 * @Author: TaxiDriverSantos
 * @CreateDate: 2019/11/7   12:57
 * @UpdateUser: TaxiDriverSantos
 * @UpdateDate: 2019/11/7   12:57
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ResponseSubcriber<D extends BaseBean> implements Observer<ResponseBody> {


   private ResponseCallback<D> responseCallback;
   private Class<D> beanClass;


    public ResponseSubcriber(ResponseCallback<D> responseCallback, Class<D> beanClass) {
        Objects.requireNonNull(responseCallback,"ResponseCallback can not be null");
        Objects.requireNonNull(beanClass,"BeanClass can not be null");
        this.responseCallback = responseCallback;
        this.beanClass = beanClass;
    }



    @Override
    public void onSubscribe(Disposable d) {
   responseCallback.onSubscribe(d);
    }

    @Override
    public void onNext(ResponseBody responseBody) {

        try {
            byte[] bytes = responseBody.bytes();
            String jsonString = new String(bytes);
            LogUtils.d("onNext  ====ResponseBody:====" + jsonString);
            if (TextUtils.isEmpty(jsonString)) {
                responseCallback.onError(new Exception("ResponseBody  is empty"));
                return;
            }
            D data =  new Gson().fromJson(jsonString, beanClass);
            responseCallback.onSucceed(data);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("failed to resolve data  Exception: " + e.getMessage());
            responseCallback.onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        responseCallback.onError(e);
    }

    @Override
    public void onComplete() {
        responseCallback.onComplete();
    }
}
