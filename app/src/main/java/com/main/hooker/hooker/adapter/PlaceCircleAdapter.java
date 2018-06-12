package com.main.hooker.hooker.adapter;

import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Character;

import java.util.List;

public class PlaceCircleAdapter extends BaseQuickAdapter<PlaceCircleAdapter.Place, BaseViewHolder> {

    public PlaceCircleAdapter(int layoutResId, @Nullable List<Place> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Place item) {
        String placeText = "";
        int colorRes = 0;
        switch (item.position) {
            case 0: placeText = "L"; colorRes = R.color.japanBlue;break;
            case 1: placeText = "R"; colorRes = R.color.japanRed;break;
            case 2: placeText = "M"; colorRes = R.color.japanGray;break;

        }
        helper.setText(R.id.position, placeText);
        ImageView background = helper.getView(R.id.circle);
        background.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, colorRes)));
    }


    public static class Place {
        public int position;

        public Place(int position) {
            this.position = position;
        }
    }
}
