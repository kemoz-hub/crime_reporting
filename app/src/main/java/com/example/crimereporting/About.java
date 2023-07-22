package com.example.crimereporting;

import android.os.Bundle;

import com.example.crimereporting.databinding.ActivityAboutBinding;

public class About extends DrawerBaseActivity {
    ActivityAboutBinding activityAboutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(activityAboutBinding.getRoot());
        allocateActivityTitle("About");
    }
}