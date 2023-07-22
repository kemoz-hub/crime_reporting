package com.example.crimereporting;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimereporting.databinding.ActivityComplaintsupdateBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class complaintsupdate extends DrawerBaseActivity {
    ActivityComplaintsupdateBinding activityComplaintsupdateBinding;
    myadapter2 adapter;
    RecyclerView recyclerView;
    DatabaseReference reference1;
    FirebaseUser user;
    TextView assign;
    String userid;



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
        activityComplaintsupdateBinding = ActivityComplaintsupdateBinding.inflate(getLayoutInflater());
        setContentView(activityComplaintsupdateBinding.getRoot());
        allocateActivityTitle("Update complaints");

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
        Query query=rootRef.child("complaints").child(uid);
        recyclerView=findViewById(R.id.recycleview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( complaintsupdate.this));

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference1= FirebaseDatabase.getInstance().getReference("users");
        userid=user.getUid();




        final  TextView pTextview=(TextView) findViewById(R.id.nameassi);


        //retrieving data from firebase and auto filling the users personal credentials
        reference1.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper userprofile=snapshot.getValue(userhelper.class);
                if(userprofile!=null){

                    String full=userprofile.fullname;
                    pTextview.setText(full);

                    FirebaseRecyclerOptions<complaintsfirebase> options=
                            new FirebaseRecyclerOptions.Builder<complaintsfirebase>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference("complaints").orderByChild("assignto").equalTo(full),complaintsfirebase.class)
                                    .build();
                    adapter=new myadapter2(options);
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(complaintsupdate.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseRecyclerOptions<complaintsfirebase> options=
                new FirebaseRecyclerOptions.Builder<complaintsfirebase>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("complaints"),complaintsfirebase.class)
                        .build();



        adapter=new myadapter2(options);
        recyclerView.setAdapter(adapter);
    }
}