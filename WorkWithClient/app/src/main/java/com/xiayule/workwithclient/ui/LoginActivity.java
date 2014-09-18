package com.xiayule.workwithclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.service.DataService;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.ToastUtils;
import com.xiayule.workwithclient.view.FloatLabeledEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.bt_login)
    Button bt_login;

    @InjectView(R.id.tv_register)
    TextView tv_register;

    @InjectView(R.id.username)
    FloatLabeledEditText fe_username;

    @InjectView(R.id.password)
    FloatLabeledEditText fe_password;

    @InjectView(R.id.save_status)
    CheckBox cb_save_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // 隐藏 actionbar
        actionBar.hide();

        ButterKnife.inject(this);

        setListener();
    }

    private void setListener() {
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = fe_username.getText().toString();
                final String password = fe_password.getText().toString();

                if (username.equals("")) {
                    Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);//加载动画资源文件
                    fe_username.startAnimation(shake); //给组件播放动画效果
                    ToastUtils.showShort("用户名不能为空");
                    return ;
                } else if (password.equals("")) {
                    Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);//加载动画资源文件
                    fe_password.startAnimation(shake); //给组件播放动画效果
                    ToastUtils.showShort("密码不能为空");
                    return ;
                }

                Map<String, String> params = new HashMap();
                params.put("username", username);
                params.put("password", password);

                WorkApi.post(LoginActivity.this,
                        WorkApi.HOST_LOGIN,
                        params,
                        "登录中",
                        "请稍候",
                        new WorkApi.OnApiEndListenerWithResult() {
                            @Override
                            public void onDo(Result result) {
                                if (result.getStatus().equals("ok")) {
                                    // 保存用户名和密码
                                    if (cb_save_status.isChecked()) {// 保存登录状态
                                        DataService.saveAccount(username, password);
                                    }

                                    App.put(DataService.USERNAME, username);
                                    App.put(DataService.PASSWORD, password);

                                    ToastUtils.showShort(R.string.login_success);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    //第一个参数为启动时动画效果，第二个参数为退出时动画效果
//                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    /*overridePendingTransition(R.anim.hyperspace_in,
                                            R.anim.hyperspace_out);*/
                                    overridePendingTransition(R.anim.scale_rotate,
                                            R.anim.my_alpha_action);

                                   /* overridePendingTransition(R.anim.push_left_in,
                                            R.anim.push_left_out);*/

                                } else {
                                    ToastUtils.showShort(result.getMessage());
                                }
                            }
                        });

            }
        });

        // 注册
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                //第一个参数为启动时动画效果，第二个参数为退出时动画效果
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
