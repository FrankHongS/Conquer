package com.hon.conquer.util;

import android.widget.Toast;

import com.hon.conquer.Conquer;

/**
 * Created by Frank on 2018/1/31.
 * E-mail:frank_hon@foxmail.com
 */

public class ToastUtil {
    private ToastUtil(){}
    private static volatile Toast sToast;

    public static void showToast(String content){
        if(sToast==null){
            synchronized (ToastUtil.class){
                if(sToast==null){
                    sToast=Toast.makeText(Conquer.sConquer,content,Toast.LENGTH_LONG);
                }else{
                    sToast.setText(content);
                }
            }
        }else {
            sToast.setText(content);
        }

        sToast.show();
    }
}
