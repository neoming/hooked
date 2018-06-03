package com.main.hooker.hooker.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.components.CoverFragment;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        String title = getIntent().getStringExtra("title");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, CoverFragment.newInstance(title)).commit();
        getSupportFragmentManager().executePendingTransactions();
    }
}
