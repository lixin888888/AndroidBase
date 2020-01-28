package com.lixin.xinu.beans;

public class WhatDo {
    String wantDo;
    int  currentPosition;

    public WhatDo(String wantDo, int currentPosition) {
        this.wantDo = wantDo;
        this.currentPosition = currentPosition;
    }

    public WhatDo() {

    }


    public String getWantDo() {
        return wantDo;
    }

    public void setWantDo(String wantDo) {
        this.wantDo = wantDo;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
