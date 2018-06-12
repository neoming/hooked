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
import com.main.hooker.hooker.adapter.PlaceCircleAdapter;
import com.main.hooker.hooker.components.SetBookDialog;
import com.main.hooker.hooker.entity.Bubble;
import com.main.hooker.hooker.entity.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CreateActivity extends AppCompatActivity {

    private TextInputEditText inputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        inputEditText = findViewById(R.id.input);
        initEdit();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        RecyclerView circles = findViewById(R.id.circles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        circles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        BubbleAdapter adapter = new BubbleAdapter(new ArrayList<>());
        PlaceCircleAdapter circleAdapter = new PlaceCircleAdapter(R.layout.item_place_circle,
                Arrays.asList(
                        new PlaceCircleAdapter.Place(0),
                        new PlaceCircleAdapter.Place(1),
                        new PlaceCircleAdapter.Place(2)));

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(adapter);
        circles.setAdapter(circleAdapter);
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

    private void initEdit() {
        SetBookDialog dialog = new SetBookDialog();

        findViewById(R.id.edit).setOnClickListener((v -> {
            dialog.show(getSupportFragmentManager(), null);
        }));
    }

    public void finish(View view) {
        finish();
    }
}
