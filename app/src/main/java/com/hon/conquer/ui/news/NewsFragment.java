package com.hon.conquer.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hon.conquer.ConquerExecutors;
import com.hon.conquer.R;
import com.hon.conquer.base.BaseFragment;
import com.hon.conquer.db.ConquerDatabase;
import com.hon.conquer.db.FavoriteNews;
import com.hon.conquer.ui.MainActivity;
import com.hon.conquer.ui.common.NewsAdapter;
import com.hon.conquer.ui.common.NewsItem;
import com.hon.conquer.ui.common.NewsItemDivider;
import com.hon.conquer.ui.news.newsdetail.NewsDetailActivity;
import com.hon.conquer.util.CalendarUtil;
import com.hon.conquer.util.Util;
import com.hon.conquer.vo.event.NewsFavoritesEvent;
import com.hon.conquer.vo.news.ZhihuDailyNews;
import com.hon.conquer.vo.news.ZhihuDailyNewsDetail;
import com.hon.optimizedrecyclerviewlib.OptimizedRecyclerView;
import com.hon.pagerecyclerview.PageRecyclerView;
import com.hon.pagerecyclerview.item.PageItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Frank on 2018/1/26.
 * E-mail:frank_hon@foxmail.com
 */

@SuppressWarnings("all")
public class NewsFragment extends BaseFragment implements NewsContract.View {

    private PageRecyclerView mNewsListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<PageItem> mNewsItemList = new ArrayList<>();
    private List<ZhihuDailyNewsDetail> mNewsDetailList = new ArrayList<>();

    private NewsAdapter mNewsAdapter;

    private CalendarUtil mCalendarUtil;

    private static final int FIRST_INTERVAL = 5;
    private static final int INTERVAL = 6;

    private int mStartIndex = -1;
    private int mEndIndex = -1;
    private int mDayCount = 0;

    private ZhihuDailyNewsDetail mCurrentClickedItem;

    private NewsContract.Presenter mPresenter;
    private int mInitialLength;

    private static final int PAGE_SIZE = 5;

    public NewsFragment() {
        // no parameter constructor is necessary
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initViews(view);

        // initial news length
        mInitialLength = Util.getScreenHeight() / getResources().getDimensionPixelSize(R.dimen.item_news_height);

        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFab();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addNewsToFavorites(NewsFavoritesEvent event) {

        if (mCurrentClickedItem != null) {
            FavoriteNews news = new FavoriteNews(mCurrentClickedItem);
            ConquerExecutors.getInstance().getIoExecutors().execute(
                    () -> ConquerDatabase.getInstance()
                            .newsFavoritesDao()
                            .insert(news)
            );

        }
    }

    private void initViews(View view) {
        mNewsListView = view.findViewById(R.id.prv_news);
        mSwipeRefreshLayout = view.findViewById(R.id.srl_news);
        mNewsAdapter = new NewsAdapter(mNewsItemList);
        mNewsAdapter.setOnItemClickListener(position -> {

            mCurrentClickedItem = mNewsDetailList.get(position);

            if (mCurrentClickedItem != null) {
                int articleId = mCurrentClickedItem.getId();
                String articleTitle = mCurrentClickedItem.getTitle();
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra(NewsDetailActivity.KEY_ARTICLE_ID, articleId);
                intent.putExtra(NewsDetailActivity.KEY_ARTICLE_TITLE, articleTitle);
                startActivity(intent);
            }
        });

        mNewsListView.setAdapter(mNewsAdapter);
        mNewsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                switch (newState) {//TODO bug
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
        mNewsListView.addItemDecoration(new NewsItemDivider(Util.getColor(R.color.colorDividerColor)));
        mNewsListView.setOnLoadMoreListener(() -> new Handler().postDelayed(this::loadData, 5000));

        mSwipeRefreshLayout.setOnRefreshListener(() -> onRefresh(mCalendarUtil.getCurrentDate()));

        mCalendarUtil = new CalendarUtil(this);
        mCalendarUtil.setOnDismissListener(dialog -> fab.show());
        mCalendarUtil.setOnDateSetListener((view1, year, month, day) ->
                onRefresh(CalendarUtil.getSelectedDate(year, month, day)));
    }

    private void initFab() {
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
        mPresenter.fetchNews(mCalendarUtil.getCurrentDate(), true);
    }

    @Override
    public void setToolbarTitle(Toolbar toolbar) {
        toolbar.setTitle(R.string.bottom_news);
    }

    private void onRefresh(String date) {
        mNewsAdapter.clear();
        mDayCount = 0;
        mPresenter.fetchNews(date, true);
    }

    private void loadData() {

        if (!mNewsDetailList.isEmpty()) {
            loadExistingData(false, PAGE_SIZE);
        } else {
            if (mDayCount < 3) {
                mPresenter.fetchNews(mCalendarUtil.getLastDay(mDayCount), false);
            } else {
                mNewsAdapter.showBottom();
            }
        }

    }

    private void loadInitialData() {
        loadExistingData(true, mInitialLength);
        if(mInitialLength > mNewsDetailList.size()){
            mNewsAdapter.showLoading();
            new Handler(Looper.getMainLooper()).postDelayed(this::loadData,5000);
        }
    }

    private void loadExistingData(boolean initial, int l) {
        int length = l > mNewsDetailList.size() ? mNewsDetailList.size() : l;

        List<PageItem> tempList=new ArrayList<>();
        for(int i=0;i<length;i++){

            ZhihuDailyNewsDetail newsDetail=mNewsDetailList.get(i);

            NewsItem newsItem = new NewsItem();
            newsItem.setTitle(newsDetail.getTitle());
            newsItem.setImageUrl(newsDetail.getImages().get(0));
            tempList.add(newsItem);
        }

        mNewsAdapter.addAll(initial,tempList);

        if (length < mNewsDetailList.size()) {
            mNewsDetailList = mNewsDetailList.subList(length,mNewsDetailList.size());
        } else {
            mNewsDetailList.clear();
        }

//        mNewsAdapter.notifyItemRangeInserted(mNewsItemList.size()-length, length);
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void startRefreshing() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showNews(ZhihuDailyNews news, boolean initial) {
        mNewsDetailList = news.getStrories();
        Log.d("hong", "showNews: "+mNewsDetailList);
        if (initial) {
            loadInitialData();
        } else {
            loadData();
        }
        mDayCount++;
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    public void smoothScrollToFirst() {
        mNewsListView.smoothScrollToPosition(0);
    }

}

