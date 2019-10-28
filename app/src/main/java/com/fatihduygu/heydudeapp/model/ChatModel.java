package com.fatihduygu.heydudeapp.model;

import com.google.firebase.Timestamp;

public class ChatModel {
    private String userPhoneNumber;
    private String userMessage;
    private Timestamp messageDate;

    public ChatModel(String userPhoneNumber, String userMessage, Timestamp messageDate) {
        this.userPhoneNumber = userPhoneNumber;
        this.userMessage = userMessage;
        this.messageDate = messageDate;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Timestamp getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Timestamp messageDate) {
        this.messageDate = messageDate;
    }
}
