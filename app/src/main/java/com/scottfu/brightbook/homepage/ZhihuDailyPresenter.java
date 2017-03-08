package com.scottfu.brightbook.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scottfu.brightbook.api.BrightBookAPI;
import com.scottfu.brightbook.bean.ZhihuDailyNews;
import com.scottfu.brightbook.db.DatabaseHelper;
import com.scottfu.sflibrary.net.CloudClient;
import com.scottfu.sflibrary.net.JSONResultHandler;
import com.scottfu.sflibrary.util.DateFormatter;
import com.scottfu.sflibrary.util.NetworkState;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by fujindong on 2017/3/6.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private ZhihuDailyContract.View mView;
    private Context mContext;


    private DateFormatter formatter = new DateFormatter();
    private Gson gson = new Gson();

    private ArrayList<ZhihuDailyNews.Story> mZhihuStoryList = new ArrayList<>();

    //    TODO 数据库
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mdb;

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view){
        this.mContext = context;
        mView = view;
        mView.setPresenter(this);
        //数据库相关操作
        mDbHelper = new DatabaseHelper(mContext);
        mdb = mDbHelper.getWritableDatabase();
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
//
//        if (clearing) {
//            mView.showLoading();
//        }
//        if (NetworkState.networkConnected(mContext)) {
//            mCloudClient.doHttpRequest(BrightBookAPI.ZHIHU_HISTORY + formatter.ZhihuDailyDateFormat(date), new JSONResultHandler() {
//                @Override
//                public void onSuccess(String jsonString) {
//                    try {
//                        ZhihuDailyNews zhihuDailyNews = gson.fromJson(jsonString, ZhihuDailyNews.class);
//                        ContentValues values = new ContentValues();
//                        if (clearing) {
//                            mZhihuStoryList.clear();
//                        }
//                        for (ZhihuDailyNews.Story item :
//                                zhihuDailyNews.getStories()) {
//                            mZhihuStoryList.add(item);
//                            if (!queryIfIDExists(item.getId())) {
//                                mdb.beginTransaction();
//                                try {
//                                    DateFormat format = new SimpleDateFormat("yyyyMMdd");
//                                    Date date = format.parse(zhihuDailyNews.getDate());
//                                    values.put("zhihu_id", item.getId());
//                                    values.put("zhihu_news", gson.toJson(item));
//                                    values.put("zhihu_content", "");
//                                    values.put("zhihu_time", date.getTime() / 1000);
//                                    mdb.insert("Zhihu", null, values);
//                                    mdb.setTransactionSuccessful();
//                                    values.clear();
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                } finally {
//                                    mdb.endTransaction();
//                                }
//                            }
//
////                        TODO Local_broadcast
//
//                        }
//                        mView.showResults(mZhihuStoryList);
//                    } catch (JsonSyntaxException e) {
//                        mView.showError();
//                    }
//                    mView.stopLoading();
//                }
//
//                @Override
//                public void onError(VolleyError errorMessage) {
//                    mView.stopLoading();
//                    mView.showError();
//                }
//            });
//        } else {
//            if (clearing) {
//                mZhihuStoryList.clear();
//                Cursor cursor = mdb.query("Zhihu", null, null, null, null, null, null);
//                if (cursor.moveToFirst()) {
//                    do {
//                        ZhihuDailyNews.Story story = gson.fromJson(cursor.getString(cursor.getColumnIndex("zhihu_news")), ZhihuDailyNews.Story.class);
//                        mZhihuStoryList.add(story);
//
//                    } while (cursor.moveToNext());
//                }
//                cursor.close();
//                mView.stopLoading();
//                mView.showResults(mZhihuStoryList);
//            } else {
//                mView.showError();
//            }
//
//        }


    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(),true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);

    }

    @Override
    public void startReading(int Position) {

//        TODO 跳到阅读页
    }

    @Override
    public void feelLucky() {

        if (mZhihuStoryList.isEmpty()) {
            mView.showError();
            return;
        }
        startReading(new Random().nextInt(mZhihuStoryList.size()));
    }

    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(),true);
    }

//TODO 添加到DBManager 中
    private boolean queryIfIDExists(int id) {
        Cursor cursor = mdb.query("Zhihu", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (id == cursor.getInt(cursor.getColumnIndex("zhihu_id"))) {
                    return true;
                }
            } while (cursor.moveToNext());

        }
        cursor.close();
        return false;
    }
}
