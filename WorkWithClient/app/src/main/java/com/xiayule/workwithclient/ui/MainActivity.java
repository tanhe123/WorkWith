package com.xiayule.workwithclient.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.GagApi;
import com.xiayule.workwithclient.data.GsonRequest;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.ui.fragment.TaskFragment;
import com.xiayule.workwithclient.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;


public class MainActivity extends BaseActivity /*implements TaskFragment.OnFragmentInteractionListener*/ {
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

    public void click(View v) {
        ToastUtils.showLong("waha click gridview butter");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        initDrawerLayout();

        setTitle(R.string.app_name);

        setListener();

        // 设置打开应用的默认选择
        setCaegory(mDrawerTitles[0]);

        // 获取数据，并更新侧边栏
        executeRequest(new GsonRequest(GagApi.HOST_PERSON,
                Person.PersonRequestData.class,
                new Response.Listener<Person.PersonRequestData>() {
                    @Override
                    public void onResponse(Person.PersonRequestData data) {
                      Log.d("TAG", new Gson().toJson(data));

                        //TODO: 如果 data == null 怎么办?
                        if (data != null && data.getStatus().equals("ok")) {
                            App.setData(data.getPerson());
                            List<String> projectNames = App.getData().getProjectNames();

                            // 添加原始条目，再加上项目名称一块添加到侧边栏
                            String[] drawerTitles = getResources().getStringArray(R.array.drawer_item_array);
                            ArrayList drawerItems = new ArrayList();
                            // 添加元素
                            drawerItems.addAll(Arrays.asList(drawerTitles));
                            drawerItems.addAll(projectNames);

                            // 重新射灯侧边栏
                            mDrawerTitles = (String[])drawerItems.toArray(new String[drawerItems.size()]);
                            initDrawerLayout(mDrawerTitles);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showShort("Volley error");
                    }
                }));
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
//            case R.id.action_add_memo:
//                actionAddMemo();
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }

//        return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        return true;
    }

    public void setCaegory(String category) {
        if (this.mCategory == category) {
            return ;
        }

        mCategory = category;

        // TODO: 设置 fragment
        if (mCategory.equals("工作台")) {
            ToastUtils.showShort(mCategory);
            setTitle(mCategory);
        } else if (mCategory.equals("项目")) {
            ToastUtils.showShort(mCategory);
            setTitle(mCategory);
        } else {
            /*//TODO :test
            TaskType[] taskTypes = TaskFragment.TASK_TYPES;
            Fragment fragment = TaskFragment.newInstance(TaskFragment.TASK_TYPE_NOW);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment).commit();*/

            Intent intent = new Intent(MainActivity.this, ProjectActivity.class);

            Project selectedProject = App.getData().getProject(mCategory);

            // 设置要传递的数据
            Bundle bundle = new Bundle();
            bundle.putSerializable("project", selectedProject);

            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    /*@Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public Project getProject() {
        Project project = App.getData().getProject("熊猫不烧香");
        ToastUtils.showLong(project.toString());
        return project;
    }*/
}
