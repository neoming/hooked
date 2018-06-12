package com.main.hooker.hooker.components;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.hooker.hooker.MainActivity;
import com.main.hooker.hooker.MainApplication;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.CollectionAdapter;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.State;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.views.FollowActivity;
import com.main.hooker.hooker.views.FollowerActivity;
import com.main.hooker.hooker.views.WorkActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileDetailFragment extends Fragment {
    private User mUser;
    private Context mContext;
    private View mHeaderView;
    private ImageView mPopMenuBtn;
    private TextView mNavMenuFollowBtn;
    private CollectionAdapter mColAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static ProfileDetailFragment newInstance(User user) {
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        fragment.mUser = user;
        fragment.setEnterTransition(new Fade(Fade.IN));
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

        setUpNavMenus();

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mHeaderView = View.inflate(mContext, R.layout.header_profile, null);
        mColAdapter = new CollectionAdapter(R.layout.item_work, new ArrayList<>());
        mColAdapter.addHeaderView(mHeaderView);
        recyclerView.setAdapter(mColAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mHeaderView.findViewById(R.id.follower).setOnClickListener((v -> {
            Intent intent = new Intent(mContext, FollowerActivity.class);
            intent.putExtra("user", mUser);
            startActivity(intent);
        }));
        mHeaderView.findViewById(R.id.following).setOnClickListener(v -> {
            Intent intent = new Intent(mContext, FollowActivity.class);
            intent.putExtra("user", mUser);
            startActivity(intent);
        });
        mHeaderView.findViewById(R.id.works).setOnClickListener((v -> startActivity(new Intent(mContext, WorkActivity.class))));
        view.findViewById(R.id.icon_back).setOnClickListener(v -> ((Activity) mContext).finish());
        load();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(mContext, "Resuming!!!", Toast.LENGTH_SHORT).show();
        load();
    }

    public void load(){
        new Thread(()->{
            try {
                if(mUser == null){
                    mUser = UserModel.getMe();
                }
                if(mUser != null){
                    mUser = UserModel.getProfile(mUser.id);
                    getActivity().runOnUiThread(()->{
                        updateInfo(mUser);
                    });
                }
            } catch (ApiFailException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setUpNavMenus(){
        // set up pop up menu
        if(getView() == null){
            return;
        }
        View view = getView();
        mPopMenuBtn = view.findViewById(R.id.popMenu);
        mPopMenuBtn.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.nav_menu, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.logout:
                        UserModel.logout();
                        Toast.makeText(mContext, "logout", Toast.LENGTH_SHORT).show();
                        ((Activity) mContext).finish();
                        break;
                }
                return false;
            });
        });
        mNavMenuFollowBtn = view.findViewById(R.id.follow_btn);
        mNavMenuFollowBtn.setOnClickListener(v->{
            if(mNavMenuFollowBtn.getText().equals("FOLLOW")){
                follow();
            }else{
                unfollow();
            }
            load();
        });
    }



    public void updateInfo(User user){
        if(getView()!=null){
            State state = MainApplication.getState();
            TextView navTitle = getView().findViewById(R.id.navbar_title);
            if(state.userGetUid() == user.id)
                navTitle.setText("My Profile");
            else
                navTitle.setText(user.username + "'s Profile");
        }
        TextView tvUsername = mHeaderView.findViewById(R.id.profile_username);
        TextView tvFullname = mHeaderView.findViewById(R.id.profile_full_name);
        TextView tvMyWorks = mHeaderView.findViewById(R.id.profile_works_count);
        TextView tvFollowedBys = mHeaderView.findViewById(R.id.profile_followed_by_count);
        TextView tvFollowings = mHeaderView.findViewById(R.id.profile_following_count);
        tvUsername.setText(String.format("%s%s", getString(R.string.at_symbol), user.username.toLowerCase()));
        tvFullname.setText(user.username);
        tvMyWorks.setText(String.valueOf(user.work_count));
        tvFollowedBys.setText(String.valueOf(user.followed_by_count));
        tvFollowings.setText(String.valueOf(user.following_count));
        Picasso.get().load(user.avatar)
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.avatar_placeholder)
                .into((ImageView) mHeaderView.findViewById(R.id.profile_user_avatar));
        updateNavMenus(user);
    }

    public void updateNavMenus(User user){
        if(mUser.id == MainApplication.getState().userGetUid()){
            mPopMenuBtn.setVisibility(View.VISIBLE);
            mNavMenuFollowBtn.setVisibility(View.INVISIBLE);
        } else {
            if(user.followed == null){
                mNavMenuFollowBtn.setText("FOLLOW");
            } else {
                mNavMenuFollowBtn.setText("UNFOLLOW");
            }
            mPopMenuBtn.setVisibility(View.INVISIBLE);
            mNavMenuFollowBtn.setVisibility(View.VISIBLE);
        }
    }

    public void follow(){
        new Thread(()->{
            try {
                UserModel.follow(mUser.id);
                getActivity().runOnUiThread(()->{
                    mNavMenuFollowBtn.setText("UNFOLLOW");
                });
            } catch (ApiFailException e) {
                if(e.getApiResult().code == 402){
                    mNavMenuFollowBtn.setText("UNFOLLOW");
                }
                getActivity().runOnUiThread(()->{
                    Toast.makeText(mContext, "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT)
                            .show();
                });
            }
        });
    }

    public void unfollow(){
        new Thread(()->{
            try {
                UserModel.follow(mUser.id);
                getActivity().runOnUiThread(()->{
                    mNavMenuFollowBtn.setText("FOLLOW");
                });
            } catch (ApiFailException e) {
                getActivity().runOnUiThread(()->{
                    if(e.getApiResult().code == 403){
                        mNavMenuFollowBtn.setText("FOLLOW");
                    }
                    Toast.makeText(mContext, "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT)
                            .show();
                });
            }
        });
    }
}
