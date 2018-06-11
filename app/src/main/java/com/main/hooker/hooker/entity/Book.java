package com.main.hooker.hooker.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.main.hooker.hooker.utils.Gsoner;

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

    public Book() {}

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            String jsonText = in.readString();
            return Gsoner.fromJson(jsonText, Book.class);
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
        String jsonText = Gsoner.toJson(this);
        dest.writeString(jsonText);
    }
}
