package com.xiayule.workwithclient.model;

/**
 * Created by tan on 14-8-9.
 */
public enum TaskType {
    TODO("要做"), NOW("在做"), FUTURE("待定");

    private String type;
    TaskType(String type) { this.type = type; }
    public String getType() { return this.type; }

    @Override
    public String toString() {
        return "name:" + name() + ";value:" + getType();
    }
}