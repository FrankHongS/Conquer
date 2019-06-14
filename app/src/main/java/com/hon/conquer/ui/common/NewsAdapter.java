package com.hon.conquer.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hon.conquer.R;
import com.hon.pagerecyclerview.BasePageViewHolder;
import com.hon.pagerecyclerview.PageAdapter;
import com.hon.pagerecyclerview.item.PageItem;

import java.util.List;

/**
 * Created by Frank_Hon on 3/28/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class NewsAdapter extends PageAdapter {

    private OnItemClickListener mOnItemClickListener;

    public NewsAdapter(List<PageItem> itemList) {
        super(itemList);
    }

    @Override
    protected BasePageViewHolder<PageItem> createNormalViewHolder(ViewGroup parent) {
        return new NewsViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_layout,parent,false),
                mOnItemClickListener
        );
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

}
