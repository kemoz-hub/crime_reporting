package com.example.crimereporting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.crimereporting.databinding.ActivityPoliceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Police extends DrawerBaseActivity {

    ActivityPoliceBinding activityPoliceBinding;
    EditText mFullName, mEmail, mPassword, mPhone,mconfirm,mid;
    Button button,btnRegLogin;
    TextView textView3;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPoliceBinding = ActivityPoliceBinding.inflate(getLayoutInflater());
        setContentView(activityPoliceBinding.getRoot());
        allocateActivityTitle("police registration");
        //child advocate officers Registration


        mFullName = findViewById(R.id.Fullname1);
        mEmail = findViewById(R.id.email1);
        mPassword = findViewById(R.id.password1);
        mconfirm = findViewById(R.id.location1);
        mid = findViewById(R.id.id_no1);
        mPhone = findViewById(R.id.phone1);
        button = findViewById(R.id.buttonl1);



        fAuth = FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");


                String fullname=mFullName.getText().toString().trim();
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String phone=mPhone.getText().toString().trim();
                String confirm=mconfirm.getText().toString().trim();
                String id=mid.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("password is required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError(("password must be>=6 characters"));
                    return;
                }
                if(TextUtils.isEmpty(id)) {
                    mid.setError("please enter id number");
                    return;
                }
                if(mid.length()!=8){
                    mid.setError("please enter valid id number");
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    mPhone.setError("please enter phone number");
                    return;
                }
                if(mPhone.length()!=10){
                    mPhone.setError("please enter valid phone number");
                    return;
                }

                if(TextUtils.isEmpty(fullname)) {
                    mFullName.setError("please enter your full names");
                }

                else {
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String Uid = task.getResult().getUser().getUid();
                                userhelper userhelper = new userhelper(Uid, fullname, email, password, phone, confirm, id, 1, "");
                                reference.child(Uid).setValue(userhelper);
                                Toast.makeText(Police.this, "user created", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(Police.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }


        });

    }
}