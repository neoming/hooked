package com.main.hooker.hooker.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Character;

import java.util.List;

public class CharacterCircleAdapter extends BaseQuickAdapter<Character, BaseViewHolder> {

    public CharacterCircleAdapter(int layoutResId, @Nullable List<Character> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Character item) {
        helper.setText(R.id.text, "猪");
    }
}
