package com.main.hooker.hooker.components;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.main.hooker.hooker.R;


public class CoverFragment extends Fragment {
    private String title;
    private ChatBookFragment book = new ChatBookFragment();
    public static CoverFragment newInstance(String title) {
        CoverFragment fragment = new CoverFragment();
        fragment.title = title;
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
        tv.setText(title);
        view.setOnClickListener(v ->
        {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_top)
                    .add(R.id.fragment, book)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
