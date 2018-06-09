package com.main.hooker.hooker.components;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.model.BookModel;
import com.main.hooker.hooker.utils.Tool;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.views.ContentActivity;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.kits.CustomLoadMore;
import java.util.ArrayList;


public class PageFragment extends Fragment {
    private CoverAdapter adapter;
    private int mType;
    private int mPage = 1;


    public static PageFragment newInstance(int index) {
        PageFragment fragment = new PageFragment();
        fragment.setIndex(index);
        return fragment;
    }


    public void setIndex(int index){
        mType = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        adapter = new CoverAdapter(R.layout.item_book);
        adapter.setOnLoadMoreListener(this::loadMore, recyclerView);
        load();
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setLoadMoreView(new CustomLoadMore());
        adapter.setOnItemClickListener((adapter, view1, position) -> {
            Intent intent = new Intent(getActivity(), ContentActivity.class);
            Book book = (Book) adapter.getItem(position);
            intent.putExtra("title", book.title);
            intent.putExtra("book_id", book.id);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), new Pair<>(view1, getString(R.string.transition_name_card)));
            startActivity(intent, options.toBundle());
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void load(){
        load(false);
    }

    private void load(boolean more){
        if(more){
            mPage += 1;
        }
        new Thread(() -> {
            try {
                ArrayList<Book> list = BookModel.getList(Tool.indexToType(mType), mPage);
                getActivity().runOnUiThread(() -> {
                    if(more){
                        if(list!=null && list.size()>0){
                            adapter.addData(list);
                            adapter.loadMoreComplete();
                        }else{
                            Toast.makeText(getContext(), "There is no more", Toast.LENGTH_LONG).show();
                            adapter.loadMoreEnd(true);
                        }
                    }else{
                        adapter.setNewData(list);
                    }
                });
            } catch (ApiFailException e) {
                if(more){
                    adapter.loadMoreFail();
                }
                Toast.makeText(getContext(), "Error:" + e.getApiResult().msg, Toast.LENGTH_LONG).show();
            }
        }).start();
    }


    private void loadMore() {
        load(true);
    }


    private static class CoverAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {

        CoverAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Book item) {
            helper.setText(R.id.title, item.title);
            helper.itemView.getLayoutParams().height = 600;
        }
    }
}
