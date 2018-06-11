package com.main.hooker.hooker.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.main.hooker.hooker.R;
import com.main.hooker.hooker.entity.Comment;
import com.main.hooker.hooker.utils.Gsoner;
import com.main.hooker.hooker.utils.Http;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.utils.http.ApiResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.CompletionException;

import okhttp3.RequestBody;

public class BookCommentModel {
    public static void commentBook(int book_id, double score, String content) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("book_id", Integer.toString(book_id))
                .add("score", Double.toString(score))
                .add("comment", content)
                .build();
        ApiResult result = Http.post("comment/comment", body);
    }

    public static void editComment(int book_id, double score, String content) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("book_id", Integer.toString(book_id))
                .add("score", Double.toString(score))
                .add("comment", content)
                .build();
        ApiResult result = Http.post("comment/edit", body);
    }

    public static void deleteComment(int book_id) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("book_id", Integer.toString(book_id))
                .build();
        ApiResult result = Http.post("comment/delete", body);

    }

    public static Comment getComment(int book_id) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("book_id", Integer.toString(book_id))
                .build();
        ApiResult result = Http.post("comment/get_comment", body);
        return Gsoner.fromJson(result.data, Comment.class);
    }

    public static ArrayList<Comment> getCommentList(int page) throws ApiFailException {
        RequestBody body = UserModel.getAuthBodyBuilder()
                .add("page", Integer.toString(page))
                .build();
        ApiResult result = Http.post("comment/get_my_comment_list", body);
        Type commentList = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        return Gsoner.fromJson(result.data, commentList);
    }
}
