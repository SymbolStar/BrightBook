package com.scottfu.brightbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.scottfu.sflibrary.util.LogUtil;

/**
 * Created by fujindong on 2017/4/11.
 */

public class WelcomeActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.e(TAG,"----------------");
                initBrightBook();
            }

        }, 2 * 1000);


    }

    private void initBrightBook() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
