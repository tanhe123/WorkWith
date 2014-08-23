package com.xiayule.workwithclient.factory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.ui.fragment.TaskFragment;
import com.xiayule.workwithclient.util.ToastUtils;
import com.xiayule.workwithclient.view.SelectTaskTypeDialog;

/**
 * Created by tan on 14-8-22.
 */
public class DialogFactory {
    public interface OnClickListener {
        public void onClick();
    }

    public static SelectTaskTypeDialog createSelectTaskTypeDialog(Context context, TaskType defaultTaskType, SelectTaskTypeDialog.onSelectedListener onSelectedListener) {
        SelectTaskTypeDialog selectTaskTypeDialog = new SelectTaskTypeDialog(context, defaultTaskType, onSelectedListener);

        return selectTaskTypeDialog;
    }

    public static Dialog createConfirmDialog(Context context, String title, String message, final OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onClickListener.onClick();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtils.showShort("返回");
                    }
                });

        return builder.create();
    }
}
