package com.loyal.rx;

import android.text.TextUtils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManage {
    private static RetrofitManage mInstance;
    private Retrofit retrofit;

    /**
     * @param trustedCert https方式访问证书是否受信任
     * @param protocol    哪种协议，如SSL协议或者TLS协议
     */
    private RetrofitManage(String baseUrl, boolean trustedCert, String protocol) {
        reSetIpAdd(baseUrl, trustedCert, protocol);
    }

    /**
     * @param trustedCert https方式访问证书是否受信任
     * @param protocol    哪种协议，如SSL协议或者TLS协议
     */
    private void reSetIpAdd(String baseUrl, boolean trustedCert, String protocol) {
        if (TextUtils.isEmpty(baseUrl))
            baseUrl = "http://192.168.0.1/";
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuild = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)       //设置连接超时
                .readTimeout(25, TimeUnit.SECONDS)          //设置读取超时
                .writeTimeout(60, TimeUnit.SECONDS)//设置写入超时
                ;
        System.out.println("reSetIpAdd--"+trustedCert);
        if (!trustedCert) {//若证书不信任执行这里，信任的话流程就和http访问方式一样
            try {
                SSLContext sc = SSLContext.getInstance(protocol);
                X509TrustManager[] trustManager = new X509TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }};
                sc.init(null, trustManager, /*new SecureRandom()*/null);
                clientBuild.sslSocketFactory(sc.getSocketFactory(), trustManager[0])
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        retrofit = new Retrofit.Builder().client(clientBuild.build())
                .baseUrl(baseUrl)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitManage getInstance(String baseUrl) {
        return getInstance(baseUrl, true);
    }

    /**
     * @param trustedCert https方式访问证书是否受信任
     */
    public static RetrofitManage getInstance(String baseUrl, boolean trustedCert) {
        return getInstance(baseUrl, trustedCert, "SSL");
    }

    /**
     * @param trustedCert https方式访问证书是否受信任
     * @param protocol    哪种协议，如SSL协议或者TLS协议
     */
    public static RetrofitManage getInstance(String baseUrl, boolean trustedCert, String protocol) {
        if (mInstance == null) {
            synchronized (RetrofitManage.class) {
                if (mInstance == null)
                    mInstance = new RetrofitManage(baseUrl, trustedCert, protocol);
                else mInstance.reSetIpAdd(baseUrl, trustedCert, protocol);
            }
        } else mInstance.reSetIpAdd(baseUrl, trustedCert, protocol);
        return mInstance;
    }

    public <T> T createServer(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}