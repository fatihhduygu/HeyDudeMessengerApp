package com.fatihduygu.heydudeapp.model;

import java.util.Comparator;

public class UserContactModel {
    private String uId;
    private String userContactName;
    private String userContactPhoneNumber;
    private String userProfileUrl;

    public UserContactModel(String userContactName, String userContactPhoneNumber) {
        this.userContactName = userContactName;
        this.userContactPhoneNumber = userContactPhoneNumber;
    }

    public UserContactModel(String uId, String userContactName, String userContactPhoneNumber,String userProfileUrl) {
        this.uId=uId;
        this.userContactName = userContactName;
        this.userContactPhoneNumber = userContactPhoneNumber;
        this.userProfileUrl=userProfileUrl;
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

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public static Comparator<UserContactModel> sortByName = (s1, s2) -> {

        String name1 = s1.getUserContactName();
        String name2 = s2.getUserContactName();

        //ascending order
        return name1.compareTo(name2);

        //descending order
        //return name2.compareTo(name1);

    };
}
