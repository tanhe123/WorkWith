package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.adapter.ProjectsListAdapter;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.factory.DialogFactory;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends BaseActivity {

    @InjectView(R.id.title)
    EditText et_title;

    @InjectView(R.id.search)
    Button bt_search;

    @InjectView(R.id.gridview)
    GridView gridView;

    @InjectView(R.id.null_result)
    TextView tv_null_result;

    private List<Project> projects;

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(R.string.title_activity_search);

        ButterKnife.inject(this);

        person = (Person)App.get(App.PERSON);

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_null_result.setVisibility(View.GONE);

                final String projectName = et_title.getText().toString();

                Map<String, String> params = new HashMap<String, String>();

                params.put("projectName", projectName);

                WorkApi.post(SearchActivity.this, WorkApi.HOST_SEARCH_PROJECT, params, "请等待", "搜索中", new WorkApi.OnApiEndListenerWithResult() {
                    @Override
                    public void onDo(Result result) {
                        if (result.getStatus().equals("ok")) {

                            tv_null_result.setVisibility(View.GONE);

                            projects = result.getProjects();

                            // 删除所有的本人拥有的项目
                            projects.removeAll(person.getProjects());

                            if (projects.size() == 0) {
                                tv_null_result.setVisibility(View.VISIBLE);
                                return;
                            }

                            ProjectsListAdapter projectsListAdapter = new ProjectsListAdapter(SearchActivity.this, projects);

                            gridView.setAdapter(projectsListAdapter);
                        } else {
                            ToastUtils.showShort("搜索结果为空");

                            tv_null_result.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                //ToastUtils.showShort("position: " + position);
                Dialog dialog = DialogFactory.createConfirmDialog(SearchActivity.this, "提示", "要申请加入吗?", new DialogFactory.OnClickListener() {
                    @Override
                    public void onClick() {

                        // 如果所选项目有密码

                        final Project selectedProject = projects.get(position);
                        final String joinPassword = selectedProject.getJoinPassword();

                        if (joinPassword != null && !joinPassword.equals("")) {// 如果密码需要输入密码

                            Dialog inputDialog = DialogFactory.createInputDialog(SearchActivity.this,// 输入密码的对话框
                                    "请输入密码", new DialogFactory.OnClickListernerWithString() {
                                        @Override
                                        public void onClick(String result) {
                                            if (result.equals(joinPassword)) {// 如果密码输入正确

                                                person.addProject(selectedProject);

                                                WorkApi.updatePerson(SearchActivity.this, person, new WorkApi.OnApiEndListener() {
                                                    @Override
                                                    public void onDo() {
                                                        // 添加成功
                                                        setResult(1);

                                                        ToastUtils.showShort("加入成功");
                                                        finish();
                                                        overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
                                                    }
                                                });

                                            } else {
                                                ToastUtils.showShort("密码输入错误");
                                            }
                                        }
                                    }
                            );

                            inputDialog.show();

                        } else {// 如果没有密码，则直接加入
                            person.addProject(selectedProject);
                            WorkApi.updatePerson(SearchActivity.this, person, new WorkApi.OnApiEndListener() {
                                @Override
                                public void onDo() {
                                    // 添加成功
                                    ToastUtils.showShort("加入成功");
                                }
                            });
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
