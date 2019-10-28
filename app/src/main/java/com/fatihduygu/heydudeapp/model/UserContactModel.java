package com.fatihduygu.heydudeapp.model;

public class UserContactModel {
    private String userContactName;
    private String userContactPhoneNumber;

    public UserContactModel(String userContactName, String userContactPhoneNumber) {
        this.userContactName = userContactName;
        this.userContactPhoneNumber = userContactPhoneNumber;
    }


    public String getUserContactName() {
        return userContactName;
    }

    public void setUserContactName(String userContactName) {
        this.userContactName = userContactName;
    }

    public String getUserContactPhoneNumber() {
        return userContactPhoneNumber;
    }

    public void setUserContactPhoneNumber(String userContactPhoneNumber) {
        this.userContactPhoneNumber = userContactPhoneNumber;
    }
}
