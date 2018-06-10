package com.main.hooker.hooker.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{
    public int id;
    public String title;
    public String type;
    public String desc;
    public int author_id;
    public User author;
    public String cover_img;
    public int items_count;
    public int view_count;
    public int favor_count;
    public int score_count;
    public int score_total;
    public String create_time;
    public String update_time;

    protected Book(Parcel in) {
        id = in.readInt();
        title = in.readString();
        type = in.readString();
        desc = in.readString();
        author_id = in.readInt();
        cover_img = in.readString();
        items_count = in.readInt();
        view_count = in.readInt();
        favor_count = in.readInt();
        score_count = in.readInt();
        score_total = in.readInt();
        create_time = in.readString();
        update_time = in.readString();
    }

    public Book() {}

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(desc);
        dest.writeInt(author_id);
        dest.writeString(cover_img);
        dest.writeInt(items_count);
        dest.writeInt(view_count);
        dest.writeInt(favor_count);
        dest.writeInt(score_count);
        dest.writeInt(score_total);
        dest.writeString(create_time);
        dest.writeString(update_time);
    }
}
