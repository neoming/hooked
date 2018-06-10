package com.main.hooker.hooker.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MomentAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

    public MomentAdapter(int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        Picasso.get().load(item.getAvatar_res()).into((ImageView) helper.getView(R.id.avatar));
        Picasso.get().load(item.getPic_res()).into((ImageView) helper.getView(R.id.photo));
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.num1, String.valueOf(item.getStar_num()));
        helper.setText(R.id.num2, String.valueOf(item.getComment_num()));
    }
}