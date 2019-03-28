package com.hon.conquer.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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

    @Query("SELECT * FROM news_favorites WHERE url_id=:urlId")
    List<FavoriteNews> loadNewsFavorites(int urlId);

    @Insert
    void insert(FavoriteNews newsDetail);

    @Delete
    void delete(FavoriteNews newsDetail);

}
