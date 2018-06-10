package com.main.hooker.hooker.utils.http;

import com.google.gson.JsonElement;

public class ApiResult {
    public int code;
    public String msg;
    public JsonElement data;

    public ApiResult() {
        this(555, "unknown issues", null);
    }

    public ApiResult(int code, String msg) {
        this(code, msg, null);
    }

    public ApiResult(int code, String msg, JsonElement data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
