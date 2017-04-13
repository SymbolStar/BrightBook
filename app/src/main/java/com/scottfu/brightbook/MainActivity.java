package com.scottfu.brightbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.common.collect.HashBiMap;
import com.scottfu.brightbook.service.CacheService;
import com.scottfu.brightbook.service.GrayService;
import com.scottfu.sflibrary.net.CloudClient;
import com.scottfu.sflibrary.net.JSONResultHandler;
import com.scottfu.sflibrary.util.StringUtil;
import com.scottfu.sflibrary.util.ToastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private MainFragment mainFragment;
//    private BookmarksFragment bookmarksFragment;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;


    private long exitTime = 0;

    public static final String ACTION_BOOKMARKS = "com.marktony.zhihudaily.bookmarks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent grayIntent = new Intent(getApplicationContext(), GrayService.class);
        startService(grayIntent);


        initViews();
        if (savedInstanceState != null) {
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "MainFragment");
        } else {
            mainFragment = MainFragment.newInstance();
        }

        if (!mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, mainFragment, "MainFragment")
                    .commit();
        }



        startService(new Intent(this, CacheService.class));


    }


    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            showMainFragment();
            ToastManager.showToast(this,"主页");
        } else if (id == R.id.nav_bookmarks) {
//            showBookmarksFragment();
            ToastManager.showToast(this,"收藏");
        } else if (id == R.id.nav_change_theme) {


        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        }

        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    //在manifest 中设置configchanges 之后 当屏幕旋转的时候就直接调用onConfigurationChanged 而不要走oncreate
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }












    private void showMainFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.commit();
        toolbar.setTitle(getResources().getString(R.string.app_name));

    }






    private void getData() {

        CloudClient.doHttpRequest(MainActivity.this, "http://news-at.zhihu.com/api/4/news/before/20170122", new JSONResultHandler() {
            @Override
            public void onSuccess(String jsonString) {
                ToastManager.showToast(MainActivity.this, jsonString);

            }

            @Override
            public void onError(VolleyError errorMessage) {
                ToastManager.showToast(MainActivity.this,"出错了");
            }
        });

    }




    private void getDataV3() {
        //云校通站点获取
        String url = "https://www.iyxt.com.cn/API/GetStationList.aspx";
        HashMap<String, Object> map = new HashMap<>();
        map.put("DeviceId", "Android");
        map.put("Key", "83b1b558b2b37934010f33b56e22f239");

        String json = StringUtil.hashMapToJson(map);
        HashMap<String, String> arg = new HashMap<>();
        arg.put("arg", json);

        CloudClient.doHttpRequestv3(arg, MainActivity.this, url, new JSONResultHandler() {
            @Override
            public void onSuccess(String jsonString) {
                ToastManager.showToast(MainActivity.this, jsonString);
            }

            @Override
            public void onError(VolleyError errorMessage) {
                ToastManager.showToast(MainActivity.this,"出错了");
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ToastManager.showToast(this,"back");
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            exit();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    //判断两次返回键的间隔时间来确定是否退出程序
    /** * 程序退出提醒 */
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
            ToastManager.showToast(this, "再按一次退出程序");
        } else {
            finish();
        }
    }
}
