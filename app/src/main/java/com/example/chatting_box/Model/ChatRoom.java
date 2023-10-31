package com.example.chatting_box.Model;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatRoom {
    String chatroomId;
    List<String> userIds;
    Timestamp lastmsgtimestmp;
    String lastmsgsenderId;

    String lastMessage;

    public ChatRoom() {
    }

    public ChatRoom(String chatroomId, List<String> userIds, Timestamp lastmsgtimestmp, String lastmsgsenderId) {
        this.chatroomId = chatroomId;
        this.userIds = userIds;
        this.lastmsgtimestmp = lastmsgtimestmp;
        this.lastmsgsenderId = lastmsgsenderId;
    }



    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getLastmsgtimestmp() {
        return lastmsgtimestmp;
    }

    public void setLastmsgtimestmp(Timestamp lastmsgtimestmp) {
        this.lastmsgtimestmp = lastmsgtimestmp;
    }

    public String getLastmsgsenderId() {
        return lastmsgsenderId;
    }

    public void setLastmsgsenderId(String lastmsgsenderId) {
        this.lastmsgsenderId = lastmsgsenderId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
