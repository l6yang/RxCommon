package com.sample.rx;

public class TranslateBean {
    /**
     * {
     * "dmjc": "vehzt",
     * "dmz": "G",
     * "dmmc": "违法未处理"
     * }
     */
    private Long id;
    private String dmz;
    private String dmmc;
    private String dmjc;
    private String trustedCert;//针对于httpOrHttps

    public TranslateBean(Long id, String dmz, String dmmc, String dmjc,
                         String trustedCert) {
        this.id = id;
        this.dmz = dmz;
        this.dmmc = dmmc;
        this.dmjc = dmjc;
        this.trustedCert = trustedCert;
    }

    public TranslateBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDmz() {
        return this.dmz;
    }

    public void setDmz(String dmz) {
        this.dmz = dmz;
    }

    public String getDmmc() {
        return this.dmmc;
    }

    public void setDmmc(String dmmc) {
        this.dmmc = dmmc;
    }

    public String getDmjc() {
        return this.dmjc;
    }

    public void setDmjc(String dmjc) {
        this.dmjc = dmjc;
    }

    public String getTrustedCert() {
        return this.trustedCert;
    }

    public void setTrustedCert(String trustedCert) {
        this.trustedCert = trustedCert;
    }

}