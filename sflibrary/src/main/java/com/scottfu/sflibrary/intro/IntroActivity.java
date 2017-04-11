package com.scottfu.sflibrary.intro;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.scottfu.sflibrary.R;

/**
 * Created by fujindong on 2017/4/11.
 */

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      可以添加自定义fragment 或者 使用包里面的

        addSlide(AppIntroFragment.newInstance("welcome!","BrightBook", R.drawable.ic_slide1, Color.parseColor("#00BCD4")));
        addSlide(AppIntroFragment.newInstance("welcome!","BrightBook", R.drawable.ic_slide2, Color.parseColor("#00BCD4")));
        addSlide(AppIntroFragment.newInstance("welcome!","BrightBook", R.drawable.ic_slide3, Color.parseColor("#00BCD4")));
        addSlide(AppIntroFragment.newInstance("welcome!","BrightBook", R.drawable.ic_slide4, Color.parseColor("#00BCD4")));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
