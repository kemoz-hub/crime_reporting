package com.example.crimereporting;

import android.os.Bundle;

import com.example.crimereporting.databinding.ActivityChatBinding;

public class chat extends DrawerBaseActivity {
ActivityChatBinding activityChatBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatBinding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(activityChatBinding.getRoot());
        allocateActivityTitle("chat");
    }
}