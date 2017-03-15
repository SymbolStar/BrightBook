package com.scottfu.brightbook.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scottfu.brightbook.homepage.DoubanFragment;
import com.scottfu.brightbook.homepage.ZhihuDailyFragment;

/**
 * Created by fujindong on 2017/3/14.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {


    private String[] titles;
    private final Context context;

    private ZhihuDailyFragment zhihuDailyFragment;
    private DoubanFragment doubanFragment;

    public ZhihuDailyFragment getZhihuFragment() {
        return zhihuDailyFragment;
    }
    public DoubanFragment getDoubanFragment() {
        return doubanFragment;
    }

    public MainPagerAdapter(FragmentManager fm, Context context, ZhihuDailyFragment zhihuDailyFragment, DoubanFragment doubanFragment) {
        super(fm);
        this.context = context;
        titles = new String[]{"知乎日报","豆瓣一刻"};

        this.zhihuDailyFragment = zhihuDailyFragment;
        this.doubanFragment = doubanFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return doubanFragment;
        }

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
