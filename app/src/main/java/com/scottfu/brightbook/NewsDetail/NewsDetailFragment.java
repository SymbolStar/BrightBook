package com.scottfu.brightbook.NewsDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scottfu.brightbook.R;
import com.scottfu.sflibrary.util.LogUtil;

/**
 * Created by fujindong on 2017/3/23.
 */

public class NewsDetailFragment extends Fragment implements NewsDetailContract.View {


    private static final String TAG = "---NewsDetailFragment---";
    private Context mContext;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mToolbarImageView;
    private WebView mNewsWebView;
    private NestedScrollView mContentNestedScrollView;
    private Toolbar mDetailToolbar;

    private NewsDetailContract.Presenter mPresenter;

    public NewsDetailFragment() {

    }


    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        LogUtil.e(TAG,"---onCreate---");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LogUtil.e(TAG,"---onCreateView---");
        View view = inflater.inflate(R.layout.fragment_universal_read_layout, container, false);
        initViews(view);
        setHasOptionsMenu(true);

        mPresenter.requestData();


        return view;

    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
            LogUtil.e(TAG,"---setPresenter---");
        }

    }

    @Override
    public void initViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mNewsWebView = (WebView) view.findViewById(R.id.wv_news);
        mDetailToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mNewsWebView.setScrollbarFadingEnabled(true);
        NewsDetailActivity activity = (NewsDetailActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbarImageView = (ImageView) view.findViewById(R.id.iv_toolbar_image);
        mContentNestedScrollView = (NestedScrollView) view.findViewById(R.id.nsv_content);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);


        //  回退
        mDetailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().finish();
            }
        });

        mDetailToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentNestedScrollView.smoothScrollTo(0,0);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestData();
            }
        });

        mNewsWebView.getSettings().setJavaScriptEnabled(true);
        mNewsWebView.getSettings().setBuiltInZoomControls(false);
        mNewsWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mNewsWebView.getSettings().setDomStorageEnabled(true);
        mNewsWebView.getSettings().setAppCacheEnabled(false);
        mNewsWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mPresenter.openUrl(view, url);
                return true;
            }
        });

    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showSharingError() {

    }

    @Override
    public void showResult(String result) {
        mNewsWebView.loadDataWithBaseURL("x-data://base",result,"text/html","utf-8",null);
    }

    @Override
    public void showResultWithoutBody(String url) {
        mNewsWebView.loadUrl(url);
    }

    @Override
    public void showCover(String url) {
        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .error(R.drawable.placeholder)
                .into(mToolbarImageView);
    }

    @Override
    public void setTitle(String title) {
        setmCollapsingToolbarLayoutTitle(title);

    }

    @Override
    public void setImageMode(boolean showImage) {
        mNewsWebView.getSettings().setBlockNetworkImage(showImage);
    }

    @Override
    public void showBrowserNotFoundError() {

    }

    @Override
    public void showTextCopied() {

    }

    @Override
    public void showCopyTextError() {

    }

    @Override
    public void showAddedToBookmarks() {

    }

    @Override
    public void showDeletedFromBookmarks() {

    }


    private void setmCollapsingToolbarLayoutTitle(String title) {
        mCollapsingToolbarLayout.setTitle(title);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }
}
