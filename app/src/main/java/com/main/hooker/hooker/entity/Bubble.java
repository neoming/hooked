package com.main.hooker.hooker.entity;

public class Bubble {
    public int id;
    public int book_id;
    public int order_id;
    public int type;
    public int position;
    public int character_id;
    public String content;
    public String create_time;
    public String update_time;
    public Character character;

    @Override
    public String toString() {
        return "Bubble{" +
                "position=" + position +
                ", character_id=" + character_id +
                ", content='" + content + '\'' +
                '}';
    }
}
