package com.main.hooker.hooker.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.entity.User;

import java.util.List;

public class FollowAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public FollowAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

    }
}
