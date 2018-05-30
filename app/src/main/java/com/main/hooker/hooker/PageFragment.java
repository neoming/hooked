package com.main.hooker.hooker;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.kits.CustomLoadMore;

import java.util.List;


public class PageFragment extends Fragment {
    private DemoDataServer server = new DemoDataServer();
    private CoverAdapter adapter;

    public static PageFragment newInstance() {
        PageFragment fragment = new PageFragment();
        return fragment;
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
        adapter.setNewData(server.genData(10));
        adapter.setOnLoadMoreListener(this::loadMore, recyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setLoadMoreView(new CustomLoadMore());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }


    private void loadMore() {
        new Request(new RequestCallBack() {
            @Override
            public void success(List<Book> data) {
                adapter.addData(data);
                adapter.loadMoreComplete();
            }

            @Override
            public void fail(Exception e) {
                adapter.loadMoreFail();
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }, server).start();
    }


    private static class CoverAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {

        CoverAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Book item) {
            helper.setText(R.id.title, item.getTitle());
            helper.itemView.getLayoutParams().height = item.getHeight();
        }
    }
}
