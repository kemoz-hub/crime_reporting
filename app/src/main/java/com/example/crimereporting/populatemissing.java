package com.example.crimereporting;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimereporting.databinding.ActivityPopulatemissingBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class populatemissing extends DrawerBaseActivity {
    personAdapter adapter;
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
        ActivityPopulatemissingBinding activityPopulatemissingBinding;
        super.onCreate(savedInstanceState);
        activityPopulatemissingBinding = ActivityPopulatemissingBinding.inflate(getLayoutInflater());
        setContentView(activityPopulatemissingBinding.getRoot());
        allocateActivityTitle("missing people");


        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
        Query query=rootRef.child("complaints").child(uid);
        recyclerView=findViewById(R.id.recycleviewPopulate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( populatemissing.this));

        FirebaseRecyclerOptions<missingFire> options=
                new FirebaseRecyclerOptions.Builder<missingFire>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("missing person"),missingFire.class)
                        .build();

        adapter=new personAdapter(options);
        recyclerView.setAdapter(adapter);

    }

}