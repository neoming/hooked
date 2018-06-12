package com.main.hooker.hooker.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Bubble;

import java.util.List;

public class BubbleAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    public BubbleAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_bubble);
        addItemType(1, R.layout.footer_rate);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity single) {
        switch (helper.getItemViewType()) {
            case 0:
                Bubble item = (Bubble) single;
                if (item.position == 0) {
                    helper.setText(R.id.character, item.character == null ? "unknown" : item.character.name);
                    helper.setText(R.id.content, item.content);
                    helper.setGone(R.id.card, true);
                    helper.setGone(R.id.card2, false);
                    helper.setGone(R.id.text, false);
                } else if (item.position == 1) {
                    helper.setText(R.id.character2, item.character == null ? "unknown" : item.character.name);
                    helper.setText(R.id.content2, item.content);
                    helper.setGone(R.id.card2, true);
                    helper.setGone(R.id.card, false);
                    helper.setGone(R.id.text, false);
                } else {
                    helper.setGone(R.id.text, true);
                    helper.setText(R.id.text, item.content);
                    helper.setGone(R.id.card2, false);
                    helper.setGone(R.id.card, false);
                }
                break;
            case 1:
                FooterBook comment = (FooterBook) single;
                helper.addOnClickListener(R.id.comment);
                break;
        }

    }

    public static class FooterBook implements MultiItemEntity {

        @Override
        public int getItemType() {
            return 1;
        }
    }
}