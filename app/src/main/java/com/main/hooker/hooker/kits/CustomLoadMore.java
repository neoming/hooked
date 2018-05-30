package com.main.hooker.hooker.kits;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.main.hooker.hooker.R;

public class CustomLoadMore extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.view_loadmore;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
