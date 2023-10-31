package com.example.chatting_box;

import static com.example.chatting_box.utils.Android.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatting_box.Model.ChatMessage;
import com.example.chatting_box.Model.ChatRoom;
import com.example.chatting_box.Model.UserModel;
import com.example.chatting_box.adapter.ChatRecyleradapter;
import com.example.chatting_box.adapter.SearchUserRecycler;
import com.example.chatting_box.utils.Android;
import com.example.chatting_box.utils.Firebase;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.sql.Time;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    TextView username;
    EditText messageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;
    ImageView imageView;
    String chatroomId;
    UserModel otherUser;
    ChatRoom chatRoommodel;

    ChatRecyleradapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        otherUser=Android.getUserModelFromIntent(getIntent());
        chatroomId=Firebase.getChatroomId(Firebase.currentUserID(),otherUser.getUserID());
        otherUsername=findViewById(R.id.user);
        otherUsername.setText(otherUser.getUsername());
        messageInput=findViewById(R.id.editTextText3);
        backBtn=findViewById(R.id.back1);
        imageView=findViewById(R.id.prof_pic);
        sendMessageBtn=findViewById(R.id.imageButton2);
        recyclerView=findViewById(R.id.recycle_view);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ChatActivity.this,Home.class);
                startActivity(intent);
            }
        });
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=messageInput.getText().toString().trim();
                if(message.isEmpty())return;

                sendMessagetoUser(message);
            }
        });

        getorCreatechatRoom();
        setUpChatRecyclerView();

    }

    void setUpChatRecyclerView(){

        Query query= Firebase.getChatroomMessageReference(chatroomId).orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ChatMessage> options=new FirestoreRecyclerOptions.Builder<ChatMessage>().setQuery(query,ChatMessage.class).build();

        adapter=new ChatRecyleradapter(options,getApplicationContext());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });


    }
    void sendMessagetoUser(String message){

        chatRoommodel.setLastmsgtimestmp(Timestamp.now());
        chatRoommodel.setLastmsgsenderId(Firebase.currentUserID());
        Firebase.getChatroomReference(chatroomId).set(chatRoommodel);

        ChatMessage chatMessagemodel=new ChatMessage(message,Firebase.currentUserID(), Timestamp.now());
        Firebase.getChatroomMessageReference(chatroomId).add(chatMessagemodel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    messageInput.setText("");
                }
            }
        });

    }

    void getorCreatechatRoom(){
        Firebase.getChatroomReference(chatroomId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    chatRoommodel=task.getResult().toObject(ChatRoom.class);

                    if(chatRoommodel==null){
                        chatRoommodel=new ChatRoom(chatroomId,
                                Arrays.asList(Firebase.currentUserID(),otherUser.getUserID()),
                                Timestamp.now(),
                                "");
                    }
                    Firebase.getChatroomReference(chatroomId).set(chatRoommodel);
                }
            }
        });

    }
}