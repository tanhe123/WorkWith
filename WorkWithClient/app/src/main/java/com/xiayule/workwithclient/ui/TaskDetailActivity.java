package com.xiayule.workwithclient.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.BroadCastSender;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.data.GsonRequest;
import com.xiayule.workwithclient.factory.DialogFactory;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.TimeUtils;
import com.xiayule.workwithclient.util.ToastUtils;
import com.xiayule.workwithclient.factory.ProgressDialogFactory;
import com.xiayule.workwithclient.view.SelectTaskTypeDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TaskDetailActivity extends BaseActivity {
    @InjectView(R.id.title) TextView tv_title;

    @InjectView(R.id.desc) TextView tv_desc;

    @InjectView(R.id.time) TextView tv_time;

    @InjectView(R.id.complete)
    CheckBox cb_complete;

    @InjectView(R.id.detail)
    LinearLayout ll;

    @InjectView(R.id.ll_time)
    LinearLayout ll_time;

    @InjectView(R.id.change_time)
    RelativeLayout rl_change_time;

    @InjectView(R.id.remove)
    RelativeLayout rl_remove;

    @InjectView(R.id.change_type)
    RelativeLayout rl_change_type;

    private Task task;

    // 修改 title 和 desc 的dialog
    private Dialog editDetailDialog;

    // dialog中的 task title 和 desc
    private EditText dialog_title;
    private EditText dialog_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        ButterKnife.inject(this);

        setTitle("任务详情");

        // 取得 task
        task = (Task) App.get(App.TASK);

        // 将属性显示出来
        showTaskDetail();

        setListener();

        // 初始化 editDetailDialog
        initDialog();
    }

    private void setListener() {

        // 修改 title 和 desc
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDetailDialog.show();
            }
        });

        cb_complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            // 更改任务完成状态
                task.setComplete(b);

                updateTaskData();
            }
        });

        rl_change_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date endTime = task.getEndTime();

                // 获取年月日
                Calendar c =  Calendar.getInstance();

                // 如果没有设置截至日期
                if (endTime != null) {
                    c.setTime(endTime);
                }

                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);


                // 直接创建一个 DatePickerdialog， 并将它显示出来
                new DatePickerDialog(TaskDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                                Calendar selectedCalendar = new GregorianCalendar(year, month, dayOfMonth);

                                task.setEndTime(selectedCalendar.getTime());

                                WorkApi.updateTask(TaskDetailActivity.this, task, new WorkApi.OnApiEndListener() {
                                    @Override
                                    public void onDo() {
                                        showTaskDetail();
                                        setResult(1);
                                    }
                                });
                            }
                        },
                        y,
                        m,
                        d)
                        .show();
            }
        });

        rl_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeTask();
            }
        });

        rl_change_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTaskType();
            }
        });
    }

    private void changeTaskType() {
        SelectTaskTypeDialog selectTaskTypeDialog = DialogFactory.createSelectTaskTypeDialog(this, task.getTaskType(), new SelectTaskTypeDialog.onSelectedListener() {
            @Override
            public void onClick(TaskType taskType) {

                // 修改 taskType 类型
                task.setTaskType(taskType);

                // 更新
                updateTaskData();

                setResult(1);
            }
        });

        selectTaskTypeDialog.show();
    }

    private void updateTaskData() {
        WorkApi.updateTask(this, task, new WorkApi.OnApiEndListener() {
            @Override
            public void onDo() {
                ToastUtils.showShort("更新成功");

                // 刷新显示
                showTaskDetail();

                // todo: 这里可以返回2, 即返回后，不用不上传到服务器，只更新
                setResult(1);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        ToastUtils.showShort("taskDetailActivity destroy");
    }

    private void showTaskDetail() {
        // 设置各种属性值
        tv_title.setText(task.getTaskName());
        tv_desc.setText(task.getTaskDesc());

        // 如果设置了时间
        if (task.getEndTime() != null) {

            if (task.isOverDeadline()) {// 如果超期了
                Drawable drawable = getResources().getDrawable(R.drawable.tv_shape_red);
                tv_time.setBackgroundDrawable(drawable);
            } else {// 如果没超期
                Drawable drawable = getResources().getDrawable(R.drawable.tv_shape_green);
                tv_time.setBackgroundDrawable(drawable);
            }

            tv_time.setText(TimeUtils.format(task.getEndTime()));
            ll_time.setVisibility(View.VISIBLE);

        } else {
            ll_time.setVisibility(View.GONE);
        }

        cb_complete.setChecked(task.isComplete());
    }

    private void initDialog() {
        editDetailDialog = createDialog();
    }

    /**
     * 创建 编辑任务的 editDetailDialog
     */
    public AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_task_detail, null);

        dialog_title = (EditText) view.findViewById(R.id.title);
        dialog_desc = (EditText) view.findViewById(R.id.desc);

        dialog_title.setText(task.getTaskName());
        dialog_desc.setText(task.getTaskDesc());

        builder.setMessage(R.string.dialog_task_detail_title)
                .setView(view)
                .setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String title = dialog_title.getText().toString();
                        String desc = dialog_desc.getText().toString();

                        task.setTaskName(title);
                        task.setTaskDesc(desc);

                        updateTaskData();

                        setResult(1);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_move) {
            changeTaskType();

            return true;
        } else if (id == R.id.action_remove) {
            removeTask();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void removeTask() {
        Dialog deleteConfirmDialog = DialogFactory.createConfirmDialog(this, "删除提示", "您确定要删除?", new DialogFactory.OnClickListener() {
            @Override
            public void onClick() {
                // 获取 项目
                Project project = (Project)App.get(App.PROJECT);

                // 从列表中删除
                project.getTasks().remove(task);

                ToastUtils.showShort("删除成功");

                setResult(1);

                finish();
            }
        });

        deleteConfirmDialog.show();
    }
}
