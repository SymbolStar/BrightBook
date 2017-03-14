package com.scottfu.brightbook.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scottfu.brightbook.homepage.ZhihuDailyFragment;

/**
 * Created by fujindong on 2017/3/14.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {


    private String[] titles;
    private final Context context;

    private ZhihuDailyFragment zhihuDailyFragment;

    public ZhihuDailyFragment getZhihuFragment() {
        return zhihuDailyFragment;
    }


    public MainPagerAdapter(FragmentManager fm, Context context, ZhihuDailyFragment zhihuDailyFragment) {
        super(fm);
        this.context = context;
        titles = new String[]{"知乎日报"};

        this.zhihuDailyFragment = zhihuDailyFragment;

    }

    @Override
    public Fragment getItem(int position) {

        return zhihuDailyFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
