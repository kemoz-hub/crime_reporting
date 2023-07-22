package com.example.crimereporting;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.crimereporting.databinding.ActivityComplainreportBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class complainreport extends DrawerBaseActivity {
    ActivityComplainreportBinding activityComplainreportBinding;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    EditText mFullName, mEmail, DateofC,TypeofCk,mid;
    AutoCompleteTextView TypeofC;
    Button button,button2;
    ImageView imageView;
    Uri videoUri;
    MediaController mediaController;
    StorageReference matorageref;
    complaintsfirebase complaintsfirebase;

    FirebaseAuth fAuth;
    private FirebaseUser user;
    private DatabaseReference reference1;
    private  String userid;
    RadioGroup genderk;
    RadioButton male,female;


    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityComplainreportBinding = ActivityComplainreportBinding.inflate(getLayoutInflater());
        setContentView(activityComplainreportBinding.getRoot());
        allocateActivityTitle("complaints");



        user= FirebaseAuth.getInstance().getCurrentUser();
        reference1= FirebaseDatabase.getInstance().getReference("users");
        userid=user.getUid();


        final TextView emailTextview=(TextView) findViewById(R.id.mEmail2);
        final TextView fullTextview=(TextView) findViewById(R.id.mFullName2);
        final TextView idTextview=(TextView) findViewById(R.id.id_no1);
        final  TextView pTextview=(TextView) findViewById(R.id.mphone2);


        //retrieving data from firebase and auto filling the users personal credentials
        reference1.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper userprofile=snapshot.getValue(userhelper.class);
                if(userprofile!=null){
                    String email=userprofile.email;
                    String full=userprofile.fullname;
                    String id=userprofile.id;
                    String phone=userprofile.phone;

                    fullTextview.setText(full);
                    idTextview.setText(id);
                    emailTextview.setText(email);
                    pTextview.setText(phone);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(complainreport.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        DateofC = findViewById(R.id.date_picker);
        TypeofC= findViewById(R.id.typeof);
        button = findViewById(R.id.report);
        button2=findViewById(R.id.choose);
        mid=findViewById(R.id.id_no1);
        imageView=findViewById(R.id.imageView5);
        genderk=(RadioGroup) findViewById(R.id.gender);
        male=findViewById(R.id.radioButton);
        female=findViewById(R.id.radioButton2);

        //"Robbery","theft","corruption","car jacking","gender violence","Terrorism","stealing","Homicide"

        //"physical","sexual","emotional","neglect","other"
        String[] location= {"Robbery","theft","corruption","car jacking","gender violence","Terrorism","stealing","Homicide","other"};

        ArrayAdapter<String> ss = new ArrayAdapter<String>(this,R.layout.dropdownlocation,location);
        ss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeofC.setAdapter(ss);

        TypeofC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });

        mediaController=new MediaController(this);
        matorageref= FirebaseStorage.getInstance().getReference("complaints");


        activityComplainreportBinding.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectiImage();
            }
        });


        Calendar calender=Calendar.getInstance();
        final int Year=calender.get(Calendar.YEAR);
        final int month=calender.get(Calendar.MONTH);
        final int Day=calender.get(Calendar.DAY_OF_MONTH);

        //date picker initializing current date,year and month
        DateofC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        complainreport.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,Year,month,Day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-10000);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=year+"/"+month+"/"+dayOfMonth;
                DateofC.setText(date);
            }
        };

     button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             uploadImage();
         }
     });

    }

    private void uploadImage() {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("reporting");
        progressDialog.show();

        FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString()).putFile(videoUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                          if(task.isSuccessful())  {
                              updatecomplain(task.getResult().toString());
                          }
                        }
                    });

                    Toast.makeText(complainreport.this,"Image uploaded",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(complainreport.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

//progress bar showing the progress while reporting the case
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress =100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                progressDialog.setMessage("reporting"+(int)progress+"%");
            }
        });
    }
//searching storage file of image from your device
    private void selectiImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && data !=null && data.getData() !=null)
        {
            videoUri=data.getData();
            getImageInImageView();
        }
    }
//converting image url data to bitmap
    private void getImageInImageView() {
        Bitmap bitmap= null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),videoUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }

    private  void updatecomplain(String url){

        //generating crime no using random numbers from 1000007 to a maximum of 2000007 with some characters included

        final int min=100007;
        final int max=200007;
        final String random=new Random().nextInt(+(max-min)+1)+"KEN"+min+1+"C";

        final TextView emailTextview=(TextView) findViewById(R.id.mEmail2);
        final TextView fullTextview=(TextView) findViewById(R.id.mFullName2);
        final TextView idTextview=(TextView) findViewById(R.id.id_no1);
        final  TextView pTextview=(TextView) findViewById(R.id.mphone2);

        //submitting reported data to the firebase
       // FirebaseDatabase.getInstance().getReference("complaints/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"videouri/").setValue(url);
       // FirebaseDatabase.getInstance().getReference("complaints/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(url);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("complaints");
        reference1 = rootNode.getReference("notification");

        String fullname=fullTextview.getText().toString().trim();
        String email=emailTextview.getText().toString().trim();
        String phone = pTextview.getText().toString().trim();
        String Dateofc=DateofC.getText().toString().trim();
        String Typeofc=TypeofC.getText().toString().trim();
        String id=idTextview.getText().toString().trim();
        String m1=male.getText().toString().trim();
        String m2=female.getText().toString().trim();



        //notification to the reporter and to the nearest police station
        String message="Dear " +fullname+ "  crime reported on " +Typeofc+ " with crime no " +random+ " is  successfully submitted please wait for feedback or contact 911 for more information,We offer maximum assistance to every kenyan";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(complainreport.this)
                .setSmallIcon(R.drawable.chat_icon)
                .setContentTitle("Crime reporting System")
                .setContentText(message)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setLights(Color.WHITE,3000,3000)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            builder.setChannelId("My notification");
        }

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(complainreport.this);
        managerCompat.notify(0,builder.build());


        Intent intent = new Intent(complainreport.this,splashscreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message3","Dear " +fullname+ " The type of crime reported that is " +Typeofc+ " with crime no " +random+ " is  successfully submitted please wait for feedback or contact 911 for more information,We give maximum assistance to every kenyan");

        PendingIntent pendingIntentk =PendingIntent.getActivity(complainreport.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntentk);

        NotificationManager notificationManager=(NotificationManager)complainreport.this. getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());


        citizensnotifications citizensnotifications=new citizensnotifications(message,fullname);
        reference1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(citizensnotifications);

        //if male button is selected then it will getText and submit to the firebase else it will select female
        if (male.isChecked()){
            complaintsfirebase complaintsfirebase=new complaintsfirebase(id,fullname, email, Dateofc, Typeofc,m1,random,url,"pending",phone,"");
            reference.child(random).setValue(complaintsfirebase);
            return;
        }else {
            complaintsfirebase complaintsfirebase = new complaintsfirebase(id, fullname, email, Dateofc, Typeofc, m2, random, url,"pending",phone,"");
            reference.child(random).setValue(complaintsfirebase);

        }

        Toast.makeText(complainreport.this, "submitted successfully", Toast.LENGTH_SHORT).show();
    }
}