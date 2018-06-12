package com.main.hooker.hooker.components;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.CommentAdapter;
import com.main.hooker.hooker.entity.Comment;
import com.main.hooker.hooker.entity.User;

import java.util.Arrays;
import java.util.List;

public class CommentFragment extends Fragment {
    private List<Comment> comments;
    private CommentAdapter adapter;
    private RecyclerView recyclerView;
    public CommentFragment() {
        // Required empty public constructor
    }


    // TODO: 真实评论
    public static CommentFragment newInstance(List<Comment> comments) {
        CommentFragment fragment = new CommentFragment();
        //fragment.comments = comments;

        User faker = new User();
        faker.username = "Steve Jobs";
        faker.avatar = "hahaha";
        fragment.comments = Arrays.asList(
                new Comment(4.2f, "What do you mean? when you nod your head and you wanna say no", faker),
                new Comment(10.0f, "What do you mean? when you nod your head and you wanna say no",faker)
        );
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler);
        adapter = new CommentAdapter(comments);
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.icon_back).setOnClickListener((v -> getActivity().onBackPressed()));
    }
}
