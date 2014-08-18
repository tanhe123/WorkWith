package com.xiayule.workwithclient;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tan on 14-8-5.
 */
public class App extends Application {
    private static Context sContext;

    public static final String PERSON = "person";
    public static final String PROJECT = "project";
    public static final String TASK = "task";

    private static Map<String, Object> data = new HashMap<String, Object>();

//    private static Person data;

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

//    public static Person getData() {
//        return data;
//    }

//    public static void setData(Person data) {
//        App.data = data;
//    }

    public static Object get(String key) {
        return data.get(key);
    }

    public static void put(String key, Object obj) {
        data.put(key, obj);
    }
}
