package com.scottfu.brightbook;

import android.app.Application;
import android.content.Intent;

import com.scottfu.brightbook.service.BackgroundService;
import com.scottfu.brightbook.service.GrayService;

/**
 * Created by fujindong on 2017/3/6.
 */

public class BrightBookApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        Intent bgIntent = new Intent(getApplicationContext(), BackgroundService.class);
//        startService(bgIntent);
//        Intent grayIntent = new Intent(getApplicationContext(), GrayService.class);
//        startService(grayIntent);
    }
}
