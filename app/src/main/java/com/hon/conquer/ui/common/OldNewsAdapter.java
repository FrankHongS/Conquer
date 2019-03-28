package com.hon.conquer.ui.common;

import android.view.ViewGroup;

import com.hon.conquer.vo.news.ZhihuDailyNewsDetail;
import com.hon.optimizedrecyclerviewlib.BaseAdapter;
import com.hon.optimizedrecyclerviewlib.BaseViewHolder;

import androidx.fragment.app.Fragment;

/**
 * Created by Frank on 2018/1/31.
 * E-mail:frank_hon@foxmail.com
 */

public class OldNewsAdapter extends BaseAdapter<ZhihuDailyNewsDetail,BaseViewHolder<ZhihuDailyNewsDetail>> {

    private Fragment mNewsFragment;
    private OnItemClickListener mOnItemClickListener;

    public OldNewsAdapter(Fragment newsFragment){
        this.mNewsFragment=newsFragment;
    }

    @Override
    public BaseViewHolder<ZhihuDailyNewsDetail> onNewCreateViewHolder(ViewGroup parent, int viewType) {
        return new OldNewsViewHolder(parent,mNewsFragment,mOnItemClickListener);
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
