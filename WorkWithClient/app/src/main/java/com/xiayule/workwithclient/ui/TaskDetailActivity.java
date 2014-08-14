package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.model.Task;
import com.xiayule.workwithclient.util.TimeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TaskDetailActivity extends BaseActivity {

    @InjectView(R.id.title) TextView title;

    @InjectView(R.id.desc) TextView desc;

    @InjectView(R.id.time) TextView time;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        ButterKnife.inject(this);

        setTitle("任务详情");

        task = (Task) getIntent().getSerializableExtra("task");

        title.setText(task.getTaskName());
        desc.setText(task.getTaskDesc());
        time.setText(TimeUtils.format(task.getEndTime()));
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
