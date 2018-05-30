package com.main.hooker.hooker;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.entity.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler);

        CoverAdapter adapter = new CoverAdapter(R.layout.item_book, getDemoList(20));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private static class CoverAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {

        public CoverAdapter(int layoutResId, @Nullable List<Book> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Book item) {
            helper.setText(R.id.title, item.getTitle());
            helper.itemView.getLayoutParams().height = item.getHeight();
        }
    }

    private List<Book> getDemoList(int number) {
        List<Book> demo = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Book temp = new Book(Integer.toString(i + 1));
            temp.setHeight(new Random().nextInt(200)+500);
            demo.add(temp);
        }
        return demo;
    }
}
