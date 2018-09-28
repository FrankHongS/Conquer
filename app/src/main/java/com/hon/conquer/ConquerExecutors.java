package com.hon.conquer;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Frank_Hon on 9/28/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class ConquerExecutors {

    private static volatile ConquerExecutors sInstance;

    private final Executor diskExecutors;

    private final Executor ioExecutors;

    private final Executor mainExecutor;

    private ConquerExecutors(){
        this.diskExecutors= Executors.newSingleThreadExecutor();
        this.ioExecutors=Executors.newFixedThreadPool(5);
        this.mainExecutor=new MainExecutor();
    }

    public static ConquerExecutors getInstance(){
        if(sInstance==null){
            synchronized (ConquerExecutors.class){
                if(sInstance==null){
                    sInstance=new ConquerExecutors();
                }
            }
        }

        return sInstance;
    }

    public Executor getDiskExecutors() {
        return diskExecutors;
    }

    public Executor getIoExecutors() {
        return ioExecutors;
    }

    public Executor getMainExecutor() {
        return mainExecutor;
    }

    private static class MainExecutor implements Executor{

        private static final Handler HANDLER=new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            HANDLER.post(command);
        }
    }
}
