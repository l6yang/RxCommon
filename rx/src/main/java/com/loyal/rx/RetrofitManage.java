package com.loyal.rx;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManage {
    private static RetrofitManage mInstance;
    private Retrofit retrofit;

    private RetrofitManage(String baseUrl) {
        reSetIpAdd(baseUrl);
    }

    private void reSetIpAdd(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl))
            baseUrl = "http://192.168.0.1/";
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)       //设置连接超时
                .readTimeout(25, TimeUnit.SECONDS)          //设置读取超时
                .writeTimeout(60, TimeUnit.SECONDS)         //设置写入超时
                .build();
        retrofit = new Retrofit.Builder().client(client)
                .baseUrl(baseUrl)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    public static RetrofitManage getInstance(String baseUrl) {
        if (mInstance == null) {
            synchronized (RetrofitManage.class) {
                if (mInstance == null)
                    mInstance = new RetrofitManage(baseUrl);
                else mInstance.reSetIpAdd(baseUrl);
            }
        } else mInstance.reSetIpAdd(baseUrl);
        return mInstance;
    }

    public static RetrofitManage getInstance() {
        return getInstance(null);
    }

    public <T> T createServer(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}