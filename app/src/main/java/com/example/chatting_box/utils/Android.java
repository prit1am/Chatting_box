package com.example.chatting_box.utils;

import android.content.Intent;

import com.example.chatting_box.Model.UserModel;
import com.google.firebase.firestore.auth.User;

public class Android {

    public static void passUserModel(Intent intent, UserModel model){

        intent.putExtra("username", model.getUsername());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("userID",model.getUserID());
    }

    public static UserModel getUserModelFromIntent(Intent intent){

        UserModel userModel=new UserModel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setUserID(intent.getStringExtra("userID"));
        return userModel;
    }
}
