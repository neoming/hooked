package com.main.hooker.hooker.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.hooker.hooker.entity.Book;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;

public class DataServer {
    private static OkHttpClient client = new OkHttpClient();
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Book.DataBean> genDate(int num) {
        Book book = null;
        try {
            book = mapper.readValue(new URL("http://ds.trealent.com/api/book/all"), Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book.getData();
    }
}
