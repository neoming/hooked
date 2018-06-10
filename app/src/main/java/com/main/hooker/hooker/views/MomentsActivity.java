package com.main.hooker.hooker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.MomentAdapter;
import com.main.hooker.hooker.entity.Message;

import java.util.Arrays;
import java.util.Random;

public class MomentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);
        Random random = new Random();
        findViewById(R.id.icon_back).setOnClickListener((v) -> finish());
        MomentAdapter adapter = new MomentAdapter(R.layout.item_news, Arrays.asList(
                new Message(R.drawable.avatar_1, R.drawable.photo_1, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_2, R.drawable.photo_2, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_3, R.drawable.photo_3, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_4, R.drawable.photo_4, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_5, R.drawable.photo_5, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_6, R.drawable.photo_6, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_7, R.drawable.photo_7, random.nextInt(10), random.nextInt(20), "xyy", "")
        ));
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
