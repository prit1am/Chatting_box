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
import com.example.chatting_box.Model.UserModel;
import com.example.chatting_box.R;
import com.example.chatting_box.utils.Android;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SearchUserRecycler extends FirestoreRecyclerAdapter<UserModel,SearchUserRecycler.UserModel>{

    Context context;
    public SearchUserRecycler(@NonNull FirestoreRecyclerOptions<com.example.chatting_box.Model.UserModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModel holder, int position, @NonNull com.example.chatting_box.Model.UserModel model) {
        holder.username_text.setText(model.getUsername());
        holder.phoneno_text.setText(model.getPhone());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ChatActivity.class);
                Android.passUserModel(intent,model);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public UserModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.search_recycle_view,parent,false);
        return new UserModel(view);
    }

    class UserModel extends RecyclerView.ViewHolder{

        TextView username_text;
        TextView phoneno_text;
        ImageView user_profil;

        public UserModel(@NonNull View itemView) {
            super(itemView);

            username_text=itemView.findViewById(R.id.user_text);
            phoneno_text=itemView.findViewById(R.id.phone_text);
            user_profil=itemView.findViewById(R.id.prof_pic);
        }
    }

}
