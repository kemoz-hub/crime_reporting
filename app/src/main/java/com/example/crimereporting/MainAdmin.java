package com.example.crimereporting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.crimereporting.databinding.ActivityMainAdminBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainAdmin extends DrawerBaseActivity {
 ActivityMainAdminBinding activityMainAdminBinding;
    CardView cardset,cardpolice,cardcitizen,cardstation,cardlog,cardchat;
    TextView notify,cases,users;
    FirebaseUser user;
    DatabaseReference reference,reference1,reference3;
    FirebaseDatabase rootNode;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainAdmin.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainAdmin.this,login.class));
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainAdminBinding = ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(activityMainAdminBinding.getRoot());
        allocateActivityTitle("Administrators Dashboard");


        cardset=findViewById(R.id.cardsetA);
        cardpolice=findViewById(R.id.cardpolice);
        cardcitizen=findViewById(R.id.cardcitizen);
        cardstation=findViewById(R.id.cardstationA);
        cardlog=findViewById(R.id.cardlogA);
        cardchat=findViewById(R.id.cardchat);
        notify=findViewById(R.id.countnA);
        cases=findViewById(R.id.countcA);
        users=findViewById(R.id.countmA);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("notification");
        reference1=FirebaseDatabase.getInstance().getReference("users");
        reference3=FirebaseDatabase.getInstance().getReference("complaints");

        String userid=user.getUid();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=(int)snapshot.getChildrenCount();
                String usercount=String.valueOf(counter);
                notify.setText(usercount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=(int)snapshot.getChildrenCount();
                String usercount=String.valueOf(counter);
                users.setText(usercount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=(int)snapshot.getChildrenCount();
                String usercount=String.valueOf(counter);
                cases.setText(usercount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        cardstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),policestation.class);
                startActivity(intent);
            }
        });
        cardchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),splashscreen.class);
                startActivity(intent);
            }
        });
        cardcitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),Manageusers.class);
                startActivity(intent);
            }
        });
        cardpolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),Police.class);
                startActivity(intent);
            }
        });
        cardset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),Managecases.class);
                startActivity(intent);
            }
        });
        cardlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),print_doc.class);
                startActivity(intent);
            }
        });


    }

}