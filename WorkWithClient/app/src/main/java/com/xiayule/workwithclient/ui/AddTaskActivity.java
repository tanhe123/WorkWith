package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddTaskActivity extends BaseActivity {

    @InjectView(R.id.title)
    EditText et_title;

    @InjectView(R.id.desc)
    EditText et_desc;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ButterKnife.inject(this);

        project = (Project) App.get(App.PROJECT);

    }

    public void save() {
        String title = et_title.getText().toString();
        String desc = et_desc.getText().toString();

        // 新建人物
        Task task = new Task();

        task.setTaskName(title);
        task.setTaskDesc(desc);

        // 默认
        task.setTaskType(TaskType.NOW);
        task.setComplete(false);

        project.addTask(task);

        setResult(1);
        finish();
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

        }

        return super.onOptionsItemSelected(item);
    }
}
