package com.xiayule.workwithclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.xiayule.workwithclient.R;
import com.xiayule.workwithclient.view.titanic.Titanic;
import com.xiayule.workwithclient.view.titanic.TitanicTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WelcomeActivity extends BaseActivity {

    @InjectView(R.id.titanic_tv)
    TitanicTextView titanicTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        actionBar.hide();

        ButterKnife.inject(this);

        new Titanic().start(titanicTextView);

        Thread thread = new Thread() {
            @Override
            public void run() {
                int waitingTime = 5000; // ms
                try {
                    Thread.sleep(waitingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);  // enter the main activity finally
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);

                    finish();
                }
            }
        };

        thread.start();


    }



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
