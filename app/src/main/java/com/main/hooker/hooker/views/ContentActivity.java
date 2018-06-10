package com.main.hooker.hooker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.components.CoverFragment;
import com.main.hooker.hooker.entity.Book;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Book book = getIntent().getExtras().getParcelable("book");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, CoverFragment.newInstance(book))
                .commit();
        getSupportFragmentManager().executePendingTransactions();
    }
}
