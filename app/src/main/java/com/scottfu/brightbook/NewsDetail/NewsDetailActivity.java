package com.scottfu.brightbook.NewsDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.scottfu.brightbook.R;

/**
 * Created by fujindong on 2017/3/23.
 */

public class NewsDetailActivity extends AppCompatActivity{

    private static final String TAG = "---NewsDetailActivity---";

    private NewsDetailFragment mFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        if (savedInstanceState != null) {
            mFragment = (NewsDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "newsDetailFragment");
        } else {
            mFragment = new NewsDetailFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, mFragment).commit();
        }

        NewsDetailPresenter presenter = new NewsDetailPresenter(NewsDetailActivity.this,mFragment);


//       TODO 获取Intent 传过来的值

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,"newsDetailFragment",mFragment);
        }

    }
}
