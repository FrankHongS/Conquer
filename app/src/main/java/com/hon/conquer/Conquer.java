package com.hon.conquer;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by Frank on 2018/1/31.
 * E-mail:frank_hon@foxmail.com
 */

public class Conquer extends Application{

    public static Context sConquer;

    private static String sCacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        sConquer=getApplicationContext();

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            sCacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            sCacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAppCacheDir() {
        return sCacheDir;
    }
}
