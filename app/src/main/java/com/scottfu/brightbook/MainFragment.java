package com.scottfu.brightbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.scottfu.brightbook.adapter.MainPagerAdapter;
import com.scottfu.brightbook.homepage.DoubanFragment;
import com.scottfu.brightbook.homepage.DoubanPresenter;
import com.scottfu.brightbook.homepage.ZhihuDailyFragment;
import com.scottfu.brightbook.homepage.ZhihuDailyPresenter;

import java.util.Random;

/**
 * Created by fujindong on 2017/3/7.
 */

public class MainFragment extends Fragment {


    private Context mContext;
    private MainPagerAdapter mPageAdapter;

    private TabLayout tabLayout;

    private ZhihuDailyFragment zhihuDailyFragment;
    private DoubanFragment doubanMomentFragment;

    private ZhihuDailyPresenter zhihuDailyPresenter;
    private DoubanPresenter doubanMomentPresenter;

    public MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = getActivity();

        if (savedInstanceState != null) {
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(savedInstanceState, "zhihu");
//            guokrFragment = (GuokrFragment) manager.getFragment(savedInstanceState, "guokr");
            doubanMomentFragment = (DoubanFragment) manager.getFragment(savedInstanceState, "douban");
        } else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
//            guokrFragment = GuokrFragment.newInstance();
            doubanMomentFragment = DoubanFragment.newInstance();
        }

        zhihuDailyPresenter = new ZhihuDailyPresenter(mContext, zhihuDailyFragment);
//        guokrPresenter = new GuokrPresenter(mContext, guokrFragment);
        doubanMomentPresenter = new DoubanPresenter(mContext, doubanMomentFragment);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);

        setHasOptionsMenu(true);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                隐藏fab
//                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//                if (tab.getPosition() == 1) {
//                    fab.hide();
//                } else {
//                    fab.show();
//                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        return view;
    }


    private void initViews(View view) {

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);

        mPageAdapter = new MainPagerAdapter(
                getChildFragmentManager(),
                mContext,
                zhihuDailyFragment,
                doubanMomentFragment);

        viewPager.setAdapter(mPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_feel_lucky) {
            feelLucky();
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "zhihu", zhihuDailyFragment);
//        manager.putFragment(outState, "guokr", guokrFragment);
        manager.putFragment(outState, "douban", doubanMomentFragment);
    }

    public void feelLucky() {
        Random random = new Random();
        int type = random.nextInt(3);
        switch (type) {
            case 0:
                zhihuDailyPresenter.feelLucky();
                break;
            case 1:
//                guokrPresenter.feelLucky();
                break;
            default:
                doubanMomentPresenter.feelLucky();
                break;
        }
    }

    public MainPagerAdapter getmPageAdapter() {
        return mPageAdapter;
    }
}
