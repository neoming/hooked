package com.main.hooker.hooker.views;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.FollowAdapter;
import com.main.hooker.hooker.entity.Follow;
import com.main.hooker.hooker.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FollowActivity extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout;
    private FollowAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        List<User> followings = new ArrayList<>();
        refreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 10; i++) {
            followings.add(new User());
        }
        adapter = new FollowAdapter(R.layout.item_follow_user, followings);
        adapter.setEnableLoadMore(true);
        initRefresh();
        initLoadMore();

        View header = View.inflate(this, R.layout.header_add_follow, null);
        adapter.addHeaderView(header);

        recyclerView.setAdapter(adapter);
    }

    private void initLoadMore() {
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(this::loadMore, recyclerView);
    }

    private void initRefresh() {
        refreshLayout.setColorSchemeResources(R.color.japanBlue);
        adapter.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(this, "我会更新0.5秒！", Toast.LENGTH_LONG).show();
            new Thread(()-> {
                try {
                    Thread.sleep(500);
                    runOnUiThread(()->refreshLayout.setRefreshing(false));
                    adapter.setEnableLoadMore(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    private void loadMore() {

    }


    public void finish(View view) {
        finish();
    }
}
