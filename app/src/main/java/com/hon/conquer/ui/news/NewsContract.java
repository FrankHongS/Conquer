package com.hon.conquer.ui.news;

import com.hon.conquer.base.BasePresenter;
import com.hon.conquer.base.BaseView;
import com.hon.conquer.vo.news.ZhihuDailyNews;

/**
 * Created by Frank_Hon on 3/28/2019.
 * E-mail: v-shhong@microsoft.com
 */
public interface NewsContract {

    interface View extends BaseView<Presenter>{

        void stopRefreshing();

        void startRefreshing();

        void showNews(ZhihuDailyNews news,boolean initial);

    }

    interface Presenter extends BasePresenter{
        void fetchNews(String date,boolean initial);
    }

}
