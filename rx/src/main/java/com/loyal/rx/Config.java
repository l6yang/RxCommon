package com.loyal.rx;

public class Config {
    private long connectTimeout = 10L;
    private long readTimeout = 10L;
    private long writeTimeout = 10L;
    /*
     * @param trustedCert https方式访问证书是否受信任
     * @param protocol    哪种协议，如SSL协议或者TLS协议
     */
    private boolean trustedCert;
    private String protocol;
    private String baseUrl;

    public Config() {
    }

    public Config(long connectTimeout, long readTimeout, long writeTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public Config setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public Config setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public Config setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public boolean isTrustedCert() {
        return trustedCert;
    }

    public Config setTrustedCert(boolean trustedCert) {
        this.trustedCert = trustedCert;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public Config setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Config setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public void apply() {
    }
}