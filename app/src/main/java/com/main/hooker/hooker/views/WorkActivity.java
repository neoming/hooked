package com.main.hooker.hooker.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.WorkAdapter;
import com.main.hooker.hooker.entity.Book;

import java.util.Arrays;

public class WorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WorkAdapter adapter = new WorkAdapter(R.layout.item_work, Arrays.asList(
                new Book(),
                new Book()
        ));
        recyclerView.setAdapter(adapter);
    }
    public void finish(View view) {
        finish();
    }
}
