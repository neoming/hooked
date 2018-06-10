package com.main.hooker.hooker.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.main.hooker.hooker.entity.Book;
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
}
