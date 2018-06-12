package com.main.hooker.hooker.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.entity.Bubble;
import com.main.hooker.hooker.utils.Tool;

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
                Book book = comment.getBook();
                helper.setText(R.id.title, book.title);
                helper.setText(R.id.viewNum, String.valueOf(book.view_count));
                helper.setText(R.id.lovedNum, String.valueOf(book.favor_count));
                String scoreStr = "5.0";
                if(book.score_count > 0){
                    float score = (float) book.score_total / book.score_count;
                    scoreStr = Tool.floatToStr(score);
                }
                helper.setText(R.id.rateNum,
                        scoreStr + "(" + String.valueOf(book.score_count) + ")");
                helper.addOnClickListener(R.id.rate);
                break;
        }

    }

    public static class FooterBook implements MultiItemEntity {
        private Book mBook;
        public FooterBook(Book book){
            mBook = book;
        }

        public Book getBook(){
            return mBook;
        }

        @Override
        public int getItemType() {
            return 1;
        }
    }
}