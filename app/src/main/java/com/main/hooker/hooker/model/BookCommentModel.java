package com.main.hooker.hooker.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.main.hooker.hooker.entity.Comment;
import com.main.hooker.hooker.utils.Gsoner;
import com.main.hooker.hooker.utils.Http;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.utils.http.ApiResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.RequestBody;

public class BookCommentModel {
    public static void commentBook(int book_id, double score, String content) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("book_id", Integer.toString(book_id))
                .add("score", Double.toString(score))
                .add("comment", content)
                .build();
        ApiResult result = Http.post("book/comment/comment", body);
    }

    public static void editComment(int comment_id, double score, String content) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("comment_id", Integer.toString(comment_id))
                .add("score", Double.toString(score))
                .add("comment", content)
                .build();
        ApiResult result = Http.post("book/comment/edit", body);
    }

    public static void deleteComment(int comment_id) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("comment_id", Integer.toString(comment_id))
                .build();
        ApiResult result = Http.post("book/comment/delete", body);

    }

    public static Comment getComment(int book_id) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("book_id", Integer.toString(book_id))
                .build();
        ApiResult result = Http.post("book/comment/get_comment", body);
        Gson gson = Gsoner.get();
        return gson.fromJson(result.data, Comment.class);
    }

    public static ArrayList<Comment> getCommentList(int book_id, int page) throws ApiFailException {

        ApiResult result = Http.get("book/comment/get_comment_list?book_id=" + String.valueOf(book_id) + "&page=" + String.valueOf(page));
        Gson gson = Gsoner.get();
        Type commentList = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        return gson.fromJson(result.data, commentList);
    }
}