package com.xiayule.workwithclient.api;

import android.content.Context;
import android.content.Intent;

/**
 * Created by tan on 14-8-23.
 */
public class BroadCastSender {
    public static void sendUpdateTaskBroadCast(Context context) {
        // 如果更新成功，刷新显示
        // 发送广播, 更新 listview显示 新增的 task
        Intent intent = new Intent(Constants.ACTION_UPDATE_TASK);
        context.sendBroadcast(intent);
    }

    public static void sendPersonUpdatedBroadCast(Context context) {
        // 如果更新成功，刷新显示
        // 发送广播, 更新 listview显示 新增的 task
        Intent intent = new Intent(Constants.ACTION_QUERY_PERSON);

        context.sendBroadcast(intent);
    }
}
