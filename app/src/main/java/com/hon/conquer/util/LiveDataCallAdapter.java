package com.hon.conquer.util;

import android.arch.lifecycle.LiveData;


import com.hon.conquer.api.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Frank on 2018/1/28.
 * E-mail:frank_hon@foxmail.com
 */

public class LiveDataCallAdapter<T> implements CallAdapter<T,LiveData<ApiResponse<T>>>{

    private final Type responseType;
    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<T>> adapt(Call<T> call) {
        return new LiveData<ApiResponse<T>>() {
            AtomicBoolean started=new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false,true)){
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(Call<T> call, Response<T> response) {
                            postValue(new ApiResponse<>(response));
                        }

                        @Override
                        public void onFailure(Call<T> call, Throwable t) {
                            postValue(new ApiResponse<>(t));
                        }
                    });
                }
            }
        };
    }
}
