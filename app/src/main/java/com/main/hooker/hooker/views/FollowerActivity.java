package com.main.hooker.hooker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.FollowAdapter;
import com.main.hooker.hooker.entity.User;

import java.util.ArrayList;
import java.util.List;

public class FollowerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        List<User> followers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            followers.add(new User());
        }
        FollowAdapter adapter = new FollowAdapter(R.layout.item_follow_user, followers);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void finish(View view) {
        finish();
    }
}
