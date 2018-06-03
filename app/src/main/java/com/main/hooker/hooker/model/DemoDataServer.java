package com.main.hooker.hooker.model;

import com.main.hooker.hooker.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DemoDataServer {
    private static Random random = new Random();
    private int count;

    public DemoDataServer() {
        count = 1;
    }

    public List<Book> genData(int number) {
        List<Book> demo = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Book temp = new Book(Integer.toString(count++));
            temp.setHeight(random.nextInt(300) + 600);
            demo.add(temp);
        }
        return demo;
    }
}
