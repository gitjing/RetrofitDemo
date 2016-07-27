package com.example.ljj.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：ljj
 * 日期：2016/7/19 15:41
 */
public interface ApiStoreInter {

    @GET(Constants.QUERY_MOBILE_URL)
    Call<PhoneInfo> getPhoneInfo(@Header("apikey") String apiKey, @Query("phone") String phoneNum);

    /**
     * 添加header表现形式1
     * 按照这个添加方式，如果有多个header需要添加
     * @Headers({
     *"Accept: application/vnd.github.v3.full+json",
     *"User-Agent: Retrofit-Sample-App"})
     *
     */
    @Headers("apikey:48b0fc4c8047a43a9da5d5c1f65df2e9")
    @GET(Constants.QUERY_MOBILE_URL)
    Observable<PhoneInfo> getPhoneInfo2(@Query("phone") String num);

    //添加header表现形式2
    @GET(Constants.QUERY_MOBILE_URL)
    Observable<PhoneInfo> headerEg1(@Header("apikey") String apikey, @Query("phone") String phoneNum);



}
