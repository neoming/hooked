package com.main.hooker.hooker;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.main.hooker.hooker.utils.State;

public class MainApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static State state;

    public static State getState() {
        return state;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        state = new State(context);
    }
    public static Context getContext() {
        return context;
    }
}
