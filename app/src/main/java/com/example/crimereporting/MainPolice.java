package com.example.crimereporting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.crimereporting.databinding.ActivityMainPoliceBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainPolice extends DrawerBaseActivity {
    ActivityMainPoliceBinding activityMainPoliceBinding;
    CardView cardset,cardstatus,cardcomplaints,cardmissingp,cardlog,cardchat;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainPolice.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainPolice.this,login.class));
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
        activityMainPoliceBinding = ActivityMainPoliceBinding.inflate(getLayoutInflater());
        setContentView(activityMainPoliceBinding.getRoot());
        allocateActivityTitle("Police Dashboard");


        cardset=findViewById(R.id.cardsetp);
        cardstatus=findViewById(R.id.cardstation);
        cardcomplaints=findViewById(R.id.cardcomplainp);
        cardmissingp=findViewById(R.id.cardmissp);
        cardchat=findViewById(R.id.cardhomep);
        cardlog=findViewById(R.id.cardlogp);

        cardchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),populatemissing.class);
                startActivity(intent);
            }
        });

       cardcomplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),complaintsupdate.class);
                startActivity(intent);
            }
        });

       cardset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent=new Intent(getApplicationContext(),print_doc.class);
               startActivity(intent);
           }
       });
       cardmissingp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getApplicationContext(),missingperson.class);
               startActivity(intent);
           }
       });
       cardlog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder=new AlertDialog.Builder(MainPolice.this);
               builder.setTitle("Are you sure you want to quit ?");
               builder.setMessage("Do you want to log out");

               builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       FirebaseAuth.getInstance().signOut();
                       startActivity(new Intent(MainPolice.this,login.class));
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
       cardstatus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getApplicationContext(),station_notification.class);
               startActivity(intent);
           }
       });
    }
}