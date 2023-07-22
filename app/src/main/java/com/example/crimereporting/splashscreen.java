package com.example.crimereporting;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.crimereporting.databinding.ActivitySplashscreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class splashscreen extends DrawerBaseActivity {


    ListView mylist;
    List<citizensnotifications> notifyfirebases;
    DatabaseReference reference,reference2;
    ActivitySplashscreenBinding activitySplashscreenBinding;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashscreenBinding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(activitySplashscreenBinding.getRoot());
        allocateActivityTitle("notifications");

        mylist=findViewById(R.id.mylistview);
        notifyfirebases=new ArrayList<>();

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference2=FirebaseDatabase.getInstance().getReference("users");
        String userid=user.getUid();


        final TextView idTextview = (TextView)  findViewById(R.id.not);


        reference2.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper applicantsprofile = snapshot.getValue(userhelper.class);
                if (applicantsprofile != null) {

                    String id = applicantsprofile.fullname;

                    idTextview.setText(id);
                    reference= FirebaseDatabase.getInstance().getReference("notification");
                    reference.orderByChild("name").equalTo(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            notifyfirebases.clear();
                            for (DataSnapshot datasnap :snapshot.getChildren()){

                                citizensnotifications r=datasnap.getValue(citizensnotifications.class);
                                notifyfirebases.add(r);

                            }
                            notifyme adapter=new notifyme(splashscreen.this,notifyfirebases);
                            mylist.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(splashscreen.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


        notifyme adapter=new notifyme(splashscreen.this,notifyfirebases);
        mylist.setAdapter(adapter);
    }
}