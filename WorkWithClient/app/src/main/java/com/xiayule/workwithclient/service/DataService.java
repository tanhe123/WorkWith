package com.xiayule.workwithclient.service;

import android.app.Activity;
import android.content.SharedPreferences;

import com.android.volley.toolbox.StringRequest;
import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tan on 14-9-18.
 */
public class DataService {
    private static final String ACCOUNT = "ACCOUNT";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private static SharedPreferences accountService;

    static {
        accountService = App.getContext().getSharedPreferences(ACCOUNT, Activity.MODE_PRIVATE);
    }

    public static void saveAccount(String username, String password) {
        SharedPreferences.Editor editor = accountService.edit();

        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);

        editor.commit();
    }

    public static Map<String, String> readAccount() {
        Map<String, String> account = new HashMap<String, String>();

        String username = accountService.getString(USERNAME, "");
        String passwrod = accountService.getString(PASSWORD, "");

        account.put(USERNAME, username);
        account.put(PASSWORD, passwrod);

        return account;
    }

    public static void clear() {
        SharedPreferences.Editor editor = accountService.edit();
        editor.clear().commit();
    }
}
