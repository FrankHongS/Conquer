package com.hon.conquer.util;

import androidx.lifecycle.LiveData;
import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by Frank on 2018/1/28.
 * E-mail:frank_hon@foxmail.com
 */

public class LiveDataCallAdapterFactory extends CallAdapter.Factory{
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(getRawType(returnType)!= LiveData.class)
            return null;
        // 通过ParameterizedType获取泛型参数Class类型
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);

        if (! (observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType);
    }
}
