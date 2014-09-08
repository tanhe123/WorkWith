package com.xiayule.workwithclient.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.BroadCastSender;
import com.xiayule.workwithclient.api.Constants;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.data.GsonRequest;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.ui.fragment.TaskFragment;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.ToastUtils;
import com.xiayule.workwithclient.view.MyViewPager;
import com.xiayule.workwithclient.factory.ProgressDialogFactory;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProjectDetailActivity extends BaseActivity implements TaskFragment.OnFragmentInteractionListener{

    @InjectView(R.id.pager)
    MyViewPager mViewPager;

    private MyPagerAdapter myPagerAdapter;
    private Project project;

    private TaskType[] tabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        ButterKnife.inject(this);

        // 初始化
        init();

        actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_tab)));
//        actionBar.setStackedBackgroundDrawable(getResources().getDrawable(
//                R.drawable.tv_shape_green));
    }

    private void init() {
        // 设置 actionBar
        setListener();

        // 取得数据
        project = (Project) App.get(App.PROJECT);

        // 设置标题
        setTitle(project.getProjectName());

        // 设置 ViewPager
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(myPagerAdapter);
    }

    public void fresh() {
        init();
    }

    private void setListener() {
        tabTitles = TaskFragment.TASK_TYPES;

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // 创建一个tab listener，在用户切换tab时调用.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // 当tab被选中时, 切换到ViewPager中相应的页面
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // 隐藏指定的tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // 可以忽略这个事件
            }
        };

        //定义ActionBar模式为NAVIGATION_MODE_TABS
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // 添加3个tab, 并指定tab的文字和TabListener
        for (TaskType tab : tabTitles) {

            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tab.getType())
                            .setTabListener(tabListener));
        }

        // 页面变化时当前的tab也相应变化
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // 当划屏切换页面时，选择相应的tab.
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(ProjectDetailActivity.this, AddTaskActivity.class);

                int indexOfSelected = mViewPager.getCurrentItem();
                intent.putExtra("indexOfSelected", indexOfSelected);


                startActivityForResult(intent, 102);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);

                return true;
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
//        if (requestCode == 102 && resultCode == 1) {// 如果选择保存数据
            // 无论添加、还是删除、移动、修改，返回后都会执行这部分, 用来刷新 3个 fragment
            // 刷新的时候，需要本地同步到网上

            update();
//        }
        }
    }

    private void update() {
        WorkApi.updateProject(this, project, new WorkApi.OnApiEndListener() {
            @Override
            public void onDo() {
                ToastUtils.showShort("同步成功");

                // 如果更新成功，刷新显示
                // 发送广播, 更新 listview显示 新增的 task
//                Intent intent = new Intent(Constants.ACTION_UPDATE_TASK);
//                sendBroadcast(intent);
                BroadCastSender.sendUpdateTaskBroadCast(ProjectDetailActivity.this);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        ToastUtils.showShort("project activity destroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project_detail, menu);
        return true;
    }

    /**
     * ViewPager 的 Adapter
     */
    class MyPagerAdapter extends FragmentPagerAdapter {
        private TaskType[] taskTypes = TaskFragment.TASK_TYPES;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 根据 postsion 来选择 fragment 的内容
            Fragment fragment = TaskFragment.newInstance(taskTypes[position].name());

            return fragment;
        }

        @Override
        public int getCount() {
            return taskTypes.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return taskTypes[position].getType();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //TODO: 不知道该干嘛，保留
    }

    @Override
    public Project getProject() {
        return project;
    }
}
