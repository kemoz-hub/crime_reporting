package com.example.crimereporting;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimereporting.databinding.ActivityManagecasesBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Managecases extends DrawerBaseActivity {
ActivityManagecasesBinding activityManagecasesBinding;
    assignadapter adapter;
    RecyclerView recyclerView;
    DatabaseReference reference;
    FirebaseUser user;

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
        activityManagecasesBinding = ActivityManagecasesBinding.inflate(getLayoutInflater());
        setContentView(activityManagecasesBinding.getRoot());
        allocateActivityTitle("Assign police/Manage cases");

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
        Query query=rootRef.child("complaints").child(uid);
        recyclerView=findViewById(R.id.assign);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( Managecases.this));

        FirebaseRecyclerOptions<complaintsfirebase> options=
                new FirebaseRecyclerOptions.Builder<complaintsfirebase>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("complaints"),complaintsfirebase.class)
                        .build();


        adapter=new assignadapter(options);
        recyclerView.setAdapter(adapter);
    }
}