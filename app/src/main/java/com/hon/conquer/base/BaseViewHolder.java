package com.hon.conquer.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Frank on 2018/9/29.
 * E-mail:frank_hon@foxmail.com
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(T data, int position);
}
