package com.main.hooker.hooker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.main.hooker.hooker.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        findViewById(R.id.icon_back).setOnClickListener((v) -> finish());
    }
}
