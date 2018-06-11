package com.main.hooker.hooker.components;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.adapter.CharacterDetailAdapter;
import com.main.hooker.hooker.entity.Character;

import java.util.Arrays;

public class SetBookDialog extends DialogFragment {
    private View footer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        footer = inflater.inflate(R.layout.footer_new_character, container, false);
        return inflater.inflate(R.layout.dialog_set_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        CharacterDetailAdapter adapter = new CharacterDetailAdapter(R.layout.item_character, Arrays.asList(new Character(), new Character()));
        recyclerView.setAdapter(adapter);
        adapter.addFooterView(footer);
    }
}
