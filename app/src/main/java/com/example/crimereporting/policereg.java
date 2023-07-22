package com.example.crimereporting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crimereporting.databinding.ActivityPoliceBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class policereg extends DrawerBaseActivity {

    ActivityPoliceBinding activityPoliceBinding;
    EditText mFullName, mEmail, mPassword, mPhone,mconfirm,mid;
    Button button;
    TextView textView3;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPoliceBinding = ActivityPoliceBinding.inflate(getLayoutInflater());
        setContentView(activityPoliceBinding.getRoot());
        allocateActivityTitle("police Registration");

    }
}