package com.scottfu.brightbook.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scottfu.brightbook.api.BrightBookAPI;
import com.scottfu.brightbook.bean.DoubanNews;
import com.scottfu.brightbook.bean.StringModelImpl;
import com.scottfu.brightbook.db.DatabaseHelper;
import com.scottfu.brightbook.interfacebk.OnStringListener;
import com.scottfu.brightbook.service.CacheService;
import com.scottfu.sflibrary.util.DateFormatter;
import com.scottfu.sflibrary.util.NetworkState;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by fujindong on 2017/3/15.
 */


public class DoubanPresenter implements DoubanContract.Presenter {

    private DoubanContract.View view;
    private Context context;
    private StringModelImpl model;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Gson gson = new Gson();

    private ArrayList<DoubanNews.posts> list = new ArrayList<>();

    public DoubanPresenter(Context context, DoubanContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void startReading(int position) {
//        DoubanNews.posts item = list.get(position);
//        Intent intent = new Intent(context, DetailActivity.class);
//
//        intent.putExtra("type", BeanType.TYPE_DOUBAN);
//        intent.putExtra("id", item.getId());
//        intent.putExtra("title", item.getTitle());
//        if (item.getThumbs().size() == 0){
//            intent.putExtra("coverUrl", "");
//        } else {
//            intent.putExtra("coverUrl", item.getThumbs().get(0).getMedium().getUrl());
//        }
//        context.startActivity(intent);
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {

        if (clearing) {
            view.startLoading();
        }

        if (NetworkState.networkConnected(context)) {

            model.load(BrightBookAPI.DOUBAN_MOMENT + new DateFormatter().DoubanDateFormat(date), new OnStringListener() {
                @Override
                public void onSuccess(String result) {

                    try {
                        DoubanNews post = gson.fromJson(result, DoubanNews.class);
                        ContentValues values = new ContentValues();

                        if (clearing) {
                            list.clear();
                        }

                        for (DoubanNews.posts item : post.getPosts()) {

                            list.add(item);

                            if ( !queryIfIDExists(item.getId())) {
                                db.beginTransaction();
                                try {
                                    values.put("douban_id", item.getId());
                                    values.put("douban_news", gson.toJson(item));
                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = format.parse(item.getPublished_time());
                                    values.put("douban_time", date.getTime() / 1000);
                                    values.put("douban_content", "");
                                    db.insert("Douban", null, values);
                                    values.clear();
                                    db.setTransactionSuccessful();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.endTransaction();
                                }
                            }
                            Intent intent = new Intent("com.marktony.zhihudaily.LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_DOUBAN);
                            intent.putExtra("id", item.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                        view.showResults(list);
                    } catch (JsonSyntaxException e) {
                        view.showLoadingError();
                    }

                    view.stopLoading();

                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showLoadingError();
                }
            });
        } else {

            if (clearing) {

                list.clear();

                Cursor cursor = db.query("Douban", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        DoubanNews.posts post = gson.fromJson(cursor.getString(cursor.getColumnIndex("douban_news")), DoubanNews.posts.class);
                        list.add(post);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                view.stopLoading();
                view.showResults(list);
            }
        }
    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void feelLucky() {
        if (list.isEmpty()) {
            view.showLoadingError();
            return;
        }
        startReading(new Random().nextInt(list.size()));
    }

    @Override
    public void start() {
        refresh();
    }

    private boolean queryIfIDExists(int id){
        Cursor cursor = db.query("Douban",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if (id == cursor.getInt(cursor.getColumnIndex("douban_id"))){
                    Log.e("DoubanPresenter", "---------douban_id--------"+String.valueOf(id));
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }

}

