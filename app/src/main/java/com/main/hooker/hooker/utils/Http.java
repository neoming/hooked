package com.main.hooker.hooker.utils;

import com.google.gson.Gson;
import com.main.hooker.hooker.utils.http.ApiFailException;
import com.main.hooker.hooker.utils.http.ApiResult;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Http {

    private static final String server = "http://ds.trealent.com/";
    private static final String apiGate = server + "api/";

    public static ApiResult post(String apiUri, RequestBody requestBody) throws ApiFailException {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(apiGate + apiUri);
        if(requestBody != null){
            builder.post(requestBody);
        }
        Request request = builder.build();
        return handleRequest(client, request);
    }

    public static ApiResult get(String apiUri) throws ApiFailException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiGate + apiUri).build();
        return handleRequest(client, request);
    }

    private static ApiResult handleRequest(OkHttpClient client, Request request) throws ApiFailException {
        try {
            Response response = client.newCall(request).execute();
            return parseResponse(response);
        } catch (IOException e) {
            return new ApiResult(556, "network issues");
        }
    }

    private static ApiResult parseResponse(Response response) throws ApiFailException {
        try {
            String text = response.body() != null ? response.body().string() : null;
            Gson json = Gsoner.get();
            ApiResult result = json.fromJson(text, ApiResult.class);
            if(result == null){
                throw new Exception();
            }
            if(result.code != 200){
                throw new ApiFailException(result);
            }
            return result;
        } catch (ApiFailException e){
            throw e;
        } catch (Exception e) {
            throw new ApiFailException(new ApiResult());
        }
    }


}
