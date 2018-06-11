package com.main.hooker.hooker.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Bubble;

import java.util.List;

public class BubbleAdapter extends BaseQuickAdapter<Bubble, BaseViewHolder> {
    public BubbleAdapter(int layoutResId, @Nullable List<Bubble> data) {
        super(layoutResId, data);
    }

    public BubbleAdapter() {
        super(R.layout.item_bubble);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bubble item) {
        Log.d(TAG, "convert: position = " + item.position);
        if (item.position == 0) {
            helper.setText(R.id.character, item.character == null ? "unknown" : item.character.name);
            helper.setText(R.id.content, item.content);
            helper.setGone(R.id.card, true);
            helper.setGone(R.id.card2, false);
            helper.setGone(R.id.text, false);
        } else if (item.position == 1){
            helper.setText(R.id.character2, item.character == null ? "unknown" : item.character.name);
            helper.setText(R.id.content2, item.content);
            helper.setGone(R.id.card2, true);
            helper.setGone(R.id.card, false);
            helper.setGone(R.id.text, false);
        } else {
            helper.setGone(R.id.text, true);
            helper.setText(R.id.text, item.content);
            helper.setGone(R.id.card2, false);
            helper.setGone(R.id.card, false);
        }
    }
}