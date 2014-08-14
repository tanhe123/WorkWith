package com.xiayule.workwithclient.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.GagApi;
import com.xiayule.workwithclient.data.GsonRequest;
import com.xiayule.workwithclient.model.Person;
import com.xiayule.workwithclient.model.Project;
import com.xiayule.workwithclient.model.TaskType;
import com.xiayule.workwithclient.ui.fragment.TaskFragment;
import com.xiayule.workwithclient.util.ToastUtils;
import com.xiayule.workwithclient.view.MyViewPager;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTouch;

public class ProjectActivity extends BaseActivity implements TaskFragment.OnFragmentInteractionListener{

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

        // 设置 actionBar
        setListener();

        project = (Project) getIntent().getSerializableExtra("project");

        // 设置标题
        setTitle(project.getProjectName());

        // 设置 ViewPager
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(myPagerAdapter);



        // 使得 viewpager 的 touch 不阻断 click 的传递
   /*     mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean moved = false;

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    moved = false;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    moved = true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (!moved) {
                        view.performClick();
                    }
                }

                return false;
            }
        });*/

        mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("mview pager click");
            }
        });

        /*

        // 发送测试
        String person = new Gson().toJson(App.getData());

        Map param = new HashMap();
        param.put("person", person);

        GsonRequest req = new GsonRequest<Person.PersonRequestData>(Request.Method.POST, GagApi.HOST_GETPERSONDO, Person.PersonRequestData.class, param,
                new Response.Listener<Person.PersonRequestData>() {
                    @Override
                    public void onResponse(Person.PersonRequestData personRequestData) {
                        System.out.println(personRequestData);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("TAG", "voller error");
                    }
                });

        executeRequest(req);*/
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project, menu);
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
