package com.example.crimereporting;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crimereporting.databinding.ActivityMissingpersonBinding;
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
import java.util.UUID;

public class missingperson extends DrawerBaseActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    EditText mFullName, lastseen, DateofC,DOB,mid;
    AutoCompleteTextView location;
    Button button;
    ImageView imageView;
    ImageButton button2;
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
ActivityMissingpersonBinding activityMissingpersonBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMissingpersonBinding = ActivityMissingpersonBinding.inflate(getLayoutInflater());
        setContentView(activityMissingpersonBinding.getRoot());
        allocateActivityTitle("missing person");

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference1= FirebaseDatabase.getInstance().getReference("users");
        userid=user.getUid();

        final TextView idTextview=(TextView) findViewById(R.id.id_noM);

        reference1.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper userprofile=snapshot.getValue(userhelper.class);
                if(userprofile!=null){

                    String id=userprofile.id;

                    idTextview.setText(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(missingperson.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });



        DateofC = findViewById(R.id.yearofM);
        button = findViewById(R.id.reportm);
        button2=findViewById(R.id.choosem);
        mid=findViewById(R.id.id_noM);
        imageView=findViewById(R.id.imageViewM);
        genderk=(RadioGroup) findViewById(R.id.genderm);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        lastseen=findViewById(R.id.lastseenM);
        location=findViewById(R.id.locationM);
        mFullName=findViewById(R.id.mFullNameM);

        String[] locationK= {"kianjai","nchiru","gundune","karumo","miathene"};

        ArrayAdapter<String> ss = new ArrayAdapter<String>(this,R.layout.dropdownlocation,locationK);
        ss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(ss);

        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });

        mediaController=new MediaController(this);
        matorageref= FirebaseStorage.getInstance().getReference("missing person");

        activityMissingpersonBinding.choosem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectiImage();
            }
        });

        //datepicker options
        Calendar calender=Calendar.getInstance();
        final int Year=calender.get(Calendar.YEAR);
        final int month=calender.get(Calendar.MONTH);
        final int Day=calender.get(Calendar.DAY_OF_MONTH);

        DateofC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        missingperson.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                String date=Day+"/"+month+"/"+Year;
                DateofC.setText(date);
            }
        };

        lastseen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog2=new DatePickerDialog(
                        missingperson.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,Year,month,Day);
                datePickerDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog2.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String last=dayOfMonth+"/"+month+"/"+year;
                lastseen.setText(last);
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }
    private void selectiImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
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

                    Toast.makeText(missingperson.this,"Image uploaded",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(missingperson.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }


        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress =100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                progressDialog.setMessage("reporting"+(int)progress+"%");
            }
        });
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



        final TextView idTextview=(TextView) findViewById(R.id.id_noM);

        // FirebaseDatabase.getInstance().getReference("complaints/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"videouri/").setValue(url);
        FirebaseDatabase.getInstance().getReference("missing person/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(url);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("missing person");


        String full=mFullName.getText().toString().trim();
        String loc=location.getText().toString().trim();
        String lasts=lastseen.getText().toString().trim();
        String Dateofc=DateofC.getText().toString().trim();
        String id=idTextview.getText().toString().trim();
        String m1=male.getText().toString().trim();
        String m2=female.getText().toString().trim();

        if (male.isChecked()){
            missingFire missingFiree=new missingFire(id,full,loc,lasts,Dateofc,m1,url);
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(missingFiree);
            return;
        }else {
            missingFire missingFiree=new missingFire(id,full,loc,lasts,Dateofc,m2,url);
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(missingFiree);

        }




        Toast.makeText(missingperson.this, "submitted successfully", Toast.LENGTH_SHORT).show();
    }

}