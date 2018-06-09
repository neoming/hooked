package com.main.hooker.hooker.model;

import android.os.Handler;
import android.os.Looper;

import com.main.hooker.hooker.entity.Book;

import java.util.List;

public class Request extends Thread {
    private RequestCallBack mCallBack;
    private Handler mHandler;


    public Request(RequestCallBack callBack) {
        mCallBack = callBack;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            List<Book.DataBean> data = DataServer.genDate(3);
            mHandler.post(() -> mCallBack.success(data));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
