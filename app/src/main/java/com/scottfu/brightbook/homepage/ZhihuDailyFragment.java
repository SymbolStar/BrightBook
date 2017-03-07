package com.scottfu.brightbook.homepage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scottfu.brightbook.R;
import com.scottfu.brightbook.bean.ZhihuDailyNews;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by fujindong on 2017/3/7.
 */

public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View {


    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;
    private FloatingActionButton fab;
    private TabLayout tabLayout;

//    TODO RecyclerView adapter
//    private ZhihuDailyNewsAdapter adapter;

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private ZhihuDailyContract.Presenter presenter;

    public ZhihuDailyFragment() {}

    public static ZhihuDailyFragment newInstance() {
        return new ZhihuDailyFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_list,container,false);

        initViews(view);

        presenter.start();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                presenter.refresh();
            }

        });



        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Story> list) {

    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }
}
