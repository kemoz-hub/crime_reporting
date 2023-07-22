package com.example.crimereporting;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class myadapter2 extends FirebaseRecyclerAdapter<complaintsfirebase,myadapter2.MyviewHolder >  {

    public myadapter2(@NonNull FirebaseRecyclerOptions<complaintsfirebase> options) {

        super(options);

    }


    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, int position1, @NonNull complaintsfirebase model)
    {
        holder.Name.setText(model.getPhone());
        holder.idno.setText(model.getId_number());
        holder.Email.setText(model.getEmail());
        holder.gender.setText(model.getFullName());
        holder.DOC.setText(model.getDOC());
        holder.TOC.setText(model.getTOC());
        holder.OBnumber.setText(model.getOBnumber());
        holder.status.setText(model.getStatus());

        Picasso.get()
                .load(model.getVideouri())
                .into(holder.image);

        holder.update.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user;
            DatabaseReference reference,reference2;
            String userid;
            FirebaseDatabase rootNode,rootnode2;
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.Name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updatecomplain))
                        .setExpanded(true, 1000)
                        .setGravity(Gravity.CENTER)
                        .create();



                View v = dialogPlus.getHolderView();

                EditText name = v.findViewById(R.id.txtname);
                EditText name1=v.findViewById(R.id.txtname3);
                EditText obnum = v.findViewById(R.id.txtob);
                TextView status2=v.findViewById(R.id.txtstatus2);
                Spinner status = v.findViewById(R.id.txtstatus);
                Button btn=v.findViewById(R.id.btnupdate);

                name.setText(model.getPhone());
                obnum.setText(String.valueOf(model.getOBnumber()));
                status2.setText(model.getStatus());
                name1.setText(model.getFullName());

                String[] statusk={"confirmed","investigated","taken to action","closed"};

                ArrayAdapter<String> aa=new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item,statusk);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                status.setAdapter(aa);
                status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(v.getContext(), statusk[i],Toast.LENGTH_LONG).show();
                        String text=status.getSelectedItem().toString();
                        status2.setText(text);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                dialogPlus.show();

                btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                       map.put("status",status2.getText().toString());




                            String phone = name.getText().toString();
                            String mess = status2.getText().toString();
                            String obno=obnum.getText().toString();
                            String name=name1.getText().toString();

                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phone, null,"Dear "+name+" your case of OB number "+obno+" has been "+mess+" for more information contact or dial 911", null, null);
                                Toast.makeText(v.getContext(),"SMS sent successfully", Toast.LENGTH_SHORT).show();
                            }catch (Exception e) {

                                Toast.makeText(v.getContext(), "please enter phone and message", Toast.LENGTH_SHORT).show();
                            }

                           /*
                            String message= "Dear "+name+" your case of OB number "+obno+" have been "+mess+" for more information contact or dial 911";
                             NotificationCompat.Builder builder=new NotificationCompat.Builder(v.getContext())
                                .setSmallIcon(R.drawable.chat_icon)
                                .setContentTitle("Crime reporting ")
                                .setContentText(message)
                                .setAutoCancel(true);


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel("My notification3", "My notification3", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = v.getContext().getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                            builder.setChannelId("My notification3");
                        }

                        NotificationManagerCompat managerCompat1=NotificationManagerCompat.from(v.getContext());
                        managerCompat1.notify(0,builder.build());
                        Intent intent = new Intent(v.getContext(),splashscreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("message5","Dear "+name+" your case of OB number "+obno+" have been "+mess+" for more information contact or dial 911");
                        PendingIntent pendingIntentk =PendingIntent.getActivity(v.getContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntentk);
                        NotificationManager notificationManager=(NotificationManager)v.getContext(). getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(0,builder.build());

                        citizensnotifications citizensnotifications=new citizensnotifications(message);
                        reference.setValue(citizensnotifications);*/

                        String message1= "Dear "+name+" your case of OB number "+obno+" have been "+mess+" for more information contact or dial 911";
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("notification");
                        citizensnotifications citizensnotifications1=new citizensnotifications(message1,name);
                        reference.child(name).setValue(citizensnotifications1);


                        String message="Dear incharge of police station, crime reported by "+name+" of OB number "+obno+" have been "+mess+" for more information contact or dial 911 ,We offer maximum assistance to every kenyan";
                      NotificationCompat.Builder builder2=new NotificationCompat.Builder(v.getContext())
                                .setSmallIcon(R.drawable.chat_icon)
                                .setContentTitle("Crime reporting system")
                                .setContentText(message)
                                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                                .setAutoCancel(true);



                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel("My notification2", "My notification2", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = v.getContext().getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                            builder2.setChannelId("My notification2");
                        }

                        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(v.getContext());
                        managerCompat.notify(0,builder2.build());

                        Intent intent2 = new Intent(v.getContext(),station_notification.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent2.putExtra("message4","Dear incharge of police station, crime reported by "+name+" of OB number "+obno+" have been "+mess+" for more information contact or dial 911 ,We offer maximum assistance to every kenyan ");

                        PendingIntent pendingIntentm =PendingIntent.getActivity(v.getContext(),0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                        builder2.setContentIntent(pendingIntentm);

                        NotificationManager notificationManagerp=(NotificationManager)v.getContext(). getSystemService(NOTIFICATION_SERVICE);

                        notificationManagerp.notify(0,builder2.build());


                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("notification");
                        citizensnotifications citizensnotifications=new citizensnotifications(message,"");
                        reference.child(FirebaseAuth.getInstance().getUid()).setValue(citizensnotifications);


                       FirebaseDatabase.getInstance().getReference("complaints")
                                .child(Objects.requireNonNull(getRef(holder.getAdapterPosition()).getKey())).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.status.getContext(), "status  updated successfully", Toast.LENGTH_SHORT).show();
                                        //dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.status.getContext(), "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            }
        });

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.statusentry,parent,false);
        return  new MyviewHolder(view);
    }




    class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView Name,idno,Email,gender,DOC,TOC,OBnumber,status,name2;
        ImageView image;
        Button update;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.textname1);
            idno=itemView.findViewById(R.id.textidno1);
            Email=itemView.findViewById(R.id.textemail1);
            gender=itemView.findViewById(R.id.textgender);
            DOC=itemView.findViewById(R.id.textdate1);
            TOC=itemView.findViewById(R.id.texttype1);
            OBnumber=itemView.findViewById(R.id.textobnumber);
            status=itemView.findViewById(R.id.textstatus);

            update=itemView.findViewById(R.id.buttonupdate);
            image=itemView.findViewById(R.id.imageevi);

        }
    }
}
