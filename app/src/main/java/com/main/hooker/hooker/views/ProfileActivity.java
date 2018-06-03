package com.main.hooker.hooker.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Random random = new Random();
        MessageAdapter adapter = new MessageAdapter(R.layout.item_news, Arrays.asList(
                new Message(R.drawable.avatar_1, R.drawable.photo_1, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_2, R.drawable.photo_2, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_3, R.drawable.photo_3, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_4, R.drawable.photo_4, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_5, R.drawable.photo_5, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_6, R.drawable.photo_6, random.nextInt(10), random.nextInt(20), "xyy", ""),
                new Message(R.drawable.avatar_7, R.drawable.photo_7, random.nextInt(10), random.nextInt(20), "xyy", "")
        ));
        adapter.addHeaderView(View.inflate(this, R.layout.header_profile, null));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void finish(View view) {
        finish();
    }

    private static class MessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

        MessageAdapter(int layoutResId, @Nullable List<Message> data) {
            super(layoutResId, data);

        }

        @Override
        protected void convert(BaseViewHolder helper, Message item) {
            Glide.with(mContext).load(item.getAvatar_res()).into((ImageView) helper.getView(R.id.avatar));
            Glide.with(mContext).load(item.getPic_res()).into((ImageView) helper.getView(R.id.photo));
            helper.setText(R.id.name, item.getName());
            helper.setText(R.id.num1, String.valueOf(item.getStar_num()));
            helper.setText(R.id.num2, String.valueOf(item.getComment_num()));
        }
    }

}
