package com.scottfu.brightbook.bean;

import java.util.ArrayList;

/**
 * Created by fujindong on 2017/3/6.
 *
 * url http://news-at.zhihu.com/api/4/news/before/20170122
 */

public class ZhihuDailyNews {
    private String date;
    private ArrayList<Story> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Story> stories) {
        this.stories = stories;
    }

    public class Story{
        private ArrayList<String> images;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
