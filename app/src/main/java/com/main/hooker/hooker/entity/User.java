package com.main.hooker.hooker.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.main.hooker.hooker.utils.Gsoner;

public class User implements Parcelable {
    public int id;
    public String username;
    public String phone;
    public String birthday;
    public String avatar;
    public int last_active;
    public String update_time;
    public String create_time;
    public int work_count;
    public Follow followed;
    public int following_count;
    public int followed_by_count;
    public int favoring_count;

    public User() {}

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            String jsonText = in.readString();
            return Gsoner.fromJson(jsonText, User.class);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        String jsonText = Gsoner.toJson(this);
        parcel.writeString(jsonText);
    }
}
