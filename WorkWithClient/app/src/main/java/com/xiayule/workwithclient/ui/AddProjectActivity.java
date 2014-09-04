package com.xiayule.workwithclient.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    @InjectView(R.id.password)
    EditText et_join_password;

    @InjectView(R.id.bt_add)
    Button bt_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        ButterKnife.inject(this);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
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

            // add project
            add();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void add() {
        String title = et_title.getText().toString();
        String desc = et_desc.getText().toString();
        String joinPassword = et_join_password.getText().toString();

        if (title.equals("")) {
            ToastUtils.showShort("项目名称不能为空");
            return ;
        }

        Project project = new Project();

        project.setProjectName(title);
        project.setProjectDesc(desc);

        project.setJoinPassword(joinPassword);

        project.setCreateTime(new Date());

        // 保存数据
        Person person = ((Person) App.get(App.PERSON));
        person.addProject(project);
        // 设置 ownerId
        project.setProjectOwnerId(person.getId());

        // 返回成功保存标识
        setResult(1);
        this.finish();
    }
}
