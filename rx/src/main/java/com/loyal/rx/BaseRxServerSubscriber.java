package com.loyal.rx;

import android.content.Context;
import android.text.TextUtils;

import com.loyal.rx.impl.RxSubscriberListener;
import com.loyal.rx.impl.ServerBaseUrlImpl;

/**
 * 适用于执行Retrofit+RxJava与服务器交互
 */
public abstract class BaseRxServerSubscriber<T> extends BaseRxSubscriber<T> implements ServerBaseUrlImpl<T> {

    public abstract void createServer(RetrofitManage retrofitManage);

    @Override
    public String httpOrHttps() {
        return "http";//default
    }

    @Override
    public String defaultPort() {
        return "9080";
    }

    @Override
    public String baseUrl(String clientIp) {
        return RxUtil.getBaseUrl(httpOrHttps(), clientIp, defaultPort(), serverNameSpace());
    }

    public BaseRxServerSubscriber(Context context) {
        this(context, null);
    }

    public BaseRxServerSubscriber(Context context, String ipAdd) {
        this(context, ipAdd, false);
    }

    public BaseRxServerSubscriber(Context context, String ipAdd, boolean showProgressDialog) {
        this(context, ipAdd, 2, showProgressDialog, null);
    }

    public BaseRxServerSubscriber(Context context, String ipAdd, int what) {
        this(context, ipAdd, what, false, null);
    }

    public BaseRxServerSubscriber(Context context, String ipAdd, int what, boolean showProgressDialog, RxSubscriberListener<T> listener) {
        super(context, what, showProgressDialog, listener);
        setUrl(ipAdd);
    }

    @Override
    public BaseRxServerSubscriber<T> setUrl(String ipAdd) {
        if (TextUtils.isEmpty(ipAdd))
            createServer(RetrofitManage.getInstance());
        else
            createServer(RetrofitManage.getInstance(baseUrl(ipAdd)));
        return this;
    }
}