package com.main.hooker.hooker.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Comment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {


    public CommentAdapter(List<Comment> data) {
        super(R.layout.item_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        Picasso.get().load(R.drawable.avatar).into((ImageView) helper.getView(R.id.avatar));
    }

}
