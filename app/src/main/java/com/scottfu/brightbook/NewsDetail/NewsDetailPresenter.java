package com.scottfu.brightbook.NewsDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import com.scottfu.brightbook.bean.BeanType;
import com.scottfu.brightbook.db.DatabaseHelper;
import com.scottfu.sflibrary.util.LogUtil;

/**
 * Created by fujindong on 2017/3/23.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private static final String TAG = "---NewsDetailPresenter---";

    private NewsDetailContract.View mView;
    private Context mContext;



    private BeanType mType;
    private int id;
    private String title;
    private String coverUrl;


    public void setmType(BeanType mType) {
        this.mType = mType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public NewsDetailPresenter(@NonNull Context context, @NonNull NewsDetailContract.View view) {
        LogUtil.e(TAG, "---NewsDetailPresenter Instance---");
        mContext = context;
        mView = view;
        view.setPresenter(this);

    }


    @Override
    public void start() {

    }

    @Override
    public void openInBrowser() {

    }

    @Override
    public void shareAsText() {

    }

    @Override
    public void openUrl(WebView webView, String url) {

    }

    @Override
    public void copyText() {

    }

    @Override
    public void copyLink() {

    }

    @Override
    public void addToOrDeleteFromBookmarks() {

    }

    @Override
    public boolean queryIfIsBookmarked() {
        return false;
    }

    @Override
    public void requestData() {

    }
}
