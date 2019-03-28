package com.hon.conquer.ui.news;

import com.hon.conquer.util.RetrofitImpl;
import com.hon.conquer.util.ToastUtil;
import com.hon.conquer.vo.news.ZhihuDailyNews;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Frank_Hon on 3/28/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class NewsPresenter implements NewsContract.Presenter {

    private RetrofitImpl mRetrofitImpl;
    private NewsContract.View mView;

    public NewsPresenter(NewsContract.View view) {
        mRetrofitImpl = RetrofitImpl.getInstance();

        this.mView = view;
    }

    @Override
    public void fetchNews(String date, boolean initial) {
        mRetrofitImpl.getNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDailyNews>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (initial)
                            mView.startRefreshing();
                    }

                    @Override
                    public void onNext(ZhihuDailyNews news) {
                        mView.showNews(news,initial);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("error in loading news");
                        if (initial)
                            mView.stopRefreshing();
                    }

                    @Override
                    public void onComplete() {
                        if (initial)
                            mView.stopRefreshing();
                    }
                });

    }
}
