package com.main.hooker.hooker.entity;

import java.util.Random;

public class BookWrapper {
    public Book book;
    public int height;
    private static Random random = new Random();

    public BookWrapper(Book book) {
        this.book = book;
        this.height = random.nextInt(200) + 500;
    }
}
