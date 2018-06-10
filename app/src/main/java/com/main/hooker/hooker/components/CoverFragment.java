package com.main.hooker.hooker.components;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.squareup.picasso.Picasso;

public class CoverFragment extends Fragment {

    private Book book;

    private ChatBookFragment chatBookFragment;

    public static CoverFragment newInstance(Book book) {
        CoverFragment fragment = new CoverFragment();
        fragment.book = book;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cover, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.title);
        Picasso.get().load(book.cover_img).into((ImageView) view.findViewById(R.id.cover));
        tv.setText(book.title);
        chatBookFragment = ChatBookFragment.newInstance(book);
        view.setOnClickListener(v ->
        {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_top)
                    .add(R.id.fragment, chatBookFragment)
                    .addToBackStack(null)
                    .commit();
        });
        final Handler handler = new Handler();
    }

}