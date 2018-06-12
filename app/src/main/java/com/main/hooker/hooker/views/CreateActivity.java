package com.main.hooker.hooker.views;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.BubbleAdapter;
import com.main.hooker.hooker.adapter.PlaceCircleAdapter;
import com.main.hooker.hooker.entity.Bubble;
import com.main.hooker.hooker.entity.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateActivity extends AppCompatActivity {
    private List<Character> characters;
    private Character curCharacter;
    private TextView curCharacterName;
    private int curPosition = 0;
    private TextInputEditText inputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // TODO: 把 character 上传到服务器
        Character character1 = new Character();
        Character character2 = new Character();
        character1.name = "Alice";
        character2.name = "John";
        characters = Arrays.asList(character1, character2);
        curCharacter = character1;
        inputEditText = findViewById(R.id.input);
        curCharacterName = findViewById(R.id.character);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        RecyclerView circles = findViewById(R.id.circles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        circles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        // long Click to edit
        BubbleAdapter adapter = new BubbleAdapter(new ArrayList<>());
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Bubble bubble = (Bubble) adapter.getItem(position);
                PopupMenu popupMenu = new PopupMenu(CreateActivity.this, view, bubble.position == 1 ? GravityCompat.END : GravityCompat.START);
                popupMenu.getMenuInflater().inflate(R.menu.edit, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                Toast.makeText(CreateActivity.this, "delete", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.edit:
                                Toast.makeText(CreateActivity.this, "edit", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.changePosition:
                                Toast.makeText(CreateActivity.this, "changePosition", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                return false;
            }
        });




        PlaceCircleAdapter circleAdapter = new PlaceCircleAdapter(R.layout.item_place_circle,
                Arrays.asList(
                        new PlaceCircleAdapter.Place(0),
                        new PlaceCircleAdapter.Place(1),
                        new PlaceCircleAdapter.Place(2)));
        circleAdapter.setOnItemClickListener((adapter1, view, position) -> {
            curPosition = position;
        });

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(adapter);
        circles.setAdapter(circleAdapter);
        int[] order = new int[1];
        order[0] = 0;
        findViewById(R.id.send).setOnClickListener(v -> {
            String content = inputEditText.getText().toString();
            if (content.equals("")) return;
            Bubble bubble = new Bubble();
            bubble.order_id = order[0]++;
            bubble.character = curCharacter;
            bubble.position = curPosition;
            bubble.content = content;
            inputEditText.setText("");
            adapter.addData(bubble);
            Log.d("create", "bubble: " + order[0] + " " + bubble);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });

        findViewById(R.id.edit).setOnClickListener((v -> {
            showSwitchBox();
        }));
    }

    private void showSwitchBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a character");

        List<String> characterNames = new ArrayList<>();
        for (Character character : characters) characterNames.add(character.name);

        builder.setItems(characterNames.toArray(new String[] {}), (dialog, which) -> {
            curCharacter = findCharacterByName(characterNames.get(which));
            curCharacterName.setText(curCharacter.name);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void finish(View view) {
        finish();
    }

    public Character findCharacterByName(String name) {
        for (Character character : characters) {
            if (character.name.equals(name)) {
                return character;
            }
        }
        return null;
    }
}
