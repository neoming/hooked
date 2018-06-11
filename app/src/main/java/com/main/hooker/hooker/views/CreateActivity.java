package com.main.hooker.hooker.views;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.BubbleAdapter;
import com.main.hooker.hooker.entity.Bubble;
import com.main.hooker.hooker.entity.Character;

import java.util.ArrayList;
import java.util.Random;

public class CreateActivity extends AppCompatActivity {

    private TextInputEditText inputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        inputEditText = findViewById(R.id.input);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BubbleAdapter adapter = new BubbleAdapter();
        adapter.setNewData(new ArrayList<>());
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(adapter);
        int[] order = new int[1];
        order[0] = 0;
        Random random = new Random(); // 先随机生成三个位置
        Character character = new Character();
        character.name = "王丽苹";

        findViewById(R.id.send).setOnClickListener(v -> {
            String content = inputEditText.getText().toString();
            if (content.equals("")) return;
            Bubble bubble = new Bubble();
            bubble.order_id = order[0]++;
            bubble.character = character;
            bubble.position = random.nextInt(3);
            bubble.content = content;
            inputEditText.setText("");
            adapter.addData(bubble);
            Log.d("create", "bubble: " + order[0] + " " + bubble);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });
    }

    public void finish(View view) {
        finish();
    }
}
