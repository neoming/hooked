package com.main.hooker.hooker.views;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.FollowAdapter;
import com.main.hooker.hooker.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FollowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        List<User> followings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            followings.add(new User());
        }
        FollowAdapter adapter = new FollowAdapter(R.layout.item_follow_user, followings);
        View header = View.inflate(this, R.layout.header_add_follow, null);
        adapter.addHeaderView(header);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void finish(View view) {
        finish();
    }
}
