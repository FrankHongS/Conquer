package com.hon.conquer.ui.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hon.conquer.R;
import com.hon.conquer.db.FavoriteNews;
import com.hon.conquer.ui.common.BaseViewHolder;
import com.hon.conquer.ui.common.NewsAdapter;

import java.util.List;

/**
 * Created by Frank on 2018/9/29.
 * E-mail:frank_hon@foxmail.com
 */

public class NewsFavoritesAdapter extends RecyclerView.Adapter<NewsFavoritesAdapter.NewsFavoritesViewHolder>{

    private Context mContext;
    private List<FavoriteNews> mFavoriteNewsList;

    private OnItemClickListener mOnItemClickListener;

    public NewsFavoritesAdapter(Context context, List<FavoriteNews> favoriteNews){
        this.mContext=context;
        this.mFavoriteNewsList=favoriteNews;
    }

    @Override
    public NewsFavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(mContext).inflate(R.layout.item_news_layout,parent,false);
        return new NewsFavoritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsFavoritesViewHolder holder, int position) {
        holder.bindView(mFavoriteNewsList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mFavoriteNewsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public class NewsFavoritesViewHolder extends BaseViewHolder<FavoriteNews>{

        RelativeLayout newsItem;
        TextView newsTitle;
        ImageView newsImage;

        public NewsFavoritesViewHolder(View itemView) {
            super(itemView);

            newsItem = itemView.findViewById(R.id.rl_news_item);
            newsTitle = itemView.findViewById(R.id.tv_news_item);
            newsImage = itemView.findViewById(R.id.iv_news_item);
        }

        @Override
        public void bindView(FavoriteNews data, int position) {
            Glide.with(mContext)
                    .load(data.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(newsImage);

            newsTitle.setText(data.getTitle());

            newsItem.setOnClickListener(view->mOnItemClickListener.onClick(position));
        }
    }
}
