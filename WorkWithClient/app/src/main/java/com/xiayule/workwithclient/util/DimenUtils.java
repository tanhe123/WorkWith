package com.xiayule.workwithclient.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by tan on 14-9-15.
 */
public class DimenUtils {
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
