package com.hon.conquer.api;

import com.hon.conquer.vo.news.ZhihuDailyContent;
import com.hon.conquer.vo.news.ZhihuDailyNews;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Frank on 2018/1/27.
 * E-mail:frank_hon@foxmail.com
 */

public interface NewsService {

    String NEWS_BASE_URL="https://news-at.zhihu.com/api/4/news/";

    @GET("before/{date}")
    Observable<ZhihuDailyNews> getNews(@Path("date") String date);

    @GET("{id}")
    Observable<ZhihuDailyContent> getNewsContent(@Path("id") int id);
}
