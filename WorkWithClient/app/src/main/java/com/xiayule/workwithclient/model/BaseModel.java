package com.xiayule.workwithclient.model;

import com.google.gson.Gson;

/**
* Created by tan on 14-8-5.
        */
public abstract class BaseModel {
    public String toJson() {
        return new Gson().toJson(this);
    }
}
