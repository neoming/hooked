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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.main.hooker.hooker.components.PageFragment;
import com.main.hooker.hooker.utils.Tool;
import com.main.hooker.hooker.views.NotificationActivity;
import com.main.hooker.hooker.views.ProfileActivity;
import com.main.hooker.hooker.views.SearchActivity;

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

public class MainActivity extends AppCompatActivity {
    private final static int PAGE_NUM = 5;
    private List<PageFragment> pages = new ArrayList<>();
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Glide.with(this).load(R.drawable.avatar).into((ImageView) findViewById(R.id.avatar));
        initPage();
        findViewById(R.id.icon_notification).setOnClickListener((v) -> startActivity(new Intent(this, NotificationActivity.class)));
        findViewById(R.id.icon_search).setOnClickListener((v) -> {
            startActivity(new Intent(this, SearchActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        findViewById(R.id.avatar).setOnClickListener((v) -> startActivity(new Intent(this, ProfileActivity.class)));
        Log.i("test", MainApplication.getContext() == null? "null context" : "has context");
        String test = MainApplication.getState().getMeta("test_meta");
        Log.i("test", "Test 1: "+test);
        MainApplication.getState().setMeta("test_meta", "haha!");
        test = MainApplication.getState().getMeta("test_meta");
        Log.i("test", "Test 2: "+test);
    }

    private void initPage() {
        for (int i = 0; i < PAGE_NUM; i++) {
            pages.add(PageFragment.newInstance(i));
        }
        pager = findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) { return pages.get(position); }
            @Override
            public int getCount() { return PAGE_NUM; }
        });

        MagicIndicator magicIndicator = findViewById(R.id.indicator);
        magicIndicator.setBackgroundColor(Color.BLACK);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new NavigatorAdapter());
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, pager);
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
