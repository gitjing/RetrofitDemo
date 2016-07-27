package com.example.ljj.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPhoneRx("18301538075");
    }

    //RxJava与Retrofit结合使用
    private void getPhoneRx(String phone) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        client.addInterceptor(headerInterceptor);
        client.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client.build())
                .build();

        ApiStoreInter apiInter = retrofit.create(ApiStoreInter.class);

        Observable<PhoneInfo> phoneInfo2 = apiInter.getPhoneInfo2(phone);

        phoneInfo2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhoneInfo>() {
                    @Override
                    public void onCompleted() {

                        System.out.println("---complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("----error");
                    }

                    @Override
                    public void onNext(PhoneInfo phoneInfo) {
                        System.out.println("--onNext" + phoneInfo.toString());
                        String province = phoneInfo.getRetData().getProvince();
                        if (!TextUtils.isEmpty(province)){
                            Toast.makeText(MainActivity.this, province, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    //Retrofit使用+Log信息拦截
    private void startQueryMobile(String phone) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(loggingInterceptor);

        //1.获取retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2.接口代理对象
        final ApiStoreInter apiStoreInter = retrofit.create(ApiStoreInter.class);

        //3.调用接口里面的方法
        final Call<PhoneInfo> phoneInfo = apiStoreInter.getPhoneInfo(Constants.API_KEY, phone);

        //4.异步请求
        phoneInfo.enqueue(new Callback<PhoneInfo>() {
            @Override
            public void onResponse(Call<PhoneInfo> call, Response<PhoneInfo> response) {
                String province = response.body().getRetData().getProvince();
                if (!TextUtils.isEmpty(province)){
                    Toast.makeText(MainActivity.this, province, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PhoneInfo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
}











