package com.xiayule.workwithclient.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by tan on 14-8-15.
 */
public class ProgressDialogFactory {
    public static ProgressDialog createProgressDialogWithSpinner(Context context, String title, String detail) {
        ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(detail);
        progressDialog.setCancelable(true);

        return progressDialog;
    }
}
