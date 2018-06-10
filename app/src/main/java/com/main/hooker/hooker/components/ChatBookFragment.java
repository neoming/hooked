package com.main.hooker.hooker.components;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.entity.BookWrapper;
import com.main.hooker.hooker.entity.Bubble;
import com.main.hooker.hooker.model.BookModel;
import com.main.hooker.hooker.utils.Tool;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public class ChatBookFragment extends Fragment {
    private Context mContext;
    private List<Bubble> bubbles = new ArrayList<>();
    private List<Bubble> bubbleCache = new ArrayList<>();
    private Book book;
    private int mPage = 0;
    private int mNowItem = -1;
    private boolean mHasMore = true;
    private BubbleAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static ChatBookFragment newInstance(Book book) {
        return newInstance(book, 1);
    }

    public static ChatBookFragment newInstance(Book book, int nowPage) {
        ChatBookFragment fragment = new ChatBookFragment();
        fragment.book = book;
        fragment.mPage = nowPage - 1;
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chatbook, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.get().load(book.cover_img).into((ImageView) view.findViewById(R.id.cover));
        view.findViewById(R.id.title).setOnClickListener((v) -> getActivity().getSupportFragmentManager().popBackStack());
        View layout = view.findViewById(R.id.mainLayout);
        mRecyclerView = view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BubbleAdapter(R.layout.item_bubble, bubbles);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        layout.setOnClickListener(v -> getNewBubble());
        mAdapter.setOnItemClickListener((adapter1, view1, position) -> {
            layout.performClick();
        });
        mRecyclerView.setAdapter(mAdapter);
        getNewBubble();
    }

    public void getNewBubble(){
        if(!mHasMore){
            Toast.makeText(getContext(), "There is no more", Toast.LENGTH_LONG).show();
        }
        mNowItem += 1;
        if(bubbleCache.size() > mNowItem){
            mAdapter.addData(bubbleCache.get(mNowItem));
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        } else {
            load(() -> {
                if(bubbleCache.size() > mNowItem && mHasMore){
                    mAdapter.addData(bubbleCache.get(mNowItem));
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                }
            });
        }
    }

    public void load(){
        load(false, null);
    }

    public void load(Runnable callback){
        load(false, callback);
    }

    public void load(boolean backward, Runnable callback){
        if(!backward){
            mPage += 1;
        }else{
            if(mPage<=0) return;
            mPage -= 1;
        }
        new Thread(() -> {
            try {
                ArrayList<Bubble> list = BookModel.getBubbles(book.id, mPage);
                ((Activity) mContext).runOnUiThread(() -> {
                    if(!backward) {
                        if(list==null || list.size()==0){
                            mHasMore = false;
                            Toast.makeText(getContext(), "There is no more", Toast.LENGTH_LONG).show();
                            return;
                        }
                        bubbleCache.addAll(list);
                    }else {
                        if(list==null || list.size()==0){
                            mHasMore = false;
                            Toast.makeText(getContext(), "There is no more", Toast.LENGTH_LONG).show();
                            return;
                        }
                        bubbleCache.addAll(0, list);
                        mNowItem += list.size();
                    }
                    if(callback != null){
                        callback.run();
                    }
                });
            } catch (ApiFailException e) {
                Log.e("api", "load: "+e.getApiResult().msg);
                //  Toast.makeText(getContext(), "Error:" + e.getApiResult().msg, Toast.LENGTH_LONG).show();
            }
        }).start();
    }


    private static class BubbleAdapter extends BaseQuickAdapter<Bubble, BaseViewHolder> {
        public BubbleAdapter(int layoutResId, @Nullable List<Bubble> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Bubble item) {
            Log.d(TAG, "convert: position = " + item.position);
            if (item.position == 0) {
                helper.setText(R.id.character, item.character == null ? "unknown" : item.character.name);
                helper.setText(R.id.content, item.content);
                helper.setGone(R.id.card, true);
                helper.setGone(R.id.card2, false);
            } else if (item.position == 1){
                helper.setText(R.id.character2, item.character == null ? "unknown" : item.character.name);
                helper.setText(R.id.content2, item.content);
                helper.setGone(R.id.card2, true);
                helper.setGone(R.id.card, false);
            } else {
                helper.setText(R.id.text, item.content);
                helper.setGone(R.id.card2, false);
                helper.setGone(R.id.card, false);
            }
        }
    }


}
