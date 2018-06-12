package com.main.hooker.hooker.components;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.model.BookModel;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoverFragment extends Fragment {

    private Book mBook;

    private ChatBookFragment chatBookFragment;

    private View appbar;
    private View detail;

    private ImageView mFavIcon;

    public static CoverFragment newInstance(Book book) {
        CoverFragment fragment = new CoverFragment();
        fragment.mBook = book;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cover, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatBookFragment = ChatBookFragment.newInstance(mBook);
        detail = view.findViewById(R.id.detail);
        appbar = view.findViewById(R.id.appbar);

        freshStateView();

        CircleImageView imageView = view.findViewById(R.id.cover_logo);
        imageView.setOnClickListener((View image)->{
            getActivity().finishAfterTransition();
        });

        mFavIcon = getView().findViewById(R.id.icon_notification);

        mFavIcon.setOnClickListener(v -> {
            String tag = (String) mFavIcon.getTag();
            if(tag == null){
                return;
            }
            if(tag.equals("lit")){
                tag.equals("unlighting...");
                unlightFavor();
            } else if(tag.equals("unlit")) {
                tag.equals("lighting...");
                lightFavor();
            }
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
        freshState();
        checkIsFavored();
        addViewCount();
    }

    private void setFavorIcon(boolean lit){
        ImageView icon = mFavIcon;
        if(lit){
            icon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.litYellow)));
            icon.setTag("lit");
        } else {
            icon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.white)));
            icon.setTag("unlit");
        }
    }

    private void freshState(){
        new Thread(()->{
            try {
                mBook = BookModel.getOneBook(mBook.id);
                getActivity().runOnUiThread(this::freshStateView);
            } catch (ApiFailException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void freshStateView(){
        if(getView() != null){
            View view= getView();
            TextView tv = view.findViewById(R.id.title);
            Picasso.get().load(mBook.cover_img).into((ImageView) view.findViewById(R.id.cover));
            Picasso.get().load(R.drawable.main_icon).into((ImageView) view.findViewById(R.id.cover_logo));
            tv.setText(mBook.title);
            TextView tvAuthor = view.findViewById(R.id.author);
            tvAuthor.setText(mBook.author == null ? "unknown" : mBook.author.username);
            TextView viewCount = view.findViewById(R.id.viewNum);
            viewCount.setText(String.valueOf(mBook.view_count));
            TextView favoredCount = view.findViewById(R.id.loved);
            favoredCount.setText(String.valueOf(mBook.favor_count));
            TextView desc = view.findViewById(R.id.description);
            desc.setText(mBook.desc);
            checkIsFavored();
        }

    }

    private void checkIsFavored(){
        new Thread(()->{
            try {
                if (UserModel.hasLogin()) {
                    boolean isFavored = UserModel.isFavored(mBook.id);
                    getActivity().runOnUiThread(()->{
                        setFavorIcon(isFavored);
                    });
                }
            } catch (ApiFailException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void addViewCount(){
        new Thread(()->{
            try {
                BookModel.addView(mBook.id);
            } catch (ApiFailException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void lightFavor(){
        new Thread(()->{
            try {
                UserModel.favor(mBook.id);
                getActivity().runOnUiThread(()-> {
                    setFavorIcon(true);
                    freshState();
                });
            } catch (ApiFailException e) {
                e.printStackTrace();
                if(e.getApiResult().code == 402){
                    getActivity().runOnUiThread(()-> setFavorIcon(true));
                } else {
                    getActivity().runOnUiThread(()-> setFavorIcon(false));
                }
                getActivity().runOnUiThread(()->{
                    Toast.makeText(getContext(), "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT)
                            .show();
                });
            }
        }).start();
    }

    private void unlightFavor(){
        new Thread(()->{
            try {
                UserModel.unfavor(mBook.id);
                getActivity().runOnUiThread(()->{
                    setFavorIcon(false);
                    freshState();
                });
            } catch (ApiFailException e) {
                e.printStackTrace();
                if(e.getApiResult().code == 402){
                    getActivity().runOnUiThread(()-> setFavorIcon(false));
                } else {
                    getActivity().runOnUiThread(()-> setFavorIcon(true));
                }
                getActivity().runOnUiThread(()->{
                    Toast.makeText(getContext(), "Failed: " + e.getApiResult().msg, Toast.LENGTH_SHORT)
                            .show();
                });
            }
        }).start();
    }

}