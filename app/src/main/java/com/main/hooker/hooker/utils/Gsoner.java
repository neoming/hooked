package com.main.hooker.hooker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.JsonTreeReader;
import com.main.hooker.hooker.utils.http.ApiFailException;

import java.lang.reflect.Type;

public class Gsoner {
    private static Gson mGson;

    private static Gson generate() {
        return new GsonBuilder().enableComplexMapKeySerialization()
                .create();
    }

    public static Gson get() {
        if (mGson == null) {
            mGson = generate();
        }
        return mGson;
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        return get().fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return get().fromJson(json, typeOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
        return get().fromJson(json, classOfT);
    }

    public static <T> T fromJson(JsonElement json, Type typeOfT) {
        return get().fromJson(json, typeOfT);
    }

    public static String toJson(Object src){
        return get().toJson(src);
    }
}
