package com.hon.conquer.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hon.conquer.Conquer;

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
