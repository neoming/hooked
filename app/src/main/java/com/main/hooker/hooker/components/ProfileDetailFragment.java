package com.main.hooker.hooker.components;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.CollectionAdapter;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.views.FollowActivity;
import com.main.hooker.hooker.views.FollowerActivity;
import com.main.hooker.hooker.views.WorkActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDetailFragment extends Fragment {
    private User user;
    private Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static ProfileDetailFragment newInstance(User user) {
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        fragment.user = user;
        return fragment;
    }

    public ProfileDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        View header = View.inflate(mContext, R.layout.header_profile, null);
        CollectionAdapter adapter = new CollectionAdapter(R.layout.item_work, new ArrayList<>());
        adapter.addHeaderView(header);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        header.findViewById(R.id.follower).setOnClickListener((v -> startActivity(new Intent(mContext, FollowerActivity.class))));
        header.findViewById(R.id.following).setOnClickListener((v -> startActivity(new Intent(mContext, FollowActivity.class))));
        header.findViewById(R.id.works).setOnClickListener((v -> startActivity(new Intent(mContext, WorkActivity.class))));
    }
}
