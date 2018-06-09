package com.main.hooker.hooker.model;

import android.os.Handler;
import android.os.Looper;

public class Request extends Thread {
    private RequestCallBack mCallBack;
    private Handler mHandler;


    public Request(RequestCallBack callBack) {
        mCallBack = callBack;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        mHandler.post(() -> mCallBack.success(DataServer.genDate(3)));
    }
}
