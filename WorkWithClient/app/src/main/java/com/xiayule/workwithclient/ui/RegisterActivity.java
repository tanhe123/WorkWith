package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class RegisterActivity extends BaseActivity {
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

                if (username.equals("")) {

                    tv_message.setText("用户名不能为空");
                    tv_message.setVisibility(View.VISIBLE);

                    Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);//加载动画资源文件
                    et_username.startAnimation(shake); //给组件播放动画效果

                    return ;
                } else if (password.equals("")) {
                    tv_message.setText("用户名不能为空");
                    tv_message.setVisibility(View.VISIBLE);

                    Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);//加载动画资源文件
                    et_password.startAnimation(shake); //给组件播放动画效果

                } else if (name.equals("")) {
                    tv_message.setText("用户名不能为空");
                    tv_message.setVisibility(View.VISIBLE);

                    Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);//加载动画资源文件
                    et_name.startAnimation(shake); //给组件播放动画效果
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
