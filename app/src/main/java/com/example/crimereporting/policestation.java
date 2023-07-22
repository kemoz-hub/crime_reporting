package com.example.crimereporting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crimereporting.databinding.ActivityPolicestationBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class policestation extends DrawerBaseActivity {
ActivityPolicestationBinding activityPolicestationBinding;
    EditText Iid, Sname, incharge, locations,stationsid,sphone;
    Button button;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPolicestationBinding = ActivityPolicestationBinding.inflate(getLayoutInflater());
        setContentView(activityPolicestationBinding.getRoot());
        allocateActivityTitle("police station");

        Iid = findViewById(R.id.inchargeid);
        Sname = findViewById(R.id.stationname);
        incharge = findViewById(R.id.incharge);
        locations= findViewById(R.id.locations);
        stationsid = findViewById(R.id.stationid);
        sphone = findViewById(R.id.sPhone);
        button = findViewById(R.id.buttons);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("police station");


                    String Inchargeid = Iid.getText().toString().trim();
                    String staname = Sname.getText().toString().trim();
                    String inchar = incharge.getText().toString().trim();
                    String locatio = locations.getText().toString().trim();
                    String stationi = stationsid.getText().toString().trim();
                    String phone = sphone.getText().toString().trim();

                    stationfirebase stationfirebase = new stationfirebase(Inchargeid, staname, inchar, locatio, stationi, phone);
                    reference.child(Inchargeid).setValue(stationfirebase);

                    Toast.makeText(policestation.this, "police station added successfully", Toast.LENGTH_SHORT).show();


                }

            });

    }

}