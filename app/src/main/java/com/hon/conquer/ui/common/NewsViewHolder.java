package com.hon.conquer.ui.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hon.conquer.R;
import com.hon.conquer.vo.news.ZhihuDailyNewsDetail;
import com.hon.optimizedrecyclerviewlib.BaseViewHolder;

/**
 * Created by Frank on 2018/2/25.
 * E-mail:frank_hon@foxmail.com
 */

class NewsViewHolder extends BaseViewHolder<ZhihuDailyNewsDetail> {
    private RelativeLayout mNewsItem;
    private TextView mNewsTitle;
    private ImageView mNewsImage;

    private Context mContext;
    private Fragment mFragment;
    private NewsAdapter.OnItemClickListener mOnItemClickListener;

    NewsViewHolder(ViewGroup parent, Fragment fragment, NewsAdapter.OnItemClickListener listener) {
        super(parent, R.layout.item_news_layout);

        mContext = parent.getContext();
        mFragment = fragment;
        mOnItemClickListener = listener;

        mNewsItem = itemView.findViewById(R.id.rl_news_item);
        mNewsTitle = itemView.findViewById(R.id.tv_news_item);
        mNewsImage = itemView.findViewById(R.id.iv_news_item);
    }

    @Override
    public void setData(ZhihuDailyNewsDetail data, int position) {
        mNewsTitle.setText(data.getTitle());
        // 传Fragment或Activity给Glide，使其拥有相同的生命周期
        //优化RecyclerView滚动时加载图片方式
        //Glide.with(context).resumeRequests();
        //Glide.with(context).pauseRequests(); TODO

        Glide.with(mFragment)
                .load(data.getImages().get(0))
//                .apply(new RequestOptions()
//                        .placeholder(R.mipmap.placeholder))
//                .placeholder(R.mipmap.placeholder)
                .into(mNewsImage);

        mNewsItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null)
                            mOnItemClickListener.onClick(position);
                    }
                }
        );
    }

}

