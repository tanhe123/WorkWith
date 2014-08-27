package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.api.WorkApi;
import com.xiayule.workwithclient.util.Result;
import com.xiayule.workwithclient.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends BaseActivity {

    @InjectView(R.id.title)
    EditText et_title;

    @InjectView(R.id.search)
    Button bt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.inject(this);

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String projectName = et_title.getText().toString();

                Map<String, String> params = new HashMap<String, String>();

                params.put("projectName", projectName);

                WorkApi.post(SearchActivity.this, WorkApi.HOST_SEARCH_PROJECT, params, "请等待", "搜索中", new WorkApi.OnApiEndListenerWithResult() {
                    @Override
                    public void onDo(Result result) {
                        ToastUtils.showShort("已经返回");
                    }
                });
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
