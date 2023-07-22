package com.example.crimereporting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimereporting.databinding.ActivityStatusBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class status extends DrawerBaseActivity {
ActivityStatusBinding activityStatusBinding;
    citizenstatus adapter;
    RecyclerView recyclerView;
    DatabaseReference reference;
    FirebaseUser user;
    //ArrayList<complaintsfirebase>list;




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(status.this,MainActivity.class));
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
        activityStatusBinding = ActivityStatusBinding.inflate(getLayoutInflater());
        setContentView(activityStatusBinding.getRoot());
        allocateActivityTitle("Status");

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference("complaints");
        recyclerView=findViewById(R.id.recycleview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( status.this));

        /*rootRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintsfirebase complaintsfirebase=snapshot.getValue(complaintsfirebase.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(status.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }


            });*/

        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        String userid=user.getUid();

        final TextView fullTextview=(TextView) findViewById(R.id.textStat);

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper userprofile=snapshot.getValue(userhelper.class);

                if(userprofile!=null){

                    String full=userprofile.id;
                    fullTextview.setText(full);

                    FirebaseRecyclerOptions<complaintsfirebase> options1=
                            new FirebaseRecyclerOptions.Builder<complaintsfirebase>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference("complaints").orderByChild("id_number").equalTo(full),complaintsfirebase.class)
                                    .build();
                    adapter=new citizenstatus(options1);
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(status.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseRecyclerOptions<complaintsfirebase> options1=
                new FirebaseRecyclerOptions.Builder<complaintsfirebase>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("complaints"),complaintsfirebase.class)
                        .build();
        adapter=new citizenstatus(options1);
        recyclerView.setAdapter(adapter);
    }
}