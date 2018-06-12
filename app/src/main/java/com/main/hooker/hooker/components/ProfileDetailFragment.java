package com.main.hooker.hooker.components;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.MainApplication;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.entity.Favor;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.State;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.views.ContentActivity;
import com.main.hooker.hooker.views.FollowActivity;
import com.main.hooker.hooker.views.FollowerActivity;
import com.main.hooker.hooker.views.WorkActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ProfileDetailFragment extends Fragment {
    private User mUser;
    private Context mContext;
    private View mHeaderView;
    private BookCollectAdapter mColAdapter;
    private ImageView mPopMenuBtn;
    private TextView mNavMenuFollowBtn;
    private ArrayList<Book> mMyFavorings = new ArrayList<Book>();

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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mHeaderView = View.inflate(mContext, R.layout.header_profile, null);

        mColAdapter = new BookCollectAdapter(mMyFavorings);
        mColAdapter.addHeaderView(mHeaderView);
        mColAdapter.setOnItemClickListener((adapter, v, position)->{
            Intent intent = new Intent(getActivity(), ContentActivity.class);
            intent.putExtra("book", (Book)adapter.getItem(position));
            startActivity(intent);
        });
        recyclerView.setAdapter(mColAdapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
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
        updateInfo(mUser);
        load();
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    public void load() {
        new Thread(() -> {
            try {
                if (mUser == null) {
                    mUser = UserModel.getMe();
                }
                if(mUser != null){
                    try {
                        mUser = UserModel.getProfile(mUser.id);
                    }catch (ApiFailException e){
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(()->{
                        updateInfo(mUser);
                    });
                }
            } catch (ApiFailException e) {
                e.printStackTrace();
            }
        }).start();
        loadMyFavorings();
    }

    public void loadMyFavorings(){
        Log.e("test", "hahahahahahahaahaha" + mMyFavorings.size() + "!!!!!!!!!!!");
        new Thread(()->{
            try {
                ArrayList<Favor> list = UserModel.getFavorings(mUser.id, 1);
                ArrayList<Book> bookList = new ArrayList<>();
                for (Favor favor:list) {
                    if(favor.book != null)
                        bookList.add(favor.book);
                }
                getActivity().runOnUiThread(()->{
                    mColAdapter.setNewData(bookList);
                });
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
                mNavMenuFollowBtn.setText("FOLLOWING...");
                follow();
            }else if(mNavMenuFollowBtn.getText().equals("UNFOLLOW")){
                mNavMenuFollowBtn.setText("UNFOLLOWING...");
                unfollow();
            }
        });
    }



    public void updateInfo(User user) {
        if (getView() != null) {
            State state = MainApplication.getState();
            TextView navTitle = getView().findViewById(R.id.navbar_title);
            TextView likeTitle = getView().findViewById(R.id.like_title);
            if (state.userGetUid() == user.id)
                navTitle.setText("My Profile");
            else {
                navTitle.setText(user.username + "'s Profile");
                likeTitle.setText(user.username + "'s Like");
            }
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
                load();
            } catch (ApiFailException e) {
                if(e.getApiResult().code == 402){
                    mNavMenuFollowBtn.setText("UNFOLLOW");
                } else {
                    mNavMenuFollowBtn.setText("FOLLOW");
                }
                getActivity().runOnUiThread(()->{
                    Toast.makeText(mContext, "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT)
                            .show();
                });
            }
        }).start();
    }

    public void unfollow(){
        new Thread(()->{
            try {
                UserModel.unfollow(mUser.id);
                getActivity().runOnUiThread(()->{
                    mNavMenuFollowBtn.setText("FOLLOW");
                });
                load();
            } catch (ApiFailException e) {
                getActivity().runOnUiThread(()->{
                    if(e.getApiResult().code == 403){
                        mNavMenuFollowBtn.setText("FOLLOW");
                    } else {
                        mNavMenuFollowBtn.setText("UNFOLLOW");
                    }
                    Toast.makeText(mContext, "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT)
                            .show();
                });
            }
        }).start();
    }

    private static class BookCollectAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {

        private BookCollectAdapter(@Nullable List<Book> data) {
            super(R.layout.item_book_collection, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Book item) {
            helper.setText(R.id.title, item.title);
            helper.setText(R.id.author, item.author == null ? "unknown" : item.author.username);
            Picasso.get().load(item.cover_img).into((ImageView) helper.getView(R.id.cover));
        }
    }
}
