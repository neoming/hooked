package com.main.hooker.hooker.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.main.hooker.hooker.MainApplication;
import com.main.hooker.hooker.entity.Favor;
import com.main.hooker.hooker.entity.Follow;
import com.main.hooker.hooker.entity.User;
import com.main.hooker.hooker.utils.Gsoner;
import com.main.hooker.hooker.utils.Http;
import com.main.hooker.hooker.utils.State;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.utils.http.ApiResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class UserModel {
    public static boolean hasLogin() throws ApiFailException {
        State state = MainApplication.getState();
        if (state.userHasLogin()) {
            try {
                getMe();
                return true;
            } catch (ApiFailException e) {
                if (e.getApiResult().code == 500) {
                    state.userLogout();
                    return false;
                }
                throw e;
            }
        } else {
            return false;
        }
    }

    public static FormBody.Builder getAuthBodyBuilder() {
        State state = MainApplication.getState();
        return new FormBody.Builder()
                .add("uid", String.valueOf(state.userGetUid()))
                .add("api_token", state.userGetApiToken());
    }

    public static User getMe() throws ApiFailException {
        ApiResult result = Http.post("user/info", getAuthBodyBuilder().build());
        return Gsoner.fromJson(result.data, User.class);
    }

    public static void register(String username, String password, String phone) throws ApiFailException {
        State state = MainApplication.getState();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("phone", phone)
                .build();
        ApiResult result = Http.post("user/register", body);
    }

    public static void login(String username, String password) throws ApiFailException {
        State state = MainApplication.getState();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        ApiResult result = Http.post("user/login", body);
        if(result.data == null){
            throw new ApiFailException();
        }
        JsonObject data = result.data.getAsJsonObject();
        if (data == null || data.getAsJsonPrimitive("uid") == null || data.getAsJsonPrimitive("api_token") == null)
            throw new ApiFailException();
        int uid = data.getAsJsonPrimitive("uid").getAsInt();
        String api_token = data.getAsJsonPrimitive("api_token").getAsString();
        state.userLogin(uid, api_token);
    }

    public static void logout() {
        State state = MainApplication.getState();
        state.userLogout();
    }

    public static User getProfile(int uid) throws ApiFailException {
        ApiResult result = Http.post("user/profile/" + uid, getAuthBodyBuilder().build());
        return Gsoner.fromJson(result.data, User.class);
    }

    public static void follow(int to_uid) throws ApiFailException {
        ApiResult result = Http.post("user/follow",
                getAuthBodyBuilder().
                        add("to_uid", String.valueOf(to_uid))
                        .build()
        );
    }

    public static void unfollow(int to_uid) throws ApiFailException {
        ApiResult result = Http.post("user/unfollow",
                getAuthBodyBuilder().
                        add("to_uid", String.valueOf(to_uid))
                        .build()
        );
    }

    public static ArrayList<Follow> getFollowings(int page) throws ApiFailException {
        ApiResult result = Http.post("user/get_followings?page=" + page, getAuthBodyBuilder().build());
        Type followListType = new TypeToken<ArrayList<Follow>>() {
        }.getType();
        return Gsoner.fromJson(result.data, followListType);
    }

    public static ArrayList<Follow> getFollowedBy(int page) throws ApiFailException {
        ApiResult result = Http.post("user/get_followed_bys?page=" + page, getAuthBodyBuilder().build());
        Type followListType = new TypeToken<ArrayList<Follow>>() {
        }.getType();
        return Gsoner.fromJson(result.data, followListType);
    }

    public static void favor(int book_id) throws ApiFailException {
        ApiResult result = Http.post("user/favor",
                getAuthBodyBuilder().
                        add("book_id", String.valueOf(book_id))
                        .build()
        );
    }

    public static void unfavor(int book_id) throws ApiFailException {
        ApiResult result = Http.post("user/unfavor",
                getAuthBodyBuilder().
                        add("book_id", String.valueOf(book_id))
                        .build()
        );
    }

    public static boolean isFavored(int book_id) throws ApiFailException {
        ApiResult result = Http.post("user/is_favored",
                getAuthBodyBuilder().
                        add("book_id", String.valueOf(book_id))
                        .build()
        );
        if(result.data == null){
            throw new ApiFailException();
        }
        JsonObject data = result.data.getAsJsonObject();
        if (data == null || data.getAsJsonPrimitive("is_favored") == null)
            throw new ApiFailException();
        int is_favored = data.getAsJsonPrimitive("is_favored").getAsInt();
        return is_favored == 1;
    }

    public static ArrayList<Favor> getFavorings(int page) throws ApiFailException {
        ApiResult result = Http.post("user/get_favorings?page=" + page, getAuthBodyBuilder().build());
        Type favorListType = new TypeToken<ArrayList<Favor>>() {
        }.getType();
        return Gsoner.fromJson(result.data, favorListType);
    }

}
