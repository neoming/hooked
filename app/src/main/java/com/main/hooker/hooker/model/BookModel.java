package com.main.hooker.hooker.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.main.hooker.hooker.entity.Book;
import com.main.hooker.hooker.entity.Bubble;
import com.main.hooker.hooker.utils.Gsoner;
import com.main.hooker.hooker.utils.Http;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.utils.http.ApiResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BookModel {
    public static ArrayList<Book> getList(String type) throws ApiFailException {
        return getList(type, 1);
    }

    public static ArrayList<Book> getList(String type, int page) throws ApiFailException {
        ApiResult result = Http.get("book/" + type + "?page=" + String.valueOf(page));
        Gson gson = Gsoner.get();
        Type bookListType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(result.data, bookListType);
    }

    public static ArrayList<Book> getMyWorks(int page) throws ApiFailException {
        ApiResult result = Http.post("edit/my?page=" + page, UserModel.getAuthBodyBuilder().build());
        Gson gson = Gsoner.get();
        Type bookListType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(result.data, bookListType);
    }

    public static ArrayList<Bubble> getBubbles(int book_id, int page) throws ApiFailException {
        ApiResult result = Http.get("book/" + book_id + "?page=" + page);
        Gson gson = Gsoner.get();
        Type bubbleListType = new TypeToken<ArrayList<Bubble>>() {
        }.getType();
        return gson.fromJson(result.data, bubbleListType);
    }


}
