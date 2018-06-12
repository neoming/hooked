package com.main.hooker.hooker.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.entity.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 2018/6/11.
 */

public class BookAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {
    public BookAdapter(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Book item) {
        //helper.setText(,"");
        Log.d(TAG, "convert: book = " + item.title);
        helper.setText(R.id.search_book_title,item.title);
        helper.setText(R.id.search_book_author,"xyy");
        Picasso.get().load(item.cover_img).into((ImageView) helper.getView(R.id.search_book_cover_img));
    }
}
