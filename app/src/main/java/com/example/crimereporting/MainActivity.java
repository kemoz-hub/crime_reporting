package com.example.crimereporting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.crimereporting.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends DrawerBaseActivity  {
Activity context;

    CardView cardhome,cardset,cardlog,cardcomplain,cardstatus,cardmiss;
    TextView count1;
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseDatabase rootNode;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,login.class));
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
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        allocateActivityTitle("citizen Dashboard");


        cardhome=findViewById(R.id.cardhome);
        cardset=findViewById(R.id.cardset);
        cardlog=findViewById(R.id.cardlog);
        cardcomplain=findViewById(R.id.cardcomplain);
        cardstatus=findViewById(R.id.cardstatus);
        cardmiss=findViewById(R.id.cardmiss);
        count1=findViewById(R.id.count);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("notification");
        String userid=user.getUid();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=(int)snapshot.getChildrenCount();
                String usercount=String.valueOf(counter);
                count1.setText(usercount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //when cardview home is clicked it opens a ne activity splashscreen
        cardhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=(TextView)kview.findViewById(R.id.textView5);
                Intent intent=new Intent(getApplicationContext(),splashscreen.class);
                startActivity(intent);
                String str = intent.getStringExtra("message_key");
                email.setText(str);
            }
        });
        //when cardview with textname reportcomplaint is clicked it opens a new activity complaintreport
       activityMainBinding.cardcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=(TextView)kview.findViewById(R.id.textView5);
               Intent intent=new Intent(getApplicationContext(),complainreport.class);
                startActivity(intent);
                String str = intent.getStringExtra("message_key");
                email.setText(str);
            }
        });
        cardstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),status.class);
                startActivity(intent);
            }
        });
        cardmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=(TextView)kview.findViewById(R.id.textView5);
                Intent intent=new Intent(getApplicationContext(),missingperson.class);
                startActivity(intent);
                String str = intent.getStringExtra("message_key");
                email.setText(str);
            }
        });
        cardlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you sure you want to quit ?");
                builder.setMessage("Do you want to log out");

                builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this,login.class));
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        cardset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),populatemissing.class);
                startActivity(intent);
            }
        });
    }
}