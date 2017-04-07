package com.scottfu.brightbook.NewsDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scottfu.brightbook.R;
import com.scottfu.brightbook.api.BrightBookAPI;
import com.scottfu.brightbook.bean.BeanType;
import com.scottfu.brightbook.bean.DoubanNews;
import com.scottfu.brightbook.bean.DoubanStory;
import com.scottfu.brightbook.bean.StringModelImpl;
import com.scottfu.brightbook.bean.ZhihuDailyStory;
import com.scottfu.brightbook.db.DatabaseHelper;
import com.scottfu.brightbook.interfacebk.OnStringListener;
import com.scottfu.sflibrary.util.LogUtil;
import com.scottfu.sflibrary.util.NetworkState;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by fujindong on 2017/3/23.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private static final String TAG = "---NewsDetailPresenter---";

    private NewsDetailContract.View mView;
    private Context mContext;
    private StringModelImpl model;

    private ZhihuDailyStory zhihuDailyStory;
    private DoubanStory mDoubanStory;
    private Gson gson;

    private SharedPreferences sp;
    private DatabaseHelper mdbHelper;

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
        sp = context.getSharedPreferences("user_settings", MODE_PRIVATE);
        mdbHelper = new DatabaseHelper(mContext);
        gson = new Gson();
        model = new StringModelImpl(context);

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
        if (id == 0 || mType == null) {
            mView.showLoadingError();
            return;
        }

        mView.showLoading();
        mView.setTitle(title);
        mView.showCover(coverUrl);

        // set the web view whether to show the image
        mView.setImageMode(sp.getBoolean("no_picture_mode",false));

        switch (mType) {
            case TYPE_ZHIHU:
                if (NetworkState.networkConnected(mContext)) {
                    model.load(BrightBookAPI.ZHIHU_NEWS + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            {
                                Gson gson = new Gson();
                                try {
                                    zhihuDailyStory = gson.fromJson(result, ZhihuDailyStory.class);
                                    if (zhihuDailyStory.getBody() == null) {
                                        mView.showResultWithoutBody(zhihuDailyStory.getShare_url());
                                    } else {
                                        mView.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                                    }
                                } catch (JsonSyntaxException e) {
                                    mView.showLoadingError();
                                }
                                mView.stopLoading();
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            mView.stopLoading();
                            mView.showLoadingError();
                        }
                    });
                } else {
                    Cursor cursor = mdbHelper.getReadableDatabase()
                            .query("Zhihu", null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            if (cursor.getInt(cursor.getColumnIndex("zhihu_id")) == id) {
                                String content = cursor.getString(cursor.getColumnIndex("zhihu_content"));
                                try {
                                    zhihuDailyStory = gson.fromJson(content, ZhihuDailyStory.class);
                                } catch (JsonSyntaxException e) {
                                    mView.showResult(content);
                                }
                                mView.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;

//            case TYPE_GUOKR:
//                if (NetworkState.networkConnected(context)) {
//                    model.load(Api.GUOKR_ARTICLE_LINK_V1 + id, new OnStringListener() {
//                        @Override
//                        public void onSuccess(String result) {
//                            convertGuokrContent(result);
//                            view.showResult(guokrStory);
//                        }
//
//                        @Override
//                        public void onError(VolleyError error) {
//                            view.showLoadingError();
//                        }
//                    });
//                } else {
//                    Cursor cursor = dbHelper.getReadableDatabase()
//                            .query("Guokr", null, null, null, null, null, null);
//                    if (cursor.moveToFirst()) {
//                        do {
//                            if (cursor.getInt(cursor.getColumnIndex("guokr_id")) == id) {
//                                guokrStory = cursor.getString(cursor.getColumnIndex("guokr_content"));
//                                convertGuokrContent(guokrStory);
//                                view.showResult(guokrStory);
//                                break;
//                            }
//                        } while (cursor.moveToNext());
//                    }
//                    cursor.close();
//                }
//                break;

            case TYPE_DOUBAN:
                if (NetworkState.networkConnected(mContext)) {
                    model.load(BrightBookAPI.DOUBAN_ARTICLE_DETAIL + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                mDoubanStory = gson.fromJson(result, DoubanStory.class);
                                mView.showResult(convertDoubanContent());
                            } catch (JsonSyntaxException e) {
                                mView.showLoadingError();
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            mView.showLoadingError();
                        }
                    });
                } else {
                    Cursor cursor = mdbHelper.getReadableDatabase()
                            .rawQuery("select douban_content from Douban where douban_id = " + id, null);
                    if (cursor.moveToFirst()) {
                        do {
                            if (cursor.getCount() == 1) {
                                mDoubanStory = gson.fromJson(cursor.getString(0), DoubanStory.class);
                                mView.showResult(convertDoubanContent());
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    break;

                }
            default:
                mView.stopLoading();
                mView.showLoadingError();
                break;
        }

        mView.stopLoading();
    }



    private String convertZhihuContent(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // in fact,in api,css addresses are given as an array
        // api中还有js的部分，这里不再解析js
        // javascript is included,but here I don't use it
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        // use the css file from local assets folder,not from network
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        // load content judging by different theme
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((mContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }


    private String convertDoubanContent() {

        if (mDoubanStory.getContent() == null) {
            return null;
        }
        String css;
        if ((mContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES) {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_dark.css\" type=\"text/css\">";
        } else {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_light.css\" type=\"text/css\">";
        }
        String content = mDoubanStory.getContent();
        ArrayList<DoubanNews.posts.thumbs> imageList = mDoubanStory.getPhotos();
        for (int i = 0; i < imageList.size(); i++) {
            String old = "<img id=\"" + imageList.get(i).getTag_name() + "\" />";
            String newStr = "<img id=\"" + imageList.get(i).getTag_name() + "\" "
                    + "src=\"" + imageList.get(i).getMedium().getUrl() + "\"/>";
            content = content.replace(old, newStr);
        }
        StringBuilder builder = new StringBuilder();
        builder.append( "<!DOCTYPE html>\n");
        builder.append("<html lang=\"ZH-CN\" xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        builder.append("<head>\n<meta charset=\"utf-8\" />\n");
        builder.append(css);
        builder.append("\n</head>\n<body>\n");
        builder.append("<div class=\"container bs-docs-container\">\n");
        builder.append("<div class=\"post-container\">\n");
        builder.append(content);
        builder.append("</div>\n</div>\n</body>\n</html>");

        return builder.toString();
    }


}
