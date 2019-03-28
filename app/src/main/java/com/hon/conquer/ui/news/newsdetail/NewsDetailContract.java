package com.hon.conquer.ui.news.newsdetail;

import com.hon.conquer.base.BasePresenter;
import com.hon.conquer.base.BaseView;

/**
 * Created by Frank on 2018/7/1.
 * E-mail:frank_hon@foxmail.com
 */

public interface NewsDetailContract {

    interface View extends BaseView<Presenter>{
        void showContent(String body);
    }

    interface Presenter extends BasePresenter{
        void getNewsDetail(int newsId);
    }
}
