package com.hon.conquer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

import com.hon.conquer.Conquer;

/**
 * Created by Frank on 2018/2/12.
 * E-mail:frank_hon@foxmail.com
 */

public class Util {
    private Util(){}

    public static int px2dip(int pxValue){
        final float scale = Conquer.sConquer.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(int dipValue){
        final float scale = Conquer.sConquer.getResources().getDisplayMetrics().density;
        return (int) (dipValue*scale + 0.5f);
    }

    public static int getColor(int resId){
        return Conquer.sConquer.getResources().getColor(resId);
    }

    public static int getScreenHeight(){
        DisplayMetrics dm = Conquer.sConquer.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getScreenWidth(){
        DisplayMetrics dm = Conquer.sConquer.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }


    /**
     * Check network state
     * @param context
     * @return true if connected,otherwise false
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
