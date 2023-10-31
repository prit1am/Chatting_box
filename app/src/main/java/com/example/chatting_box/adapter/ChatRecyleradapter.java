package com.example.chatting_box.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatting_box.ChatActivity;
import com.example.chatting_box.Model.ChatMessage;
import com.example.chatting_box.Model.ChatMessage;
import com.example.chatting_box.R;
import com.example.chatting_box.utils.Android;
import com.example.chatting_box.utils.Firebase;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatRecyleradapter extends FirestoreRecyclerAdapter<ChatMessage,ChatRecyleradapter.ChatModelViewHolder>{

    Context context;
    public ChatRecyleradapter(@NonNull FirestoreRecyclerOptions<com.example.chatting_box.Model.ChatMessage> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull com.example.chatting_box.Model.ChatMessage model) {

        if(model.getSenderId().equals(Firebase.currentUserID())){
            holder.leftchat.setVisibility(View.GONE);
            holder.rightchat.setVisibility(View.VISIBLE);
            holder.rightchatText.setText(model.getMessage());
        }
        else{
            holder.rightchat.setVisibility(View.GONE);
            holder.leftchat.setVisibility(View.VISIBLE);
            holder.leftchatText.setText(model.getMessage());
        }
        


    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.message_recycler_view,parent,false);
        return new ChatModelViewHolder(view);
    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftchat,rightchat;
        TextView leftchatText,rightchatText;


        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);
            leftchat=itemView.findViewById(R.id.leftchat);
            rightchat=itemView.findViewById(R.id.rightchat);
            leftchatText=itemView.findViewById(R.id.left_chat_text);
            rightchatText=itemView.findViewById(R.id.right_chat_text);


        }
    }

}
