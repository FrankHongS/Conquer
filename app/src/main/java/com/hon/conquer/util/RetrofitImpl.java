package com.hon.conquer.util;

import com.hon.conquer.BuildConfig;
import com.hon.conquer.Conquer;
import com.hon.conquer.api.NewsService;
import com.hon.conquer.vo.news.ZhihuDailyContent;
import com.hon.conquer.vo.news.ZhihuDailyNews;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Frank on 2018/1/31.
 * E-mail:frank_hon@foxmail.com
 */

public class RetrofitImpl {

    private static volatile RetrofitImpl sInstance;

    private Retrofit.Builder mNewsRetrofitBuilder;

    private static final String NEWS_CACHE_DIR = "zhihudailynews";
    private static final String NEWS_CONTENT_CACHE_DIR = "zhihudailynewscontent";

    private RetrofitImpl(){
        mNewsRetrofitBuilder=new Retrofit.Builder()
                .baseUrl(NewsService.NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static RetrofitImpl getInstance(){
        if(sInstance==null){
            synchronized (RetrofitImpl.class){
                if(sInstance==null){
                    sInstance=new RetrofitImpl();
                }
            }
        }

        return sInstance;
    }

    public Observable<ZhihuDailyNews> getNews(String date){
        return buildNewsService(NEWS_CACHE_DIR)
                .getNews(date);
    }

    public Observable<ZhihuDailyContent> getNewsContent(int id){
        return buildNewsService(NEWS_CONTENT_CACHE_DIR)
                .getNewsContent(id);
    }

    private NewsService buildNewsService(String cacheDir){
        return mNewsRetrofitBuilder
                .client(buildNewsOkHttpClient(cacheDir))
                .build()
                .create(NewsService.class);
    }

    /**
     * build the News OKHttpClient with the cache interceptor
     * @param dir cache directory
     * @return okHttpClient
     */

    private static OkHttpClient buildNewsOkHttpClient(String dir){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();

        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        File cacheFile=new File(Conquer.getAppCacheDir()+"/news/"+dir);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

        Interceptor cacheInterceptor=chain -> {
            Request request=chain.request();
            if (!Util.isNetworkConnected(Conquer.sConquer)) {
                int maxStale = 60 * 60 * 24 * 28;
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }else{
                int maxAge = 60*60*10;
                request = request.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            }
            Response response=chain.proceed(request);
            Response.Builder responseBuilder=response.newBuilder();
//            if (Util.isNetworkConnected(Conquer.sConquer)) {
//                int maxAge = 60*60*10;
//                responseBuilder
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .removeHeader("Pragma");
//            }
//            else {
//                int maxStale = 60 * 60 * 24 * 28;
//                responseBuilder
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .removeHeader("Pragma");
//            }

            return responseBuilder.build();
        };

        builder.cache(cache).addInterceptor(cacheInterceptor);

        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        builder.retryOnConnectionFailure(true);

        return builder.build();
    }
}
