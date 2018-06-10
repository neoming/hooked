package com.main.hooker.hooker.components;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.entity.Bubble;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatBookFragment extends Fragment {
    private List<Bubble> bubbles = new ArrayList<>();
    private Book book;

    public static ChatBookFragment newInstance(Book book) {
        ChatBookFragment fragment = new ChatBookFragment();
        fragment.book = book;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chatbook, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bubbles.add(new Bubble());
        Picasso.get().load(book.cover_img).into((ImageView) view.findViewById(R.id.cover));
        view.findViewById(R.id.title).setOnClickListener((v) -> getActivity().getSupportFragmentManager().popBackStack());
        View layout = view.findViewById(R.id.mainLayout);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BubbleAdapter adapter = new BubbleAdapter(R.layout.item_bubble, bubbles);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        layout.setOnClickListener(v -> adapter.addData(new Bubble()));
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            layout.performClick();
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });
        recyclerView.setAdapter(adapter);
    }

    private static class BubbleAdapter extends BaseQuickAdapter<Bubble, BaseViewHolder> {
        public BubbleAdapter(int layoutResId, @Nullable List<Bubble> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Bubble item) {
            if (item.isLeft()) {
                helper.setText(R.id.character, item.getCharacter());
                helper.setText(R.id.content, item.getContent());
                helper.setGone(R.id.card, true);
                helper.setGone(R.id.card2, false);
            } else {
                helper.setText(R.id.character2, item.getCharacter());
                helper.setText(R.id.content2, item.getContent());
                helper.setGone(R.id.card2, true);
                helper.setGone(R.id.card, false);
            }
        }
    }

}

