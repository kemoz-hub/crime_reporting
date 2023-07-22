package com.example.crimereporting;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.crimereporting.databinding.ActivityReportsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Reports extends DrawerBaseActivity {
ListView mylist;
List<complaintsfirebase>complaintsfirebase;
Button generatepdf;

DatabaseReference reference;
    ActivityReportsBinding activityReportsBinding;
    SearchView searchView;
    int pageHeight=1120;
    int pagewidth=792;

    Bitmap bmp,scaledbmp;

    private static final int PERMISSION_REQUEST_CODE=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReportsBinding = ActivityReportsBinding.inflate(getLayoutInflater());
        setContentView(activityReportsBinding.getRoot());
        allocateActivityTitle("Generating reports");

        mylist=findViewById(R.id.mylistview);
        searchView=findViewById(R.id.search_bar);
        complaintsfirebase=new ArrayList<>();

       generatepdf=findViewById(R.id.print);
       bmp= BitmapFactory.decodeResource(getResources(),R.drawable.back_v24);
       scaledbmp=Bitmap.createScaledBitmap(bmp,140,140,false);

       if(checkPermission()){
           Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
       }
       else{
           requestPermission();
       }
       generatepdf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               generatepdf();
           }
       });

        reference= FirebaseDatabase.getInstance().getReference("complaints");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintsfirebase.clear();
                for (DataSnapshot datasnap :snapshot.getChildren()){

                    complaintsfirebase r=datasnap.getValue(complaintsfirebase.class);
                    complaintsfirebase.add(r);

                }
               reportadapter adapter=new reportadapter(Reports.this,complaintsfirebase);
                mylist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reportadapter adapter=new reportadapter(Reports.this,complaintsfirebase);
        mylist.setAdapter(adapter);
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String Text) {

                adapter.getFilter().filter(Text);
                return true;

            }
        });
    }

    private boolean checkPermission() {
        int permission1=ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int permission2=ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        return permission1== PackageManager.PERMISSION_GRANTED && permission2==PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readstorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readstorage) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    private void generatepdf() {
        PdfDocument pdfDocument=new PdfDocument();
        Paint paint=new Paint();
        Paint Title=new Paint();

        PdfDocument.PageInfo mypageinfo=new PdfDocument.PageInfo.Builder(pagewidth,pageHeight,1).create();
        PdfDocument.Page mypage=pdfDocument.startPage(mypageinfo);
        Canvas canvas=mypage.getCanvas();

        canvas.drawBitmap(scaledbmp,56,40,paint);
        Title.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
        Title.setColor(ContextCompat.getColor(this,R.color.magenta));
        Title.setTextSize(15);
        Title.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Nchiru police station",20,20,Title);
        Title.setTextSize(8);
        canvas.drawText("phone no 0798539170",20,40,Title);


        pdfDocument.finishPage(mypage);

        File file=new File(Environment.getExternalStorageDirectory(),"GFG.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "pdf generated successfully", Toast.LENGTH_SHORT).show();
        }
         catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();

    }
}