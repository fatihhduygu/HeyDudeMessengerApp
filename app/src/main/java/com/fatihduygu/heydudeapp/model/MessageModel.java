package com.fatihduygu.heydudeapp.model;

public class MessageModel {
    private String messageId,creator,message,messageDate,receiver;

    public MessageModel(String messageId, String creator, String message, String messageDate, String receiver) {
        this.messageId = messageId;
        this.creator = creator;
        this.message = message;
        this.messageDate = messageDate;
        this.receiver = receiver;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
