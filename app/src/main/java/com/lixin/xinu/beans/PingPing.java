package com.lixin.xinu.beans;

public class PingPing {
    private String pinglun_name;

    private String pinglun_content;

    public PingPing() {

    }

    public PingPing(String pinglun_name, String pinglun_content) {
        this.pinglun_name = pinglun_name;
        this.pinglun_content = pinglun_content;
    }

    public String getPinglun_name() {
        return pinglun_name;
    }

    public void setPinglun_name(String pinglun_name) {
        this.pinglun_name = pinglun_name;
    }

    public String getPinglun_content() {
        return pinglun_content;
    }

    public void setPinglun_content(String pinglun_content) {
        this.pinglun_content = pinglun_content;
    }
}
