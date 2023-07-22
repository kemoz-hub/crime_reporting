package com.example.crimereporting;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.crimereporting.databinding.ActivityComplainreportBinding;
import com.example.crimereporting.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
 ActivityMainBinding activityMainBinding;
 ActivityComplainreportBinding activityComplainreportBinding;
 DrawerLayout drawerLayout;
 TextView email;
 View kview;
 ImageView image;
 TextView textView;
 FirebaseUser user;
 DatabaseReference reference;
 FirebaseDatabase rootNode;


 @Override
 public void setContentView(View view) {
  drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
  FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
  container.addView(view);
  super.setContentView(drawerLayout);

  Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
  setSupportActionBar(toolbar);




  NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
  kview =navigationView.getHeaderView(0);

  /*email=(TextView)kview.findViewById(R.id.textView5);
  Intent intent = getIntent();
  String str = intent.getStringExtra("message_key");
  email.setText(str);*/

  navigationView.setNavigationItemSelectedListener(this) ;

  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
  drawerLayout.addDrawerListener(toggle);
  toggle.syncState();

  user= FirebaseAuth.getInstance().getCurrentUser();
  reference= FirebaseDatabase.getInstance().getReference("users");
  String userid=user.getUid();

  final ImageView imageView=(ImageView)kview.findViewById(R.id.imageViewN);
  final TextView emailTextview=(TextView)kview. findViewById(R.id.textView5);
  final TextView fullTextview=(TextView)kview. findViewById(R.id.textViewname);
  final TextView idTextview=(TextView)kview. findViewById(R.id.textViewid);

  reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
   @Override
   public void onDataChange(@NonNull DataSnapshot snapshot) {
    userhelper userprofile=snapshot.getValue(userhelper.class);
    if(userprofile!=null){
     String link=userprofile.uri;
     String email=userprofile.email;
     String name=userprofile.fullname;
     String id=userprofile.id;

     try {
      Picasso.get()
              .load(link)
              .into(imageView);
     }
     catch (Exception e){

     }

     emailTextview.setText(email);
     fullTextview.setText("welcome, "+name+" !");
     idTextview.setText(id);
    }
   }

   @Override
   public void onCancelled(@NonNull DatabaseError error) {
    Toast.makeText(DrawerBaseActivity.this, "something wrong happened", Toast.LENGTH_SHORT).show();
   }
  });

 }


 @Override
 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
  drawerLayout.closeDrawer(GravityCompat.START);
  switch(item.getItemId()) {
   case R.id.profile:
    startActivity(new Intent(this, Profile.class));
    break;
   case R.id.about:
    startActivity(new Intent(this, About.class));
    break;
   case R.id.whatsapp:
    Intent intent=new Intent();
    intent.setPackage("com.whatsapp");
    if (intent.resolveActivity(getPackageManager())!=null){
     startActivity(intent);
    }
    break;
   case R.id.Home:
    String uid2=FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase2=FirebaseDatabase.getInstance();
    firebaseDatabase2.getReference().child("users").child(uid2).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot snapshot) {

      int usertype = snapshot.getValue(Integer.class);


      if (usertype == 0) {

       Intent intent4 = new Intent(getApplicationContext(),MainActivity.class);
       startActivity(intent4);
      }
      if (usertype == 1) {
       Intent intent4 = new Intent(getApplicationContext(),MainPolice.class);
       startActivity(intent4);
      }


      if (usertype == 2) {
       Intent intent4 = new Intent(getApplicationContext(), MainAdmin.class);
       startActivity(intent4);
      }

     }

     @Override
     public void onCancelled(@NonNull DatabaseError error) {

     }

    });
    break;
   case R.id.analysis:
    String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    firebaseDatabase.getReference().child("users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot snapshot) {

      int usertype = snapshot.getValue(Integer.class);


      if (usertype == 0) {
       AlertDialog.Builder builder=new AlertDialog.Builder(DrawerBaseActivity.this);
       builder.setTitle("Not permitted to access this services");
       builder.setMessage("please contact administrator ");
       builder.show();
      }
      if (usertype == 1) {
       Intent intent4 = new Intent(getApplicationContext(), analyses.class);
       startActivity(intent4);
      }


      if (usertype == 2) {
       Intent intent4 = new Intent(getApplicationContext(), analyses.class);
       startActivity(intent4);
      }

     }

     @Override
     public void onCancelled(@NonNull DatabaseError error) {

     }

    });
    break;
   case R.id.facebook:
    Intent intent1;
    intent1 = new Intent();
    intent1.setPackage("https://www.facebook.com");
    if (intent1.resolveActivity(getPackageManager())!=null){
     startActivity(intent1);
    }
    break;
  }
  return false;
 }
 protected void allocateActivityTitle(String titleString) {
  if (getSupportActionBar() != null) {
   getSupportActionBar().setTitle(titleString);
  }
 }

}













