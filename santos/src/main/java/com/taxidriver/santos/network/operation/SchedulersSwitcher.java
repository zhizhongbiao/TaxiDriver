package com.taxidriver.santos.network.operation;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @ProjectName: TaxiDriver
 * @Package: com.taxidriver.santos.network.operation
 * @ClassName: SchedulersSwitcher
 * @Description:
 * @Author: TaxiDriverSantos
 * @CreateDate: 2019/11/7   9:28
 * @UpdateUser: TaxiDriverSantos
 * @UpdateDate: 2019/11/7   9:28
 * @UpdateRemark:
 * @Version: 1.0
 */
public class SchedulersSwitcher implements ObservableTransformer<ResponseBody, ResponseBody> {
    @Override
    public ObservableSource<ResponseBody> apply(Observable<ResponseBody> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
