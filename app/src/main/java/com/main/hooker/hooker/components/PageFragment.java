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
import com.main.hooker.hooker.model.DataServer;
import com.main.hooker.hooker.views.ContentActivity;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.model.Request;
import com.main.hooker.hooker.model.RequestCallBack;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.kits.CustomLoadMore;

import java.util.List;


public class PageFragment extends Fragment {
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
        adapter.setOnLoadMoreListener(this::loadMore, recyclerView);
        adapter.setNewData(DataServer.genDate(0)); // 参数还没什么用
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setLoadMoreView(new CustomLoadMore());
        adapter.setOnItemClickListener((adapter, view1, position) -> {
            Intent intent = new Intent(getActivity(), ContentActivity.class);
            intent.putExtra("title", ((Book.DataBean) adapter.getItem(position)).getTitle());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), new Pair<>(view1, getString(R.string.transition_name_card)));
            startActivity(intent, options.toBundle());
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }


    private void loadMore() {
        new Request(new RequestCallBack() {
            @Override
            public void success(List<Book.DataBean> data) {
                adapter.addData(data);
                adapter.loadMoreComplete();
            }

            @Override
            public void fail(Exception e) {
                adapter.loadMoreFail();
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }).start();
    }


    private static class CoverAdapter extends BaseQuickAdapter<Book.DataBean, BaseViewHolder> {

        CoverAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Book.DataBean item) {
            helper.setText(R.id.title, item.getTitle());
            helper.itemView.getLayoutParams().height = 600;
        }
    }
}
