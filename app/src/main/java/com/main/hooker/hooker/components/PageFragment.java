package com.main.hooker.hooker.components;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.entity.BookWrapper;
import com.main.hooker.hooker.kits.CustomLoadMore;
import com.main.hooker.hooker.model.BookModel;
import com.main.hooker.hooker.utils.Tool;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.views.ContentActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PageFragment extends Fragment {
    private CoverAdapter adapter;
    private int mType;
    private int mPage = 1;
    private View appbar;
    private View detail;
    private RecyclerView mRecView;
    private List<BookWrapper> mBookWrappers;
    private SmartRefreshLayout refreshLayout;

    public static PageFragment newInstance(int index) {
        PageFragment fragment = new PageFragment();
        fragment.setIndex(index);
        return fragment;
    }

    public void setIndex(int index) {
        mType = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        appbar = getActivity().findViewById(R.id.appbar);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (detail != null)
            detail.animate().alpha(1.0f).setDuration(300).start();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecView = view.findViewById(R.id.recycler);
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this::refresh);
        refreshLayout.setEnableLoadMore(false);
        adapter = new CoverAdapter(R.layout.item_book);
        adapter.setOnLoadMoreListener(this::loadMore, mRecView);
        load();
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setLoadMoreView(new CustomLoadMore());
        adapter.setOnItemClickListener((adapter, view1, position) -> {
            Intent intent = new Intent(getActivity(), ContentActivity.class);
            BookWrapper wrapper = (BookWrapper) adapter.getItem(position);
            Book book = wrapper.book;
            detail = view1.findViewById(R.id.title_container);
            detail.animate().alpha(0.0f).setDuration(300).start();
            if (appbar != null)
                appbar.animate().alpha(0.0f).setDuration(300).start();

            new Handler().postDelayed(() -> {
                intent.putExtra("book", book);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), view1.findViewById(R.id.cover), getString(R.string.transition_name_cover)
                );
                startActivity(intent, options.toBundle());
            }, 300);
        });
        mRecView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecView.setAdapter(adapter);
        mRecView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }


    private void load() {
        load(false, null);
    }

    private void load(boolean more, Runnable callback) {
        if (more) {
            mPage += 1;
        }
        new Thread(() -> {
            try {
                ArrayList<Book> list = BookModel.getList(Tool.indexToType(mType), mPage);
                if(list == null){
                    throw new ApiFailException();
                }
                List<BookWrapper> bookWrappers = new ArrayList<>();
                for (Book book : list) {
                    bookWrappers.add(new BookWrapper(book));
                }
                getActivity().runOnUiThread(() -> {
                    if (more) {
                        if (list != null && list.size() > 0) {
                            mBookWrappers.addAll(bookWrappers);
                            adapter.loadMoreComplete();
                        } else {
                            //Toast.makeText(getContext(), "There is no more", Toast.LENGTH_LONG).show();
                            adapter.loadMoreEnd(true);
                        }
                    } else {
                        mBookWrappers = bookWrappers;
                        adapter.setNewData(mBookWrappers);
                    }
                    if (callback != null) {
                        callback.run();
                    }
                });
            } catch (ApiFailException e) {
                getActivity().runOnUiThread(() -> {
                    if (more) {
                        adapter.loadMoreFail();
                    }
                    if (callback != null) {
                        callback.run();
                    }
                });
                Log.e("api", "load: " + e.getApiResult().msg);
                //  Toast.makeText(getContext(), "Error:" + e.getApiResult().msg, Toast.LENGTH_LONG).show();
            }
        }).start();
    }


    private void loadMore() {
        load(true, null);
    }

    private void refresh(RefreshLayout layout) {
        if(mBookWrappers != null)
            mBookWrappers.clear();
        adapter.notifyDataSetChanged();
        mPage = 1;
        load(false, layout::finishRefresh);

    }

    private static class CoverAdapter extends BaseQuickAdapter<BookWrapper, BaseViewHolder> {

        CoverAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, BookWrapper wrapper) {
            Book item = wrapper.book;
            helper.setText(R.id.title, item.title);
            helper.itemView.getLayoutParams().height = wrapper.height;
            Picasso.get().load(item.cover_img).into((ImageView) helper.getView(R.id.cover));
        }
    }
}
