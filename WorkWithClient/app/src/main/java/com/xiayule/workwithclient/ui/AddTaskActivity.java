package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.factory.DialogFactory;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.ui.fragment.TaskFragment;
import com.xiayule.workwithclient.util.ToastUtils;
import com.xiayule.workwithclient.view.SelectTaskTypeDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddTaskActivity extends BaseActivity {

    @InjectView(R.id.title)
    EditText et_title;

    @InjectView(R.id.desc)
    EditText et_desc;

    @InjectView(R.id.taskType)
    LinearLayout ll_taskType;

    // 显示 taskType
    @InjectView(R.id.taskTypeName)
    TextView tv_taskTypeName;

    private Project project;

    // 选择 taskType Dialog
    private SelectTaskTypeDialog selectTaskTypeDialog;

    private TaskType selectedTaskType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setTitle(R.string.title_activity_add_task);

        ButterKnife.inject(this);

        init();

        setListener();
    }

    private void init() {
        // 根据传过来 index，来设定默认的 taskType
        int indexOfSelected = getIntent().getIntExtra("indexOfSelected", 0);
        selectedTaskType = TaskFragment.TASK_TYPES[indexOfSelected];

        project = (Project) App.get(App.PROJECT);

        updateTaskName(selectedTaskType);
    }

    public void updateTaskName(TaskType taskType) {
//        if (selectedTaskType != taskType) {
            selectedTaskType = taskType;
            tv_taskTypeName.setText(selectedTaskType.getType());
//        }
    }

    private void setListener() {
        selectTaskTypeDialog = DialogFactory.createSelectTaskTypeDialog(this, TaskType.TODO, new SelectTaskTypeDialog.onSelectedListener() {
            @Override
            public void onClick(TaskType taskType) {
                // 更新选择的 taskType
                updateTaskName(taskType);
            }
        });

        ll_taskType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTaskTypeDialog.show();
            }
        });
    }

    public void save() {
        String title = et_title.getText().toString();
        String desc = et_desc.getText().toString();

        // 新建人物
        Task task = new Task();

        task.setTaskName(title);
        task.setTaskDesc(desc);
        task.setTaskType(selectedTaskType);

        // 默认
        task.setComplete(false);

        project.addTask(task);

        // 同步到 server
        WorkApi.updateProject(this, project, new WorkApi.OnApiEndListener() {
            @Override
            public void onDo() {
                ToastUtils.showShort("创建成功");
                setResult(1);
                finish();
                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                save();
                return true;
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
