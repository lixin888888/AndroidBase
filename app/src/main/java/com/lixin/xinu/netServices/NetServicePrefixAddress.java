package com.lixin.xinu.netServices;

public enum NetServicePrefixAddress {

    ADMIN("http://192.168.1.110:8080"),
    ES("http://192.168.1.110:8081")

    ;
    String url;

    NetServicePrefixAddress(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
