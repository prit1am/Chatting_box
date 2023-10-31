package com.example.chatting_box.Model;

import com.google.firebase.Timestamp;

public class UserModel {

    private String phone;
    private String Username;
    private Timestamp createdTimeStamp;
    private String userID;

    public UserModel() {
    }

    public UserModel(String phone, String username, Timestamp createdTimeStamp,String userID) {
        this.phone = phone;
        this.Username = username;
        this.createdTimeStamp = createdTimeStamp;
        this.userID=userID;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Timestamp getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }
}
