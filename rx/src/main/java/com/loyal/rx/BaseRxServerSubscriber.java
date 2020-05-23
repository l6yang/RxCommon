package com.loyal.rx;

import android.content.Context;
import android.text.TextUtils;

import com.loyal.rx.impl.ServerBaseUrlImpl;

/**
 * 适用于执行Retrofit+RxJava与服务器交互
 */
public abstract class BaseRxServerSubscriber<T> extends BaseRxSubscriber<T> implements ServerBaseUrlImpl {
    public BaseRxServerSubscriber(Context context) {
        this(context, null);
    }

    public BaseRxServerSubscriber(Context context, String ipAdd) {
        super(context);
        setUrl(ipAdd, TextUtils.equals("https", httpOrHttps()) && trustedCert());
    }

    public BaseRxServerSubscriber(Context context, int theme, String ipAdd) {
        super(context, theme);
        setUrl(ipAdd, TextUtils.equals("https", httpOrHttps()) && trustedCert());
    }

    public abstract void createServer(RetrofitManage retrofitManage);

    @Override
    public String httpOrHttps() {
        return "http";//default
    }

    /*端口号范围：0～65535*/
    @Override
    public String defaultPort() {
        return "9080";
    }

    /**
     * https方式访问证书是否受信任
     */
    @Override
    public boolean trustedCert() {
        return true;
    }

    @Override
    public String baseUrl(String clientIp) {
        return RxUtil.getBaseUrl(httpOrHttps(), clientIp, defaultPort(), serverNameSpace());
    }

    @Override
    public void setUrl(String ipAdd, boolean trustedCert) {
        if (TextUtils.isEmpty(ipAdd))
            createServer(RetrofitManage.getInstance((String) null));
        else {
            createServer(RetrofitManage.getInstance(baseUrl(ipAdd), trustedCert));
        }
    }

    @Override
    public void setUrl(Config config) {
        createServer(RetrofitManage.getInstance(config));
    }
}
