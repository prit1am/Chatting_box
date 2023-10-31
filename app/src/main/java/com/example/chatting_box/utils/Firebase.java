package com.example.chatting_box.utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

public class Firebase {
    public static String currentUserID(){
        return FirebaseAuth.getInstance().getUid();

    }

    public static boolean islogedin(){
        if(currentUserID()!=null){
            return true;
        }
        return false;
    }

    public static DocumentReference currentUserDetail(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserID());
    }

    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }


    public static DocumentReference getChatroomReference(String chatroomId){

        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }
    public static String getChatroomId(String user1,String user2){

        if(user1.hashCode()<user2.hashCode()){
            return user1+"_"+user2;
        }
        else{
            return user2+"_"+user1;
        }
    }

    public static CollectionReference allChatroomReference(){

        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    public  static DocumentReference getOtherUserFromChatRoom(List<String> userIds){

        if(userIds.get(0).equals(Firebase.currentUserID())){
            return allUserCollectionReference().document(userIds.get(1));
        }
        else{
            return allUserCollectionReference().document(userIds.get(0));
        }
    }

    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }


}
