package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.util.ToastUtils;

public class AddProjectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_save) {
            ToastUtils.showShort("addprojectactivity click");
            setResult(1);
            this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
