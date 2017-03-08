package com.scottfu.brightbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.common.collect.HashBiMap;
import com.scottfu.sflibrary.net.CloudClient;
import com.scottfu.sflibrary.net.JSONResultHandler;
import com.scottfu.sflibrary.util.ToastManager;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


//    private MainFragment mainFragment;
//    private BookmarksFragment bookmarksFragment;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    public static final String ACTION_BOOKMARKS = "com.marktony.zhihudaily.bookmarks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();


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
//            showMainFragment();
            ToastManager.showToast(this,"主页");
            getDataV2();
        } else if (id == R.id.nav_bookmarks) {
//            showBookmarksFragment();
            ToastManager.showToast(this,"收藏");
        } else if (id == R.id.nav_change_theme) {

            // change the day/night mode after the drawer closed
            drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    SharedPreferences sp =  getSharedPreferences("user_settings",MODE_PRIVATE);
                    if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                            == Configuration.UI_MODE_NIGHT_YES) {
                        sp.edit().putInt("theme", 0).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        sp.edit().putInt("theme", 1).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    recreate();
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });

        } else if (id == R.id.nav_settings) {
//            startActivity(new Intent(this,SettingsPreferenceActivity.class));
        } else if (id == R.id.nav_about) {
//            startActivity(new Intent(this,AboutPreferenceActivity.class));
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //在manifest 中设置configchanges 之后 当屏幕旋转的时候就直接调用onConfigurationChanged 而不要走oncreate
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

    private void getDataV2() {
        String url = "https://www.iyxt.com.cn/API/GetStationList.aspx";
        HashMap<String, String> map = new HashMap<>();
        map.put("DeviceId", "Android");
        map.put("Key", "83b1b558b2b37934010f33b56e22f239");
        JSONObject js = new JSONObject(map);
        CloudClient.doHttpRequestV2(MainActivity.this, url, js, new JSONResultHandler() {
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

}
