package com.main.hooker.hooker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.main.hooker.hooker.components.PageFragment;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.model.UserModel;
import com.main.hooker.hooker.utils.State;
import com.main.hooker.hooker.utils.Tool;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.views.MomentsActivity;
import com.main.hooker.hooker.views.ProfileActivity;
import com.main.hooker.hooker.views.SearchActivity;
import com.squareup.picasso.Picasso;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private final static int PAGE_NUM = 6;
    ViewPager pager;
    private List<PageFragment> pages = new ArrayList<>();
    private View appbar;

    @Override
    protected void onResume() {
        super.onResume();
        appbar.animate().alpha(1.0f).setDuration(300).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appbar = findViewById(R.id.appbar);
        Picasso.get().load(R.drawable.main_icon).into((ImageView) findViewById(R.id.cover_logo));
        initPage();
        findViewById(R.id.icon_notification).setOnClickListener((v) -> startActivity(new Intent(this, MomentsActivity.class)));
        findViewById(R.id.icon_search).setOnClickListener((v) -> {
            startActivity(new Intent(this, SearchActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        findViewById(R.id.cover_logo).setOnClickListener((v) -> startActivity(new Intent(this, ProfileActivity.class)));
//        MainApplication.getState().userLogout();
//        Log.i("test", MainApplication.getContext() == null ? "null context" : "has context");
//        String test = MainApplication.getState().getMeta("test_meta");
//        Log.i("test", "Test 1: " + test);
//        MainApplication.getState().setMeta("test_meta", "haha!");
//        test = MainApplication.getState().getMeta("test_meta");
//        Log.i("test", "Test 2: " + test);
    }

    private void initPage() {
        for (int i = 0; i < PAGE_NUM; i++) {
            pages.add(PageFragment.newInstance(i));
        }
        pager = findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(PAGE_NUM);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return pages.get(position);
            }

            @Override
            public int getCount() {
                return PAGE_NUM;
            }
        });

        MagicIndicator magicIndicator = findViewById(R.id.indicator);
        magicIndicator.setBackgroundColor(Color.BLACK);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new NavigatorAdapter());
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, pager);
        freshUserAvatar();
    }

    public void freshUserAvatar(){

        new Thread(()->{
            try {
                if(UserModel.hasLogin()){
                    try {
                        User user = UserModel.getMe();
                        if(user == null){
                            return;
                        }
                        runOnUiThread(()->{
                            Toast.makeText(this, "Welcome back, " + user.username + "!", Toast.LENGTH_SHORT).show();
                            CircleImageView imageView = findViewById(R.id.cover_logo);
                            Picasso.get().load(user.avatar)
                                    .placeholder(R.drawable.avatar_placeholder)
                                    .error(R.drawable.avatar_placeholder)
                                    .into(imageView);
                        });
                    } catch (ApiFailException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(()->{
                        Toast.makeText(this, "How about logging in?", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (ApiFailException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private class NavigatorAdapter extends CommonNavigatorAdapter {
        @Override
        public int getCount() {
            return PAGE_NUM;
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText(Tool.indexToType(index).toUpperCase());
            simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
            simplePagerTitleView.setSelectedColor(Color.WHITE);
            simplePagerTitleView.setOnClickListener(v -> pager.setCurrentItem(index));
            return simplePagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            TriangularPagerIndicator indicator = new TriangularPagerIndicator(context);
            indicator.setLineHeight(0);
            indicator.setTriangleHeight(15);
            indicator.setTriangleWidth(22);
            indicator.setLineColor(Color.parseColor("#999999"));
            return indicator;
        }
    }
}
