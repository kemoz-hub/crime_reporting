package com.example.crimereporting;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.crimereporting.databinding.ActivityAnalysesBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class analyses extends DrawerBaseActivity {
ActivityAnalysesBinding activityAnalysesBinding;
ArrayList barArraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAnalysesBinding = ActivityAnalysesBinding.inflate(getLayoutInflater());
        setContentView(activityAnalysesBinding.getRoot());
        allocateActivityTitle("Analysis");


        BarChart barChart=findViewById(R.id.barchat);
        getData();
        BarDataSet barDataSet=new BarDataSet(barArraylist,"Type of crime");
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
    }

    public void getData(){
        barArraylist=new ArrayList();
        reference= FirebaseDatabase.getInstance().getReference("complaints");
        reference.orderByChild("jobcategory").equalTo("economics and statistics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=(int)snapshot.getChildrenCount();
                String usercount=String.valueOf(counter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        barArraylist.add(new BarEntry(2f,10));
        barArraylist.add(new BarEntry(2f,8));
        barArraylist.add(new BarEntry(2f,12));
        barArraylist.add(new BarEntry(3f,18));
        barArraylist.add(new BarEntry(4f,10));
        barArraylist.add(new BarEntry(5f,13));
        barArraylist.add(new BarEntry(6f,16));

    }
}