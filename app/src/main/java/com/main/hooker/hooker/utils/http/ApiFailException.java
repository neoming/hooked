package com.main.hooker.hooker.utils.http;

public class ApiFailException extends Exception {
    private ApiResult mApiResult;

    public ApiFailException() {
        this(new ApiResult());
    }

    public ApiFailException(ApiResult apiResult) {
        mApiResult = apiResult;
    }

    public ApiResult getApiResult() {
        return mApiResult;
    }
}
