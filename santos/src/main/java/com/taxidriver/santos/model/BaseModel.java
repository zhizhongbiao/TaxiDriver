package com.taxidriver.santos.model;

import com.taxidriver.santos.presenter.MvpCallback;
import com.taxidriver.santos.network.NetworkManager;
import com.taxidriver.santos.network.bean.BaseBean;
import com.taxidriver.santos.network.operation.NetUtil;
import com.taxidriver.santos.network.operation.ResponseSubcriber;
import com.taxidriver.santos.network.ResponseCallback;
import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.utils.ui.UIUtils;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @ProjectName: TaxiDriver
 * @Package: com.taxidriver.santos.model
 * @ClassName: BaseModel
 * @Description:
 * @Author: TaxiDriverSantos
 * @CreateDate: 2019/11/7   10:54
 * @UpdateUser: TaxiDriverSantos
 * @UpdateDate: 2019/11/7   10:54
 * @UpdateRemark:
 * @Version: 1.0
 */
public abstract class BaseModel<C extends MvpCallback<BaseBean>> implements
        IModel<C>, ResponseCallback<BaseBean> {

    private final CompositeDisposable list = new CompositeDisposable();
    protected C callback;

    protected <D extends BaseBean> void doPost(String url, Map<String, String> params, Class<D> clz) {
        NetworkManager.getInst().doPost(url, params, new ResponseSubcriber(this, clz));
    }

    protected <D extends BaseBean> void doPost(String url, Class<D> clz) {
        NetworkManager.getInst().doPost(url, new ResponseSubcriber(this, clz));
    }

    protected <D extends BaseBean> void doGet(String url, Class<D> clz) {
        NetworkManager.getInst().doGet(url, new ResponseSubcriber(this, clz));
    }

    @Override
    public void setMvpCallback(C mvpCallback) {
        LogUtils.v("setMvpCallback  mvpCallback = " + mvpCallback);
        this.callback = mvpCallback;
    }

    @Override
    public void onSubscribe(Disposable d) {

        if (!NetUtil.isNetworkConnected(UIUtils.getContext())) {
            callback.onError(new Throwable("Network is not working , please check the situation !"));
            // 一定主动调用下面这一句,取消本次Subscriber订阅
            if (!d.isDisposed()) {
                d.dispose();
            }
            return;
        }


        LogUtils.v("onSubscribe  Disposable = " + d);
        if (callback != null) {
            callback.beforeRequest();
        }

        list.add(d);
    }

    @Override
    public void onSucceed(BaseBean bean) {
        LogUtils.v("onSucceed  bean = " + bean);
        if (callback != null) {
            callback.onSucceed(bean);
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.v("onError  e = " + e);
        if (callback != null) {
            callback.onError(e);
        }
    }

    @Override
    public void onDetachedPresenter() {
        LogUtils.v("onDetachedPresenter ");
        list.dispose();
    }

    @Override
    public void onComplete() {
        LogUtils.v("onComplete ");
    }
}
