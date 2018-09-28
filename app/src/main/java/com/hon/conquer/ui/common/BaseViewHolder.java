package com.hon.conquer.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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
