package com.example.chatting_box;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chatting_box.Model.UserModel;
import com.example.chatting_box.adapter.SearchUserRecycler;
import com.example.chatting_box.utils.Firebase;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchUser extends AppCompatActivity {

    EditText suser;
    RecyclerView recyclerView;
    ImageButton backbtn;
    ImageButton searchbtn;
    SearchUserRecycler searchUserRecycleradap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        suser=findViewById(R.id.editTextText2);
        recyclerView=findViewById(R.id.recycleview);
        backbtn=findViewById(R.id.back);
        searchbtn=findViewById(R.id.imageButton);
        suser.requestFocus();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchUser.this,Home.class);
                startActivity(intent);
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm=suser.getText().toString();
                if(searchTerm.isEmpty()||searchTerm.length()<3){
                    suser.setError("Invalid Username");
                }
                setupSearchRecyclerView(searchTerm);
            }
        });


    }
    void setupSearchRecyclerView(String SearchTerm){

        Query query= Firebase.allUserCollectionReference().whereGreaterThanOrEqualTo("username",SearchTerm);
        FirestoreRecyclerOptions<UserModel> options=new FirestoreRecyclerOptions.Builder<UserModel>().setQuery(query,UserModel.class).build();

        searchUserRecycleradap=new SearchUserRecycler(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager( this));
        recyclerView.setAdapter(searchUserRecycleradap);
        searchUserRecycleradap.startListening();

    }

    @Override
    protected void onStart() {

        if(searchUserRecycleradap!=null){
            searchUserRecycleradap.startListening();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(searchUserRecycleradap!=null){
            searchUserRecycleradap.stopListening();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {

        if(searchUserRecycleradap!=null){
            searchUserRecycleradap.startListening();
        }
        super.onResume();
    }
}