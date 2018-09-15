package com.hon.conquer.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.conquer.BaseFragment;
import com.hon.conquer.ui.MainActivity;
import com.hon.conquer.R;
import com.hon.conquer.api.NewsService;
import com.hon.conquer.ui.common.NewsAdapter;
import com.hon.conquer.ui.common.NewsItemDivider;
import com.hon.conquer.ui.news.newsdetail.NewsDetailActivity;
import com.hon.conquer.util.CalendarUtil;
import com.hon.conquer.util.RetrofitUtil;
import com.hon.conquer.util.ToastUtil;
import com.hon.conquer.util.Util;
import com.hon.conquer.vo.news.ZhihuDailyNews;
import com.hon.conquer.vo.news.ZhihuDailyNewsDetail;
import com.hon.optimizedrecyclerviewlib.OptimizedRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Frank on 2018/1/26.
 * E-mail:frank_hon@foxmail.com
 */

public class NewsFragment extends BaseFragment {

    // unit dip
    private static final int ITEM_DIVIDER = 1;

    private OptimizedRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<ZhihuDailyNewsDetail> mNewsItemList = new ArrayList<>();
    private NewsAdapter mNewsAdapter;

    private CalendarUtil mCalendarUtil;

    private NewsService mNewsService=RetrofitUtil.createNewsService();

    private static final int FIRST_INTERVAL = 5;
    private static final int INTERVAL = 6;

    private int mStartIndex = -1;
    private int mEndIndex = -1;
    private int mDayCount = 0;

    int articleId;
    String articleTitle;

    public NewsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_news);
        mSwipeRefreshLayout = view.findViewById(R.id.srl_news);
        mNewsAdapter = new NewsAdapter(this);
        mNewsAdapter.setOnItemClickListener(position -> {
            articleId=mNewsAdapter.get(position).getId();
            articleTitle=mNewsAdapter.get(position).getTitle();
            Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra(NewsDetailActivity.KEY_ARTICLE_ID,articleId);
            intent.putExtra(NewsDetailActivity.KEY_ARTICLE_TITLE,articleTitle);
            startActivity(intent);
        });
        mRecyclerView.setAdapterWithLoading(mNewsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    ((MainActivity) getActivity()).hideBottomNavigationView();
                } else if (dy < 0) {
                    ((MainActivity) getActivity()).showBottomNavigationView();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){//TODO bug
                    case RecyclerView.SCROLL_STATE_IDLE:
//                        Glide.with(NewsFragment.this).resumeRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
//                        Glide.with(NewsFragment.this).pauseRequests();
                        break;
                    default:
                        break;
                }
            }
        });
        mRecyclerView.addItemDecoration(new NewsItemDivider(ITEM_DIVIDER, Util.getColor(R.color.colorDividerColor)));
//        mRecyclerView.setItemAnimator();
        mRecyclerView.setOnLoadMoreListener(() -> new Handler().postDelayed(this::loadData, 5000));
        mSwipeRefreshLayout.setOnRefreshListener(()->onRefresh(mCalendarUtil.getCurrentDate()));

        mCalendarUtil = new CalendarUtil(this);
        mCalendarUtil.setOnDismissListener(dialog -> fab.show());
        mCalendarUtil.setOnDateSetListener((view1, year, month, day) ->
                onRefresh(CalendarUtil.getSelectedDate(year, month, day)));
        fab.setOnClickListener(v -> {
            // hide()&show() by ViewPropertyAnimator
            fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    mCalendarUtil.showDatePickerDialog();
                }
            });
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchNewsDataByNetwork(mCalendarUtil.getCurrentDate(), createLoadingObserver(true));
    }

    @Override
    public void setToolbarTitle(Toolbar toolbar) {
        toolbar.setTitle(R.string.bottom_news);
    }

    private void fetchNewsDataByNetwork(String date, Observer<ZhihuDailyNews> observer) {
        mNewsService.getNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void loadInitialData() {
        mRecyclerView.setRefreshing(true);
        mNewsAdapter.addAll(mNewsItemList.subList(0, FIRST_INTERVAL));
        mRecyclerView.setRefreshing(false);
        mStartIndex = FIRST_INTERVAL;
        mEndIndex = FIRST_INTERVAL + INTERVAL;
    }

    private void loadData() {
        if (mStartIndex == mEndIndex && mStartIndex == -1) {
            if (mDayCount < 3) {
                fetchNewsDataByNetwork(mCalendarUtil.getLastDay(mDayCount), createLoadingObserver(false));
            } else {
                mNewsAdapter.addAll(new ArrayList<>());
            }
        } else {
            if (mNewsItemList.size() > mEndIndex) {
                mNewsAdapter.addAll(mNewsItemList.subList(mStartIndex, mEndIndex));
                mStartIndex = mEndIndex;
                mEndIndex += INTERVAL;
            } else if (mNewsItemList.size() <= mEndIndex && mNewsItemList.size() >= mStartIndex + 1) {
                mNewsAdapter.addAll(mNewsItemList.subList(mStartIndex, mNewsItemList.size()));
                mStartIndex = mEndIndex = -1;
            }
        }
    }

    private void onRefresh(String date){
        mNewsAdapter.clear();
        mDayCount = 0;
        fetchNewsDataByNetwork(date, createLoadingObserver(true));
    }

    private Observer<ZhihuDailyNews> createLoadingObserver(boolean initial) {
        return new Observer<ZhihuDailyNews>() {
            @Override
            public void onSubscribe(Disposable d) {
                if(initial)
                    mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onNext(ZhihuDailyNews news) {
                mNewsItemList = news.getStrories();
                loadInitialData();
                mDayCount++;
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("error in loading news");
                Timber.e("error in loading news");
                if(initial)
                    mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onComplete() {
                ToastUtil.showToast("success :)");
                if(initial)
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    public void smoothScrollToFirst(){
        mRecyclerView.smoothScrollToPosition(0);
    }

}

