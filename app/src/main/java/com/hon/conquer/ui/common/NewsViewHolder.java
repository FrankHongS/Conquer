package com.hon.conquer.ui.common;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hon.conquer.R;
import com.hon.conquer.vo.news.NewsItem;
import com.hon.pagerecyclerview.BasePageViewHolder;
import com.hon.pagerecyclerview.item.PageItem;

/**
 * Created by Frank_Hon on 3/28/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class NewsViewHolder extends BasePageViewHolder<PageItem> {

    private RelativeLayout mNewsItem;
    private TextView mNewsTitle;
    private ImageView mNewsImage;

    private NewsAdapter.OnItemClickListener mOnItemClickListener;

    public NewsViewHolder(View itemView, NewsAdapter.OnItemClickListener listener) {
        this(itemView,
                (int) itemView.getContext().getResources().getDimension(R.dimen.item_news_height),
                listener);
    }

    public NewsViewHolder(View itemView, int itemHeight,NewsAdapter.OnItemClickListener listener) {
        super(itemView, itemHeight);

        mNewsItem = itemView.findViewById(R.id.rl_news_item);
        mNewsTitle = itemView.findViewById(R.id.tv_news_item);
        mNewsImage = itemView.findViewById(R.id.iv_news_item);

        this.mOnItemClickListener=listener;
    }

    @Override
    public void bindView(PageItem item,int position) {
        NewsItem newsItem= (NewsItem) item;

        mNewsTitle.setText(newsItem.getTitle());

        Glide.with(itemView)
                .load(newsItem.getImageUrl())
                .into(mNewsImage);

        mNewsItem.setOnClickListener(v -> mOnItemClickListener.onClick(position));
    }
}
