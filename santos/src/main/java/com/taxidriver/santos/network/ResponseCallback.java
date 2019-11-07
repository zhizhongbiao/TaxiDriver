package com.taxidriver.santos.network;


import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.taxidriver.santos.network.bean.BaseBean;
import com.taxidriver.santos.utils.log.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public interface ResponseCallback<D extends BaseBean>{

    public void onSubscribe(Disposable d) ;

    public void onSucceed(D bean) ;

    public void onError(Throwable e) ;

    public void onComplete();
}
