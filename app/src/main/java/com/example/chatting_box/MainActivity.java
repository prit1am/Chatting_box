package com.example.chatting_box;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatting_box.utils.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(){
            public void run(){

                try{
                    sleep(4000);
                }
                catch (Exception e){
                    e.printStackTrace();
                    finish();
                }
                finally {
                    if(Firebase.islogedin()){
                        startActivity(new Intent(MainActivity.this,Home.class));
                    }

                    else{
                        Intent intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                    }

                }
            }
        };thread.start();
    }
}