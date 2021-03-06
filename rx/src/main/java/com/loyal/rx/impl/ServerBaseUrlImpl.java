package com.loyal.rx.impl;

import com.loyal.rx.Config;

public interface ServerBaseUrlImpl {
    String httpOrHttps();//http 或者https

    String serverNameSpace();//访问路径

    /**
     * @param clientIp 客户端配置的IP地址
     * @return http://ip地址:端口/项目访问地址 或 https://ip地址:端口/项目访问地址
     */
    String baseUrl(String clientIp);

    /**
     * 端口号范围：0～65535
     *
     * @return 默认端口是9080，具体端口需要自行修改
     */
    String defaultPort();//端口配置

    /**
     * @param url         IP地址或者访问地址
     * @param trustedCert 使用https访问，证书是否受信任
     *                    {@link #httpOrHttps()}
     */
    void setUrl(String url, boolean trustedCert);

    void setUrl(Config config);

    /**
     * 使用https访问,证书是否受信任
     *
     * @return true:证书受信任，流程和http访问方式一样
     * false:不受信任，需要
     * {@link com.loyal.rx.RetrofitManage#getInstance(String, boolean)}
     */
    boolean trustedCert();
}
