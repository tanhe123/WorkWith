package com.xiayule.workwithclient.model;

/**
 * Created by storm on 14-3-25.
 * 用来代表哪一个 item, 或者说，数据元素，比如是团队还是项目
 */
public enum Category {
    person("Person"), project("Project");
    private String mDisplayName;

    Category(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

}
