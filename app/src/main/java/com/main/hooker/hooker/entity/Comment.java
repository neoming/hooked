package com.main.hooker.hooker.entity;

public class Comment {
    public int id;
    public int user_id;
    public int book_id;
    public float score;
    public String content;
    public String create_time;
    public String update_time;
    public User user;
    public Book book;

    public Comment(float score, String content, User user) {
        this.score = score;
        this.content = content;
        this.user = user;
    }
}
