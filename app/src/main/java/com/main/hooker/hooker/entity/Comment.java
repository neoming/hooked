package com.main.hooker.hooker.entity;

public class Comment {
    public int id;
    public int user_id;
    public int book_id;
    public float score;
    public String content;
    public String create_at;
    public String update_at;
    public User user;
    public Book book;
}
