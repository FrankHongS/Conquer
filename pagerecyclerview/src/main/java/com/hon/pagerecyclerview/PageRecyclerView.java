package com.hon.pagerecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
@SuppressWarnings("all")
public class PageRecyclerView extends RecyclerView {

    private OnLoadMoreListener mOnLoadMoreListener;

    private float y1, y2;
    private PageScrollerListener mPageScrollerListener;
    private static final int Y_THRESHOLD = 100;

    public PageRecyclerView(Context context) {
        this(context, null);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mPageScrollerListener = new PageScrollerListener();

        addOnScrollListener(new PageScrollerListener());

    }

    void loadMore() {

        Adapter adapter = getAdapter();
        if (adapter instanceof PageAdapter) {
            PageAdapter pageAdapter = (PageAdapter) adapter;

            if(pageAdapter.canLoadMore()){
                if(!pageAdapter.isLoading()){
                    pageAdapter.showLoading();
                }
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }

    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {

        void onLoadMore();

    }
}
