package com.xiayule.workwithclient.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
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
    }

    private void updateTaskData() {
        WorkApi.updateTask(this, task, new WorkApi.OnApiEndListener() {
            @Override
            public void onDo() {
                ToastUtils.showShort("更新成功");

                // 刷新显示
                showTaskDetail();
            }
        });

        /*// 设置参数
        HashMap param = new HashMap();
        param.put("method", "task");
        param.put("task", new Gson().toJson(task));

        // 显示 progressDialog
        progressDialog.show();;

        // 更新 task
        GsonRequest req = new GsonRequest<Result>(Request.Method.POST, WorkApi.HOST_UPDATE, Result.class, param,
                new Response.Listener<Result>() {
                    @Override
                    public void onResponse(Result result) {
                        if (!result.getStatus().equals("ok")) {
                            ToastUtils.showShort("发生错误");
                        }

                        // 隐藏 progressdialog
                        progressDialog.dismiss();

                        ToastUtils.showShort("更新成功");

                        // 刷新显示
                        showTaskDetail();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("volley error");
                        Log.d("TAG", "网络错误");

                        // 隐藏 progressdialog
                        progressDialog.dismiss();
                    }
                });

        executeRequest(req);*/
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
            tv_time.setText(TimeUtils.format(task.getEndTime()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_move) {
//            Intent intent = new Intent(TaskDetailActivity.this, SelectTaskTypeActivity.class);
//            startActivityForResult(intent, 104);

            SelectTaskTypeDialog selectTaskTypeDialog = DialogFactory.createSelectTaskTypeDialog(this, task.getTaskType(), new SelectTaskTypeDialog.onSelectedListener() {
                @Override
                public void onClick(TaskType taskType) {

                    // 修改 taskType 类型
                    task.setTaskType(taskType);

                    // 更新
                    updateTaskData();

                    //todo: 设置修改状态为 true，返回后， 在 activityresult中刷新
                }
            });

            selectTaskTypeDialog.show();

            return true;
        } else if (id == R.id.action_remove) {
            Dialog deleteConfirmDialog = DialogFactory.createConfirmDialog(this, "删除提示", "您确定要删除?", new DialogFactory.OnClickListener() {
                @Override
                public void onClick() {
                    //todo: 删除操作
                    // 获取 项目
                    Project project = (Project)App.get(App.PROJECT);

                    // 从列表中删除
                    project.getTasks().remove(task);

                    //todo: 更新操作
                    updateTaskData();
                }
            });

            deleteConfirmDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
/*
    *//**
     * 返回结果
     *//*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ToastUtils.showShort(""+requestCode);

        // 修改了内容
        if (requestCode == 104 && resultCode == 1) {
            ToastUtils.showShort("修改了内容");
        }
    }*/
}
