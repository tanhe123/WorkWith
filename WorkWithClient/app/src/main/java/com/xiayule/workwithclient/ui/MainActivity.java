package com.xiayule.workwithclient.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.Constants;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.data.GsonRequest;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.ui.fragment.ProjectsFragment;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements ProjectsFragment.OnFragmentInteractionListener {
    // 当前侧边栏所有的条目
    private String[] mDrawerTitles;

    // 当前选中的条目
    private String mCategory;

    private ActionBarDrawerToggle mDrawerToggle;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.left_drawer)
    ListView mDrawerList;

    // 菜单
    private Menu mMenu;

    private Person person;

    public void click(View v) {
        ToastUtils.showLong("waha click gridview butter");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        initDrawerLayout();
        init();
        initData();
    }

    public void init() {
        setTitle(R.string.app_name);

        setListener();

        // 设置打开应用的默认选择
        setCaegory(mCategory);
    }

    public void initData() {
        WorkApi.queryPerson(this, new WorkApi.OnApiEndListener() {
            @Override
            public void onDo() {
                person = (Person) App.get(App.PERSON);

                List<String> projectNames = person.projectNames();

                // 添加原始条目，再加上项目名称一块添加到侧边栏
                String[] drawerTitles = getResources().getStringArray(R.array.drawer_item_array);
                ArrayList drawerItems = new ArrayList();
                // 添加元素
                drawerItems.addAll(Arrays.asList(drawerTitles));
                drawerItems.addAll(projectNames);

                // 重新射灯侧边栏
                mDrawerTitles = (String[])drawerItems.toArray(new String[drawerItems.size()]);
                initDrawerLayout(mDrawerTitles);

                // 刷新显示
                init();
            }
        });

        /*// 获取数据，并更新侧边栏
        executeRequest(new GsonRequest(WorkApi.HOST_PERSON,
                Person.PersonRequestData.class,
                new Response.Listener<Person.PersonRequestData>() {
                    @Override
                    public void onResponse(Person.PersonRequestData data) {
                        Log.d("TAG", new Gson().toJson(data));

                        //TODO: 如果 data == null 怎么办?
                        if (data != null && data.getStatus().equals("ok")) {

                            person = data.getPerson();
                            // 保存数据
                            App.put(App.PERSON, person);

                            List<String> projectNames = data.getPerson().projectNames();

                            // 添加原始条目，再加上项目名称一块添加到侧边栏
                            String[] drawerTitles = getResources().getStringArray(R.array.drawer_item_array);
                            ArrayList drawerItems = new ArrayList();
                            // 添加元素
                            drawerItems.addAll(Arrays.asList(drawerTitles));
                            drawerItems.addAll(projectNames);

                            // 重新射灯侧边栏
                            mDrawerTitles = (String[])drawerItems.toArray(new String[drawerItems.size()]);
                            initDrawerLayout(mDrawerTitles);

                            // 刷新显示
                            init();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("Volley error");
                    }
                }));*/
    }

    private void initDrawerLayout() {
        mDrawerTitles = getResources().getStringArray(R.array.drawer_item_array);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item,
                R.id.item, mDrawerTitles));
    }

    private void initDrawerLayout(String[] drawerTitles) {
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item,
                R.id.item, drawerTitles));
    }

    private void setListener() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(R.string.app_name);
                //设置刷新按钮不可见
                mMenu.findItem(R.id.action_refresh).setVisible(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setTitle(mCategory);
                mMenu.findItem(R.id.action_refresh).setVisible(true);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // set the listener of the listview
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                setCaegory(mDrawerTitles[position]);

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    public void setCaegory(String category) {

        // 第一次执行
        if (this.mCategory == null) {
            mCategory = category = mDrawerTitles[0];
        }

        // TODO: 设置 fragment
        if (category.equals("工作台")) {
            ToastUtils.showShort(mCategory);
            setTitle(mCategory);
            mCategory = category;
        } else if (category.equals("项目")) {
            ToastUtils.showShort(mCategory);
            setTitle(mCategory);
            mCategory = category;

            // 设置 fragment
            Fragment fragment = ProjectsFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();

        } else {
            Intent intent = new Intent(MainActivity.this, ProjectDetailActivity.class);

            Project selectedProject = ((Person)App.get(App.PERSON)).getProject(category);

            // 设置要传递的数据
            App.put(App.PROJECT, selectedProject);


            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_refresh:
                update();
                ToastUtils.showShort("更新成功");
                break;
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, AddProjectActivity.class);
                startActivityForResult(intent, 103);

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ToastUtils.showShort("增加成功，已返回，刷新中");

        //TODO: 使用广播, 添加了 project
        if (resultCode == 1) {
            update();
        }
    }


    private void update() {
        WorkApi.updatePerson(this, person, new WorkApi.OnApiEndListener() {
            @Override
            public void onDo() {
                ToastUtils.showShort("同步成功");

                // 调用 activity 的 update
                // 如果更新成功，刷新显示
                // 发送广播, 更新 listview显示 新增的 task
                Intent intent = new Intent(Constants.ACTION_ADD_PROJECT);
                sendBroadcast(intent);

                init();
            }
        });

        /*HashMap param = new HashMap();
        param.put("method", "person");
        param.put("person", new Gson().toJson(person));

        // 显示 progressDialog
        progressDialog.show();

        // 更新 person
        GsonRequest req = new GsonRequest<Result>(Request.Method.POST, WorkApi.HOST_UPDATE, Result.class, param,
                new Response.Listener<Result>() {
                    @Override
                    public void onResponse(Result result) {
                        if (result.getStatus().equals("ok")) {

                        } else {
                            ToastUtils.showShort("发生错误");
                        }

                        // 隐藏 progressdialog
                        progressDialog.dismiss();
                        ToastUtils.showShort("同步成功");

                        // 调用 activity 的 update
                       *//* // 如果更新成功，刷新显示
                        // 发送广播, 更新 listview显示 新增的 task
                        Intent intent = new Intent(Constants.ACTION_ADD_PROJECT);
                        sendBroadcast(intent);*//*

                        init();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("网络错误，请稍后重试");
                        Log.d("TAG", "网络错误");

                        // 隐藏 progressdialog
                        progressDialog.dismiss();
                    }
                });

        executeRequest(req);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(mCategory);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //提示如果是服务里调用，必须加入new task标
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public void refresh() {
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public List<Project> getProjects() {
        return ((Person)App.get(App.PERSON)).getProjects();
    }
}
