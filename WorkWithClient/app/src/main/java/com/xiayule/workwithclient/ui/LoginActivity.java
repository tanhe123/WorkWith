package com.xiayule.workwithclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiayule.workwithclient.App;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.ToastUtils;

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
    EditText et_username;

    @InjectView(R.id.password)
    EditText et_password;

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
                final String username = et_username.getText().toString();
                final String password = et_password.getText().toString();

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
                                    //todo: 保存用户名和密码
                                    App.put("username", username);
                                    App.put("password", password);

                                    ToastUtils.showShort("登录成功");
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
