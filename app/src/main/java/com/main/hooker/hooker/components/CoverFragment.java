package com.main.hooker.hooker.components;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoverFragment extends Fragment {

    private Book book;

    private ChatBookFragment chatBookFragment;

    private View appbar;
    private View detail;

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
        Picasso.get().load(R.drawable.main_icon).into((ImageView) view.findViewById(R.id.cover_logo));
        tv.setText(book.title);
        TextView tvAuthor = view.findViewById(R.id.author);
        tvAuthor.setText(book.author == null ? "unknown" : book.author.username);
        TextView favoredCount = view.findViewById(R.id.loved);
        favoredCount.setText(String.valueOf(book.favor_count));
        TextView desc = view.findViewById(R.id.description);
        desc.setText(book.desc);

        chatBookFragment = ChatBookFragment.newInstance(book);
        detail = view.findViewById(R.id.detail);
        appbar = view.findViewById(R.id.appbar);



        CircleImageView imageView = view.findViewById(R.id.cover_logo);
        imageView.setOnClickListener((View image)->{
            getActivity().finishAfterTransition();
        });


        detail.animate().alpha(1.0f).setStartDelay(500);
        appbar.animate().alpha(1.0f).setStartDelay(500);
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
        checkIsFavored();
    }

    private void setFavorIcon(boolean lit){
        ImageView icon = getView().findViewById(R.id.icon_notification);
        if(lit){
            icon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.litYellow)));
        } else {
            icon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.white)));
        }
    }

    private void checkIsFavored(){
        new Thread(()->{
            try {
                if (UserModel.hasLogin()) {
                    boolean isFavored = UserModel.isFavored(book.id);
                    getActivity().runOnUiThread(()->{
                        setFavorIcon(isFavored);
                    });
                }
            } catch (ApiFailException e) {
                e.printStackTrace();
            }
        }).start();
    }

}