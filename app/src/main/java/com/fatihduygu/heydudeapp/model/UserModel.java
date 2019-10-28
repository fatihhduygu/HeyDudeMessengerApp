package com.fatihduygu.heydudeapp.model;

import java.util.Date;

public class UserModel {
    private String userName;
    private String about;
    private String phoneNumber;

    public UserModel(String userName, String about, String phoneNumber, Date registerDate) {
        this.userName = userName;
        this.about = about;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
