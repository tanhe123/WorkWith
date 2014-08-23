package com.xiayule.workwithclient.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.ui.fragment.TaskFragment;
import com.xiayule.workwithclient.util.ToastUtils;

/**
 * Created by tan on 14-8-23.
 */
public class SelectTaskTypeDialog {
    private AlertDialog dialog;

    final String[] taskTypeNames = new String[] {"在做", "要做", "待办"};
    final TaskType[] taskTypes = TaskFragment.TASK_TYPES;

    private onSelectedListener onSelectedListener;

    private int selectedIndex;

    public SelectTaskTypeDialog(Context context, TaskType defaultTaskType, final onSelectedListener onSelectedListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        this.onSelectedListener = onSelectedListener;

        // 默认选中第一项
        selectedIndex = 0;

        // 找到默认的 index
        for (int i=0; i<taskTypes.length; i++) {
            if (taskTypes[i] == defaultTaskType) {
                selectedIndex = i;
            }
        }

        builder.setTitle(R.string.dialog_select_task_type_title)
                .setSingleChoiceItems(taskTypeNames, selectedIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedIndex = i;
                    }
                })
                .setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 获得选中的 taskType
                        TaskType selectedTaskType = taskTypes[selectedIndex];

                        // 回调
                        onSelectedListener.onClick(selectedTaskType);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 取消什么也不做

                    }
                });

        dialog = builder.create();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public interface onSelectedListener {
        public void onClick(TaskType taskType);
    }

}
