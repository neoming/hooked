package com.main.hooker.hooker.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.entity.Book;

import java.util.List;

public class WorkAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {
    public WorkAdapter(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {

    }
}
