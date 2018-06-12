package com.main.hooker.hooker.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.hooker.hooker.MainApplication;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.FollowAdapter;
import com.main.hooker.hooker.entity.Follow;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.http.ApiFailException;

import java.util.ArrayList;
import java.util.List;

public class FollowActivity extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout;
    private FollowAdapter adapter;
    private RecyclerView recyclerView;
    private List<User> mFollowings = new ArrayList<>();
    private boolean mHasMore = true;
    private int mPage = 0;
    private boolean isLoading = false;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        refreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recycler);
        mUser = getIntent().getExtras().getParcelable("user");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FollowAdapter(R.layout.item_follow_user, mFollowings);
        initRefresh();
        initLoadMore();
        adapter.setOnItemClickListener((adapter, view, position)->{
            User user = (User) adapter.getItem(position);
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        //View header = View.inflate(this, R.layout.header_add_follow, null);
        //adapter.addHeaderView(header);
        recyclerView.setAdapter(adapter);
        initNav();
        load();
    }

    private void initNav() {
        TextView navTitle = findViewById(R.id.navbar_title);
        ImageView addBtn = findViewById(R.id.add_follow_btn);
        if(isUserSelf()){
            navTitle.setText("Following");
            addBtn.setVisibility(View.VISIBLE);
        }else{
            navTitle.setText(mUser.username + "'s Following");
            addBtn.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isUserSelf(){
        return mUser.id == MainApplication.getState().userGetUid();
    }

    private void initLoadMore() {
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(this::loadMore, recyclerView);
    }

    private void initRefresh() {
        refreshLayout.setColorSchemeResources(R.color.japanBlue);
        adapter.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(() -> {
            load(false, ()->{
                refreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            });
        });
    }

    private void loadMore() {
        if(isLoading && !mHasMore) return;
        isLoading = true;
        boolean more = true;
        if(mPage == 0){
            more = false;
        }
        load(more, ()-> isLoading = false);
    }

    private void load() {
        load(false, null);
    }

    private void load(boolean more, Runnable callback) {
        if(!more){
            mPage = 0;
        }
        mPage += 1;
        mHasMore = true;
        new Thread(()->{
            try {
                ArrayList<Follow> list = UserModel.getFollowings(mUser.id, mPage);
                if(list == null)
                    throw new ApiFailException();
                ArrayList<User> userList = new ArrayList<>();
                for (Follow follow : list){
                    if(follow.to_user != null)
                        userList.add(follow.to_user);
                }
                runOnUiThread(()->{
                    if(mPage == 1){
                        adapter.replaceData(userList);
                        adapter.notifyDataSetChanged();
                        adapter.loadMoreComplete();
                    } else {
                        adapter.addData(userList);
                        adapter.loadMoreComplete();
                        if(userList.size() == 0){
                            adapter.loadMoreEnd(true);
                            mHasMore = false;
                        }
                    }
                    if(callback != null)
                        callback.run();
                });
            } catch (ApiFailException e) {
                e.printStackTrace();
                runOnUiThread(()->{
                    if(more){
                        adapter.loadMoreFail();
                    }
                    if(callback != null)
                        callback.run();
                });
            }
        }).start();
    }


    public void finish(View view) {
        finish();
    }
}
