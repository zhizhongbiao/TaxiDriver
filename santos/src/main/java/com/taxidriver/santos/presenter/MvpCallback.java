package com.taxidriver.santos.presenter;

import com.taxidriver.santos.network.bean.BaseBean;

/**
 * @ProjectName: TaxiDriver
 * @Package: com.taxidriver.santos
 * @ClassName: MvpCallback
 * @Description:
 * @Author: TaxiDriverSantos
 * @CreateDate: 2019/11/7   15:27
 * @UpdateUser: TaxiDriverSantos
 * @UpdateDate: 2019/11/7   15:27
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface MvpCallback <D extends BaseBean>{
    void beforeRequest();
    void onSucceed(D bean);
    void onError(Throwable e);
}
