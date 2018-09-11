package com.hon.conquer.vo.news;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Frank on 2018/1/27.
 * E-mail:frank_hon@foxmail.com
 */

public class ZhihuDailyNews {

    @SerializedName("date")
    private String date;
    @SerializedName("stories")
    private List<ZhihuDailyNewsDetail> strories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuDailyNewsDetail> getStrories() {
        return strories;
    }

    public void setStrories(List<ZhihuDailyNewsDetail> strories) {
        this.strories = strories;
    }
}
