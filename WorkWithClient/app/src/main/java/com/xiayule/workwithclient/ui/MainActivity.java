package com.xiayule.workwithclient.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.adapter.DrawerListAdapter;
import com.xiayule.workwithclient.api.Constants;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.ui.fragment.ProjectsFragment;
import com.xiayule.workwithclient.util.ToastUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements ProjectsFragment.OnFragmentInteractionListener {
    // 当前侧边栏所有的条目
    private String[] mDrawerTitles;

    // 当前选中的条目
    private String mCategory;

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerListAdapter drawerListAdapter;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.left_drawer)
    ListView mDrawerList;

    // 菜单
    private Menu mMenu;

    private Person mPerson;


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
        String username = (String) App.get("username");
        String password = (String) App.get("password");

        WorkApi.queryPerson(this, username, password, new WorkApi.OnApiEndListener() {
            @Override
            public void onDo() {
                mPerson = (Person) App.get(App.PERSON);

                List<String> projectNames = mPerson.projectNames();

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

    }

    private void initDrawerLayout() {
        mDrawerTitles = getResources().getStringArray(R.array.drawer_item_array);

        initDrawerLayout(mDrawerTitles);
    }


    private void initDrawerLayout(String[] drawerTitles) {
        /*String[] initTitle = getResources().getStringArray(R.array.drawer_item_array);


        List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

        for (String title : drawerTitles) {
            HashMap<String, Object> data = new HashMap<String, Object>();

            data.put("title", title);

            if (title.equals(initTitle[0])) {
                data.put("img", R.drawable.menu_dashboard_grey);
            } else if (title.equals(initTitle[1])) {
                data.put("img", R.drawable.menu_project_grey);
            } else {
                data.put("img", R.drawable.menu_team_grey);
            }

            datas.add(data);
        }
*/
        /*mDrawerList.setAdapter(new SimpleAdapter(MainActivity.this,
                datas, R.layout.drawer_list_item,
                new String[] {"img", "title"},
                new int[] {R.id.icon, R.id.item}));*/

        drawerListAdapter = new DrawerListAdapter(MainActivity.this,
                Arrays.asList(drawerTitles));
        mDrawerList.setAdapter(drawerListAdapter);

//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_list_item,
//                R.id.item, drawerTitles));
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

        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.action_refresh:
                update();
                ToastUtils.showShort("更新成功");
                break;
            case R.id.action_add:
                intent = new Intent(MainActivity.this, AddProjectActivity.class);
                startActivityForResult(intent, 103);
                break;
            case R.id.action_search:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
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
        WorkApi.updatePerson(this, mPerson, new WorkApi.OnApiEndListener() {
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
        Person person = (Person) App.get(App.PERSON);

        if (person == null) {
            return null;
        }

        return person.getProjects();
    }
}
