package com.scottfu.brightbook.homepage;

import com.scottfu.brightbook.bean.ZhihuDailyNews;
import com.scottfu.sflibrary.mvp.BasePresenter;
import com.scottfu.sflibrary.mvp.BaseView;

import java.util.ArrayList;

/**
 * Created by fujindong on 2017/3/6.
 */

public interface ZhihuDailyContract {

    interface View extends BaseView<Presenter> {
        void showError();

        void showLoading();

        void stopLoading();

        void showResults(ArrayList<ZhihuDailyNews.Story> list);
    }

    interface Presenter extends BasePresenter{
        void loadPosts(long date, boolean clearing);

        void refresh();

        void loadMore(long date);

        void startReading(int Position);

        void feelLucky();
    }
}

