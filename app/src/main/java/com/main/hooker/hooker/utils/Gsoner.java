package com.main.hooker.hooker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Gsoner {
    private static Gson mGason;
    private static Gson generate(){
        return new GsonBuilder().enableComplexMapKeySerialization()
                        .create();
    }

    public static Gson get(){
        if(mGason == null){
            mGason = generate();
        }
        return mGason;
    }
}
