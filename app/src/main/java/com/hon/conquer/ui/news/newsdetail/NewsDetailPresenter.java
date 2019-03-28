package com.hon.conquer.ui.news.newsdetail;

import com.hon.conquer.api.NewsService;
import com.hon.conquer.util.RetrofitImpl;
import com.hon.conquer.vo.news.ZhihuDailyContent;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Frank_Hon on 10/12/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class NewsDetailPresenter implements NewsDetailContract.Presenter{

    private NewsService mNewsContentService;

    private NewsDetailContract.View mView;

    public NewsDetailPresenter(NewsDetailContract.View view){

        this.mNewsContentService = RetrofitImpl.createNewsContentService();
        this.mView=view;

        view.setPresenter(this);
    }

    @Override
    public void getNewsDetail(int newsId) {
        mNewsContentService.getNewsContent(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDailyContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ZhihuDailyContent content) {
                        mView.showContent(content.getBody());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
