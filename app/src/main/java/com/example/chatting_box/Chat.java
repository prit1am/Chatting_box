package com.example.chatting_box;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatting_box.Model.ChatRoom;
import com.example.chatting_box.Model.UserModel;
import com.example.chatting_box.adapter.RecentChatRecycler;
import com.example.chatting_box.adapter.SearchUserRecycler;
import com.example.chatting_box.utils.Firebase;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;


public class Chat extends Fragment {

    RecyclerView recyclerView;
    RecentChatRecycler adapter;
    


    public Chat() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView=view.findViewById(R.id.recycleView);
        setupRecyclerView();
        return view;
    }

    void setupRecyclerView(){

        Query query= Firebase.allChatroomReference().whereArrayContains("userIds",Firebase.currentUserID()).orderBy("lastmsgtimestmp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ChatRoom> options=new FirestoreRecyclerOptions.Builder<ChatRoom>().setQuery(query,ChatRoom.class).build();

        adapter=new RecentChatRecycler(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager( getContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onStart() {

        if(adapter!=null){
            adapter.startListening();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    public void onResume() {

        if(adapter!=null){
            adapter.startListening();
        }
        super.onResume();
    }
}