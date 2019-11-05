package com.fatihduygu.heydudeapp.model;

public class ChatObject {
    private String chatId;
    private String friendId;
    private String friendName;
    private String friendPhoneNumber;
    private String friendProfileImageUrl;

    public ChatObject(String chatId, String friendId) {
        this.chatId = chatId;
        this.friendId = friendId;
    }

    public ChatObject(String chatId, String friendId, String friendName, String friendPhoneNumber, String friendProfileImageUrl) {
        this.chatId = chatId;
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendPhoneNumber = friendPhoneNumber;
        this.friendProfileImageUrl = friendProfileImageUrl;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendPhoneNumber() {
        return friendPhoneNumber;
    }

    public void setFriendPhoneNumber(String friendPhoneNumber) {
        this.friendPhoneNumber = friendPhoneNumber;
    }

    public String getFriendProfileImageUrl() {
        return friendProfileImageUrl;
    }

    public void setFriendProfileImageUrl(String friendProfileImageUrl) {
        this.friendProfileImageUrl = friendProfileImageUrl;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
