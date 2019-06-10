package com.hon.conquer.ui.news.newsdetail;

import com.hon.conquer.util.RetrofitImpl;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Frank_Hon on 10/12/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class NewsDetailPresenter implements NewsDetailContract.Presenter, LifecycleObserver {

    private NewsDetailContract.View mView;

    private Disposable mNewsContentDisposable;

    public NewsDetailPresenter(Lifecycle lifecycle,NewsDetailContract.View view) {
        this.mView = view;
        view.setPresenter(this);

        lifecycle.addObserver(this);
    }

    @Override
    public void getNewsDetail(int newsId) {
        mNewsContentDisposable = RetrofitImpl.getInstance().getNewsContent(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(content -> mView.showContent(content.getBody()));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void dispose() {
        if (mNewsContentDisposable != null) {
            mNewsContentDisposable.dispose();
        }
    }

}
