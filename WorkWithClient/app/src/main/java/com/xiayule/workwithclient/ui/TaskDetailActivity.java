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
import com.xiayule.workwithclient.api.GagApi;
import com.xiayule.workwithclient.data.GsonRequest;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.TimeUtils;
import com.xiayule.workwithclient.util.ToastUtils;
import com.xiayule.workwithclient.view.ProgressDialogFactory;

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

    private Task task;

    private ProgressDialog progressDialog;

    // 修改 title 和 desc 的dialog
    private Dialog dialog;
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

        progressDialog = ProgressDialogFactory.createProgressDialogWithSpinner(this,
                "更新中", "请稍候");

        // 将属性显示出来
        showTaskDetail();

        setListener();

        // 初始化 dialog
        initDialog();
    }

    private void setListener() {

        // 修改 title 和 desc
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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
        // 设置参数
        HashMap param = new HashMap();
        param.put("method", "task");
        param.put("task", new Gson().toJson(task));

        // 显示 progressDialog
        progressDialog.show();;

        // 更新 task
        GsonRequest req = new GsonRequest<Result>(Request.Method.POST, GagApi.HOST_UPDATE, Result.class, param,
                new Response.Listener<Result>() {
                    @Override
                    public void onResponse(Result result) {
                        if (result.getStatus().equals("ok")) {

                        } else {
                            ToastUtils.showShort("发生错误");
                        }

                        // 隐藏 progressdialog
                        progressDialog.dismiss();

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

        executeRequest(req);
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

        if (task.getEndTime() != null) {
            tv_time.setText(TimeUtils.format(task.getEndTime()));
        }

        cb_complete.setChecked(task.isComplete());
    }

    private void initDialog() {
        dialog = createDialog();
    }

    /**
     * 创建 编辑任务的 dialog
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
