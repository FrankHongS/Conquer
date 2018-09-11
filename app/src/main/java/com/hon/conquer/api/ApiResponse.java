package com.hon.conquer.api;

import java.io.IOException;

import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Frank on 2018/1/28.
 * E-mail:frank_hon@foxmail.com
 */

public class ApiResponse<R> {

    public final int code;
    public final R body;
    public final String errorMessage;

    public ApiResponse(Throwable t){
        code=500;
        body=null;
        errorMessage=t.getMessage();
    }

    public ApiResponse(Response<R> response){
        code=response.code();
        if(isSuccessful()){
            body=response.body();
            errorMessage=null;
        }else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Timber.e(ignored, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }
}
