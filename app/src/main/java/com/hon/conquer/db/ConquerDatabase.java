package com.hon.conquer.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.hon.conquer.Conquer;
import com.hon.conquer.vo.news.ZhihuDailyNewsDetail;

/**
 * Created by Frank_Hon on 9/28/2018.
 * E-mail: v-shhong@microsoft.com
 */

@Database(entities = {FavoriteNews.class},version = 1)
public abstract class ConquerDatabase extends RoomDatabase{

    private static final String DATABASE_NAME="conquer";
    private static volatile ConquerDatabase sInstance;

    public static ConquerDatabase getInstance(){
        if(sInstance==null){
            synchronized (ConquerDatabase.class){
                if (sInstance==null){
                    sInstance= Room.databaseBuilder(Conquer.sConquer,ConquerDatabase.class,DATABASE_NAME)
                            .build();
                }
            }
        }

        return sInstance;
    }

    public abstract NewsFavoritesDao newsFavoritesDao();
}
