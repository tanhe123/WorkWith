package com.xiayule.workwithclient;

import android.app.Application;
import android.content.Context;

import com.xiayule.workwithclient.model.Person;

/**
 * Created by tan on 14-8-5.
 */
public class App extends Application {
    private static Context sContext;

    // 保存个人信息
    private static Person data;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

    public static Context getsContext() {
        return sContext;
    }

    public static void setsContext(Context sContext) {
        App.sContext = sContext;
    }

    public static Person getData() {
        return data;
    }

    public static void setData(Person data) {
        App.data = data;
    }
}
