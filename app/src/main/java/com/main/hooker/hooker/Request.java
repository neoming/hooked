package com.main.hooker.hooker;

import android.os.Handler;
import android.os.Looper;

public class Request extends Thread {
    private RequestCallBack mCallBack;
    private Handler mHandler;
    private DemoDataServer mServer;

    public Request(RequestCallBack callBack, DemoDataServer server) {
        mCallBack = callBack;
        mHandler = new Handler(Looper.getMainLooper());
        mServer = server;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mHandler.post(() -> mCallBack.success(mServer.genData(3)));


    }

}
