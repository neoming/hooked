package com.main.hooker.hooker.components;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Bubble;

import java.util.ArrayList;
import java.util.List;

public class ChatBookFragment extends Fragment {
    private List<Bubble> bubbles = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chatbook, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bubbles.add(new Bubble());
        View layout = view.findViewById(R.id.mainLayout);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BubbleAdapter adapter = new BubbleAdapter(R.layout.item_bubble, bubbles);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        layout.setOnClickListener(v -> adapter.addData(new Bubble()));
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP ) {
                layout.performClick();
            }
            return false;
        });
        recyclerView.setAdapter(adapter);


    }

    private static class BubbleAdapter extends BaseQuickAdapter<Bubble, BaseViewHolder> {

        public BubbleAdapter(int layoutResId, @Nullable List<Bubble> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Bubble item) {

        }
    }
}

