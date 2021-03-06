package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.service.DataService;
import com.xiayule.workwithclient.view.SecretTextView;
import com.xiayule.workwithclient.view.titanic.Titanic;
import com.xiayule.workwithclient.view.titanic.TitanicTextView;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WelcomeActivity extends BaseActivity {

    @InjectView(R.id.titanic_tv)
    TitanicTextView titanicTextView;

    @InjectView(R.id.textview)
    SecretTextView secretTextView;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        actionBar.hide();

        ButterKnife.inject(this);

        // 波纹效果
        new Titanic().start(titanicTextView);

        // 检测是否记住登录状态
        Map<String, String> account = DataService.readAccount();
        username = account.get(DataService.USERNAME);
        password = account.get(DataService.PASSWORD);

        // 显示 scretTextView
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;// 开始 secretTextview 动画
                handler.sendMessage(message);
            }
        }, 3000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 2;// 启动loginactivity
                handler.sendMessage(message);
            }
        }, 6000);
    }


    // 定时事件处理
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                // 显示隐藏的文字
                secretTextView.setmDuration(3000);
                // 默认隐藏
                secretTextView.setIsVisible(false);

                secretTextView.toggle();
            } else if (msg.what == 2) {
                // 根据是否保存了密码，来选择进入登录页面还是直接进入 mainactivity
                if (username.equals("") || password.equals("")) {
                    // 启动 login activity
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);  // enter the main activity finally
                } else {
                    App.put(DataService.USERNAME, username);
                    App.put(DataService.PASSWORD, password);

                    // 启动 MainActivity
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                finish();
            }
            super.handleMessage(msg);
        };
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
