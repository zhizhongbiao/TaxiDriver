package com.taxidriver.santos.network;

import android.text.TextUtils;

import com.taxidriver.santos.network.api.Api;
import com.taxidriver.santos.network.api.ApiService;
import com.taxidriver.santos.network.converter.MyGsonConverterFactory;
import com.taxidriver.santos.network.cookie.ClearableCookieJar;
import com.taxidriver.santos.network.cookie.PersistentCookieJar;
import com.taxidriver.santos.network.cookie.cache.SetCookieCache;
import com.taxidriver.santos.network.cookie.persistence.SharedPrefsCookiePersistor;
import com.taxidriver.santos.network.intercepter.RequestInterceptor;
import com.taxidriver.santos.network.operation.SchedulersSwitcher;
import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.utils.ui.UIUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * @ProjectName: TaxiDriver
 * @Package: com.taxidriver.santos.network
 * @ClassName: NetworkManager
 * @Description:
 * @Author: TaxiDriverSantos
 * @CreateDate: 2019/11/6   13:39
 * @UpdateUser: TaxiDriverSantos
 * @UpdateDate: 2019/11/6   13:39
 * @UpdateRemark:
 * @Version: 1.0
 */
public class NetworkManager {

    private static NetworkManager inst;
    private ApiService apiService;
    private SchedulersSwitcher schedulersSwitcher;

    public static NetworkManager getInst() {
        if (inst == null) {
            synchronized (NetworkManager.class) {
                if (inst == null) {
                    inst = new NetworkManager();
                }
            }
        }
        return inst;
    }

    private NetworkManager() {
        initRertrofit2();
        initOperation();
    }

    private void initOperation() {
        schedulersSwitcher=new SchedulersSwitcher();
    }

    private void initRertrofit2() {
        LogUtils.v("initRertrofit2   ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(MyGsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

    }

    private ApiService getApiService() {
        return apiService;
    }

    private OkHttpClient getOkHttpClient() {

        LogUtils.v("getOkHttpClient  ");

        //设置网络缓存路径 缓存大小为10M
        try {
            Cache cache = new Cache(new File(UIUtils.getContext().getExternalCacheDir()
                    , "SantosHttpCache"),
                    1024 * 1024 * 10);

            ClearableCookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache()
                            , new SharedPrefsCookiePersistor(UIUtils.getContext()));

            return new OkHttpClient.Builder()
                    .cache(cache)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .cookieJar(cookieJar)
                    .addInterceptor(new RequestInterceptor())   // 添加拦截器，实现缓存以及一些请求头
                    .addInterceptor(initLogInterceptor())//打印日志
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("getOkHttpClient   e=" + e.getMessage());
        }

        return null;
    }

    private static Interceptor initLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.i("requestParams:" + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


//        LoggingInterceptor mLoggingInterceptor = new LoggingInterceptor
//                .Builder()//构建者模式
//                .loggable(true) //是否开启日志打印
//                .setLevel(HttpLoggingInterceptor.Level.BODY) //打印的等级
//                .log(Platform.INFO) // 打印类型
//                .request("Request") // request的Tag
//                .response("Response")// Response的Tag
//                .addHeader("version", BuildConfig.VERSION_NAME)//打印版本
//                .build();
        return interceptor;
    }

    public void doGet(String url, Observer observer) {
        LogUtils.v("doGet  url = "+url+"      observer = "+observer);

        if (!TextUtils.isEmpty(url)) {
            url = url.trim();
        }
        getApiService().getRequest(url)
                .compose(schedulersSwitcher)   // 线程切换
//                .retryWhen( new RetryWithDelay(3,3000))
                .subscribe(observer);
    }

    public void doPost(String url, Map<String, String> params, Observer observer) {
        LogUtils.v("doPost  url = "+url+"      params = "+params+"      observer = "+observer);

        if (!TextUtils.isEmpty(url)) {
            url = url.trim();
        }

        Observable<ResponseBody> responseBodyObservable ;
        if (params == null || params.isEmpty()) {
            responseBodyObservable = getApiService().postRequest(url);
        } else {
            responseBodyObservable = getApiService().postRequest(url, params);
        }

        responseBodyObservable
                .compose(schedulersSwitcher)
//                .retryWhen( new RetryWithDelay(3,3000))
                .subscribe(observer);
    }

    public void doPost(String url, Observer observer) {
        doPost(url, null, observer);
    }


}

