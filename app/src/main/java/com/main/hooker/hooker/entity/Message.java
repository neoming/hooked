package com.main.hooker.hooker.entity;

public class Message {
    private int avatar_res;
    private int pic_res;
    private int star_num;
    private int comment_num;
    private String name;
    private String place;

    public Message(int avatar_res, int pic_res, int star_num, int comment_num, String name, String place) {
        this.avatar_res = avatar_res;
        this.pic_res = pic_res;
        this.star_num = star_num;
        this.comment_num = comment_num;
        this.name = name;
        this.place = place;
    }

    public Message() {
    }

    public int getAvatar_res() {
        return avatar_res;
    }

    public void setAvatar_res(int avatar_res) {
        this.avatar_res = avatar_res;
    }

    public int getPic_res() {
        return pic_res;
    }

    public void setPic_res(int pic_res) {
        this.pic_res = pic_res;
    }

    public int getStar_num() {
        return star_num;
    }

    public void setStar_num(int star_num) {
        this.star_num = star_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
