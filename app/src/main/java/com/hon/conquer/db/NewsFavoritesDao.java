package com.hon.conquer.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.hon.conquer.vo.news.ZhihuDailyNewsDetail;

import java.util.List;

/**
 * Created by Frank_Hon on 9/28/2018.
 * E-mail: v-shhong@microsoft.com
 */

@Dao
public interface NewsFavoritesDao {

    @Query("SELECT * FROM news_favorites")
    List<FavoriteNews> loadNewsFavorites();

    @Query("SELECT * FROM news_favorites WHERE newsId<:newsId ORDER BY newsId DESC LIMIT :limit")
    List<FavoriteNews> loadNewsFavorites(int newsId, int limit);

    @Insert
    void insert(FavoriteNews newsDetail);

    @Delete
    void delete(FavoriteNews newsDetail);

}
