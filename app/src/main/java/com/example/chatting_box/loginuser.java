package com.example.chatting_box;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatting_box.Model.UserModel;
import com.example.chatting_box.utils.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class loginuser extends AppCompatActivity {

    EditText userinput;
    Button letmein;

    String phoneNumber;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuser);
        userinput=findViewById(R.id.editTextText);
        letmein=findViewById(R.id.button3);
        phoneNumber=getIntent().getExtras().getString("phone");
        getUserName();

        letmein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUsername();
            }
        });


    }

    void setUsername(){
        String username=userinput.getText().toString();

        if(username.isEmpty()||username.length()<3){

            userinput.setError("Username length should be at least 3 or more");
            return;
        }
        if(userModel!=null){
            userModel.setUsername(username);
        }
        else{
            userModel=new UserModel(phoneNumber,username, Timestamp.now(),Firebase.currentUserID());
        }

        Firebase.currentUserDetail().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Intent intent=new Intent(loginuser.this,Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        });
    }
    void getUserName(){

        Firebase.currentUserDetail().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    userModel=task.getResult().toObject(UserModel.class);

                    if(userModel!=null){
                        userinput.setText(userModel.getUsername());
                    }
                }

            }
        });

    }


}