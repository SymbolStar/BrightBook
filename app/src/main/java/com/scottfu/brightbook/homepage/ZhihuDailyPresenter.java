package com.scottfu.brightbook.homepage;

import android.content.Context;

import com.google.gson.Gson;
import com.scottfu.brightbook.bean.ZhihuDailyNews;
import com.scottfu.sflibrary.net.CloudClient;
import com.scottfu.sflibrary.util.DateFormatter;

import java.util.ArrayList;

/**
 * Created by fujindong on 2017/3/6.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private ZhihuDailyContract.View mView;
    private Context context;
    private CloudClient mCloudClient;


    private DateFormatter formatter = new DateFormatter();
    private Gson gson = new Gson();

    private ArrayList<ZhihuDailyNews.Story> mZhihuStoryList = new ArrayList<>();

//    TODO 数据库

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view){
        this.context = context;
        mView = view;
        mView.setPresenter(this);
        mCloudClient = new CloudClient(context);
        //数据库相关操作

    }

    @Override
    public void loadPosts(long date, boolean clearing) {

        if (clearing) {
            mView.showLoading();
        }


    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void startReading(int Position) {

    }

    @Override
    public void feelLucky() {

    }

    @Override
    public void start() {

    }
}
