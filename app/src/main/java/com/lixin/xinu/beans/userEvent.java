package com.lixin.xinu.beans;

public class userEvent {
    private String userName;
    private String passWord;

    public userEvent(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public userEvent() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
