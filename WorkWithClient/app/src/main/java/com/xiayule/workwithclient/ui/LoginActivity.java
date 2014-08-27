package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

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

    @InjectView(R.id.bt_register)
    Button bt_register;

    @InjectView(R.id.username)
    EditText et_username;

    @InjectView(R.id.password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        setListener();

        et_username.setText("tanhe123");
        et_password.setText("123");
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
                                } else {
                                    ToastUtils.showShort(result.getMessage());
                                }
                            }
                        });
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
