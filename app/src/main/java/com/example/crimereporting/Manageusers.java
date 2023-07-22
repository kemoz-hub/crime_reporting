package com.example.crimereporting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimereporting.databinding.ActivityManageusersBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class Manageusers extends DrawerBaseActivity {
RecyclerView recyclerView;
ArrayList<userhelper> list;
DatabaseReference databaseReference;
myadapter adapter;
Context mcontext;
Activity mactivity;
    ActivityManageusersBinding activityManageusersBinding;
//when the back button is clicked it goes back to MainAdmin activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Manageusers.this,MainAdmin.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManageusersBinding = ActivityManageusersBinding.inflate(getLayoutInflater());
        setContentView(activityManageusersBinding.getRoot());
        allocateActivityTitle("Manage Users");

       recyclerView=findViewById(R.id.recycleview);
       recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager( Manageusers.this));

        //it gets all data from userhelper and fills the data to the recyclervier
        FirebaseRecyclerOptions<userhelper> options=
                new FirebaseRecyclerOptions.Builder<userhelper>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("users"),userhelper.class)
                        .build();
        adapter=new myadapter(options);
        recyclerView.setAdapter(adapter);



       /* databaseReference= FirebaseDatabase.getInstance().getReference("users");
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot users:snapshot.getChildren()){
                    userhelper userhelper=users.getValue(userhelper.class);
                    list.add(userhelper);
                }
                adapter=new Myadapter(Manageusers.this,list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                Toast.makeText(Manageusers.this, "successfull", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Manageusers.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}