package com.main.hooker.hooker.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.CollectionAdapter;
import com.main.hooker.hooker.adapter.MomentAdapter;
import com.main.hooker.hooker.components.LoginFragment;
import com.main.hooker.hooker.components.ProfileDetailFragment;
import com.main.hooker.hooker.entity.Message;
import com.main.hooker.hooker.entity.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // 先查看是否有token，cookie？并发送请求，如果正确，则持有token，并进入detail界面
        // 否则进入登录界面
        getSupportFragmentManager().beginTransaction()
                //.add(R.id.fragment, ProfileDetailFragment.newInstance(new User()))
                .add(R.id.fragment, new LoginFragment())
                .commit();

    }

    public void finish(View view) {
        finish();
    }


    public void toProfile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, ProfileDetailFragment.newInstance(new User()))
                .commit();
    }
}
