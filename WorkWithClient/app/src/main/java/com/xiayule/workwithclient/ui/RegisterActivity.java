package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterActivity extends Activity {
    @InjectView(R.id.bt_register)
    Button bt_register;

    @InjectView(R.id.username)
    EditText et_username;

    @InjectView(R.id.password)
    EditText et_password;

    @InjectView(R.id.name)
    EditText et_name;

    @InjectView(R.id.message)
    TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle(R.string.title_activity_register);

        ButterKnife.inject(this);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String name = et_name.getText().toString();

                if (username.equals("") || password.equals("") || name.equals("")) {

                    tv_message.setText("请检查注册信息");
                    tv_message.setVisibility(View.VISIBLE);

                    return ;
                }

                ToastUtils.showShort(username + " " + name + " " + password);

                // 设置参数
                HashMap params = new HashMap();

                params.put("username", username);
                params.put("password", password);
                params.put("name", name);

                WorkApi.post(RegisterActivity.this,
                        WorkApi.HOST_REGISTER,
                        params,
                        "注册",
                        "请稍候",
                        new WorkApi.OnApiEndListenerWithResult() {
                            @Override
                            public void onDo(Result result) {

                                if (result.getStatus().equals("ok")) {// 注册成功
                                    ToastUtils.showShort("恭喜，注册成功");

                                    // 结束，返回登录界面
                                    finish();
                                    overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
                                } else { // 注册失败
                                    ToastUtils.showShort("注册失败");
                                    tv_message.setText(result.getMessage());
                                    tv_message.setVisibility(View.VISIBLE);
                                }
                            }
                        });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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
