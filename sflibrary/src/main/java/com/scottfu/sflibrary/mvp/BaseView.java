package com.scottfu.sflibrary.mvp;

import android.view.View;

/**
 * Created by fujindong on 2017/3/6.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void initViews(View view);
}
