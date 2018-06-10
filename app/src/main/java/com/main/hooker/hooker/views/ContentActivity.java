package com.main.hooker.hooker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.components.CoverFragment;
import com.main.hooker.hooker.entity.Book;

public class ContentActivity extends AppCompatActivity {
    CoverFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Book book = getIntent().getExtras().getParcelable("book");
        fragment = CoverFragment.newInstance(book);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, fragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();

    }



}
