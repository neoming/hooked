package com.main.hooker.hooker.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public FollowAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        Picasso.get().load(item.avatar)
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.avatar_placeholder)
                .into((ImageView) helper.itemView.findViewById(R.id.cover_logo));
        helper.setText(R.id.username, item.username);
        CardView followBtn = helper.itemView.findViewById(R.id.follow_btn);
        TextView followBtnTitle = followBtn.findViewById(R.id.follow_btn_title);
        followBtnTitle.setText("Unfollow");
        followBtn.setOnClickListener((view)->{
            if(followBtnTitle.getText().equals("Unfollowing...")){
                return;
            }
            followBtnTitle.setText("Unfollowing...");
            if(mContext == null) {
                followBtnTitle.setText("Unfollow");
            }
            Activity activity = (Activity) mContext;
            new Thread(()->{
                try {
                    UserModel.unfollow(item.id);
                    activity.runOnUiThread(()->{
                        remove(helper.getAdapterPosition());
                    });
                } catch (ApiFailException e) {
                    activity.runOnUiThread(()->{
                        followBtnTitle.setText("Unfollow");
                        Toast.makeText(mContext, "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT)
                                .show();
                    });
                }
            }).start();
        });
    }
}
