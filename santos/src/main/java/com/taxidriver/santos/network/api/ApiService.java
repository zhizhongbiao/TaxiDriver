package com.taxidriver.santos.network.api;

import com.taxidriver.santos.network.api.Api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * @ProjectName: TaxiDriver
 * @Package: com.taxidriver.santos.network
 * @ClassName: ApiService
 * @Description:
 * @Author: TaxiDriverSantos
 * @CreateDate: 2019/11/6   16:01
 * @UpdateUser: TaxiDriverSantos
 * @UpdateDate: 2019/11/6   16:01
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface ApiService {

    @GET
    Observable<ResponseBody> getRequest(@Url() String url);

    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postRequest(@Url() String url, @FieldMap Map<String, String> params);

    @POST
    Observable<ResponseBody> postRequest(@Url() String url);

    /**
     * 发表动态
     */
    @Multipart
    @POST("base/upload.json")
    rx.Observable<ResponseBody> postUploadImg(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part filePart);
}
