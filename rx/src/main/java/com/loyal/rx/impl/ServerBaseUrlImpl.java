package com.loyal.rx.impl;

import com.loyal.rx.BaseRxServerSubscriber;

public interface ServerBaseUrlImpl<T> {
    String httpOrHttps();//http 或者https

    String serverNameSpace();//访问路径

    /**
     * @param clientIp 客户端配置的IP地址
     * @return http://ip地址:端口/项目访问地址 或 https://ip地址:端口/项目访问地址
     */
    String baseUrl(String clientIp);

    String defaultPort();//端口配置

    /**
     * @param url IP地址或者访问地址
     */
    BaseRxServerSubscriber<T> setUrl(String url);
}
