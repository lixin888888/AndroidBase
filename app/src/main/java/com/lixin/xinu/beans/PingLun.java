package com.lixin.xinu.beans;

import java.util.List;

public class PingLun {
    private String id;
    private String pinglun_name;
    private String pinglun_id;
    private String pinglun_content;
    private String pinglun_time;
    private List<PingPing> pinglun_pingluns;
    public PingLun() {
    }

    public String getPinglun_name() {
        return pinglun_name;
    }

    public void setPinglun_name(String pinglun_name) {
        this.pinglun_name = pinglun_name;
    }

    public String getPinglun_id() {
        return pinglun_id;
    }

    public void setPinglun_id(String pinglun_id) {
        this.pinglun_id = pinglun_id;
    }

    public String getPinglun_content() {
        return pinglun_content;
    }

    public void setPinglun_content(String pinglun_content) {
        this.pinglun_content = pinglun_content;
    }

    public String getPinglun_time() {
        return pinglun_time;
    }

    public void setPinglun_time(String pinglun_time) {
        this.pinglun_time = pinglun_time;
    }

    public List<PingPing> getPinglun_pingluns() {
        return pinglun_pingluns;
    }

    public void setPinglun_pingluns(List<PingPing> pinglun_pingluns) {
        this.pinglun_pingluns = pinglun_pingluns;
    }
}

