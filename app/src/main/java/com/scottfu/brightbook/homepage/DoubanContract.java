package com.scottfu.brightbook.homepage;

import com.google.common.reflect.Parameter;
import com.scottfu.sflibrary.mvp.BaseView;

/**
 * Created by fujindong on 2017/3/6.
 */

public interface DoubanContract {
    interface View extends BaseView<Parameter> {

        void startLoading();

        void stopLoading();

        void showLoadingError();

        void showResults();


    }
}
