package com.scottfu.brightbook.homepage;

import com.scottfu.brightbook.bean.DoubanNews;
import com.scottfu.sflibrary.mvp.BasePresenter;
import com.scottfu.sflibrary.mvp.BaseView;

import java.util.ArrayList;

/**
 * Created by fujindong on 2017/3/15.
 */

public interface DoubanContract {

    interface View extends BaseView<Presenter> {

        void startLoading();

        void stopLoading();

        void showLoadingError();

        void showResults(ArrayList<DoubanNews.posts> list);

    }

    interface Presenter extends BasePresenter {

        void startReading(int position);

        void loadPosts(long date, boolean clearing);

        void refresh();

        void loadMore(long date);

        void feelLucky();

    }
}
