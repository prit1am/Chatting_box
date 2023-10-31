package com.example.chatting_box.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatting_box.ChatActivity;
import com.example.chatting_box.Model.ChatRoom;
import com.example.chatting_box.Model.ChatRoom;
import com.example.chatting_box.Model.UserModel;
import com.example.chatting_box.R;
import com.example.chatting_box.utils.Android;
import com.example.chatting_box.utils.Firebase;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class RecentChatRecycler extends FirestoreRecyclerAdapter<ChatRoom,RecentChatRecycler.ChatRoomViewHolder>{

    Context context;
    public RecentChatRecycler(@NonNull FirestoreRecyclerOptions<ChatRoom> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position, ChatRoom model) {

        Firebase.getOtherUserFromChatRoom(model.getUserIds()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    UserModel otherusermodel= task.getResult().toObject(UserModel.class);
                    holder.username_text.setText(otherusermodel.getUsername());
                    holder.lastmsgText.setText(model.getLastMessage());
                    holder.lastmsgTime.setText(Firebase.timestampToString(model.getLastmsgtimestmp()));

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent=new Intent(context, ChatActivity.class);
                            Android.passUserModel(intent,otherusermodel);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });

                }
            }
        });


    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.recent_chat_row,parent,false);
        return new ChatRoomViewHolder(view);
    }

    class ChatRoomViewHolder extends RecyclerView.ViewHolder{

        TextView username_text;
        TextView lastmsgText;

        TextView lastmsgTime;
        ImageView user_profil;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);

            username_text=itemView.findViewById(R.id.user_text);
            lastmsgText=itemView.findViewById(R.id.lastmsg_text);
            lastmsgTime=itemView.findViewById(R.id.lastmsg_timetext);
            user_profil=itemView.findViewById(R.id.prof_pic);
        }
    }

}
