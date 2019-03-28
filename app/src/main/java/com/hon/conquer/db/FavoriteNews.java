package com.hon.conquer.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hon.conquer.vo.news.ZhihuDailyNewsDetail;

/**
 * Created by Frank_Hon on 9/28/2018.
 * E-mail: v-shhong@microsoft.com
 */

@Entity(tableName = "news_favorites")
public class FavoriteNews {

    @PrimaryKey(autoGenerate = true)
    private int newsId;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "url_id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    public FavoriteNews(){}

    public FavoriteNews(ZhihuDailyNewsDetail newsDetail){
        this.image=newsDetail.getImages().get(0);
        this.id=newsDetail.getId();
        this.title=newsDetail.getTitle();
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
