package com.hon.conquer.vo.news;

import com.google.gson.annotations.SerializedName;
import com.hon.optimizedrecyclerviewlib.ItemType;

import java.util.List;

/**
 * Created by Frank on 2018/1/27.
 * E-mail:frank_hon@foxmail.com
 */

public class ZhihuDailyNewsDetail implements ItemType{

    @SerializedName("images")
    private List<String> images;
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("ga_prefix")
    private String gaPrefix;
    @SerializedName("title")
    private String title;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
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

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int itemType() {
        return ItemType.NEWS_ITEM_TYPE;
    }
}
