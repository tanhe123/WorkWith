package com.xiayule.workwithclient.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddProjectActivity extends BaseActivity {

    @InjectView(R.id.title)
    EditText et_title;

    @InjectView(R.id.desc)
    EditText et_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        ButterKnife.inject(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_project, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_save) {
            String title = et_title.getText().toString();
            String desc = et_desc.getText().toString();

            if (title.equals("")) {
                ToastUtils.showShort("项目名称不能为空");
                return true;
            }

            Project project = new Project();

            project.setProjectName(title);
            project.setProjectDesc(desc);

            project.setCreateTime(new Date());

            // 保存数据
            ((Person) App.get(App.PERSON)).addProject(project);

            // 返回成功保存标识
            setResult(1);

            this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
