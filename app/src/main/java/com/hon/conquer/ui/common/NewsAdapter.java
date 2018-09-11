package com.hon.conquer.ui.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hon.conquer.R;
import com.hon.conquer.util.ToastUtil;
import com.hon.conquer.vo.news.ZhihuDailyNewsDetail;
import com.hon.optimizedrecyclerviewlib.BaseAdapter;
import com.hon.optimizedrecyclerviewlib.BaseViewHolder;

import java.util.List;

/**
 * Created by Frank on 2018/1/31.
 * E-mail:frank_hon@foxmail.com
 */

public class NewsAdapter extends BaseAdapter<ZhihuDailyNewsDetail,BaseViewHolder<ZhihuDailyNewsDetail>> {

    private Fragment mNewsFragment;
    private OnItemClickListener mOnItemClickListener;

    public NewsAdapter(Fragment newsFragment){
        this.mNewsFragment=newsFragment;
    }

    @Override
    public BaseViewHolder<ZhihuDailyNewsDetail> onNewCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent,mNewsFragment,mOnItemClickListener);
    }

    @Override
    public void onNewBindViewHolder(BaseViewHolder<ZhihuDailyNewsDetail> holder, int position) {
        holder.setData(get(position),position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}
