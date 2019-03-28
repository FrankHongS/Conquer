package com.hon.pagerecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.pagerecyclerview.item.BottomPageItem;
import com.hon.pagerecyclerview.item.LoadingPageItem;
import com.hon.pagerecyclerview.item.PageItem;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
@SuppressWarnings("all")
public abstract class PageAdapter extends RecyclerView.Adapter<BasePageViewHolder<PageItem>> {

    protected Context context;
    protected List<PageItem> itemList;

    private BasePageViewHolder<PageItem> mLoadingViewHolder;
    private BasePageViewHolder<PageItem> mErrorViewHolder;
    private BasePageViewHolder<PageItem> mBottomViewHolder;

    private boolean mIsLoadingShowing = false;
    private boolean mIsErrorShowing = false;
    private boolean mIsBottomShowing=false;

    public PageAdapter(Context context, List<PageItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public BasePageViewHolder<PageItem> onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case PageItem.NORMAL_ITEM:
                return createNormalViewHolder(parent);
            case PageItem.LOADING_ITEM:
                if(mLoadingViewHolder!=null)
                    return mLoadingViewHolder;
                else
                    return new LoadingViewHolder(
                            LayoutInflater.from(context)
                            .inflate(R.layout.default_loading_layout,parent,false)
                    );
            case PageItem.ERROR_ITEM:
                if(mErrorViewHolder!=null)
                    return mErrorViewHolder;
                else
                    return new ErrorViewHolder(
                            LayoutInflater.from(context)
                                    .inflate(R.layout.default_error_layout,parent,false)
                    );
            case PageItem.BOTTOM_ITEM:
                if (mBottomViewHolder != null)
                    return mBottomViewHolder;
                else
                    return new BottomViewHolder(
                            LayoutInflater.from(context)
                                    .inflate(R.layout.default_no_more_layout, parent, false)
                    );
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BasePageViewHolder<PageItem> holder, int position) {
        holder.bindView(itemList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).itemType();
    }

    public void showLoading() {
        if (!mIsLoadingShowing&&!mIsBottomShowing) {
            itemList.add(new LoadingPageItem());
            notifyItemInserted(itemList.size() - 1);

            mIsLoadingShowing = true;
        }
    }

    public void hideLoading() {
        if (mIsLoadingShowing) {
            int last = itemList.size() - 1;
            itemList.remove(last);
            notifyItemRemoved(last);

            mIsLoadingShowing = false;
        }
    }

    public void showBottom(){
        if(!mIsBottomShowing){

            hideLoading();

            itemList.add(new BottomPageItem());
            notifyItemInserted(itemList.size() - 1);

            mIsBottomShowing = true;
        }
    }

    public boolean shouldLoadMore() {
        return !mIsLoadingShowing&&!mIsBottomShowing;
    }

    protected abstract BasePageViewHolder<PageItem> createNormalViewHolder(ViewGroup parent);

    protected void setLoadingViewHolder(BasePageViewHolder<PageItem> viewHolder){
        this.mLoadingViewHolder=viewHolder;
    }

    protected void setErrorViewHolder(BasePageViewHolder<PageItem> viewHolder){
        this.mErrorViewHolder=viewHolder;
    }

    protected void setBottomViewHolder(BasePageViewHolder<PageItem> viewHolder) {
        this.mBottomViewHolder = viewHolder;
    }

    static class LoadingViewHolder extends BasePageViewHolder<PageItem>{

        LoadingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(PageItem item, int position) {

        }

    }

    static class ErrorViewHolder extends BasePageViewHolder<PageItem>{

        ErrorViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(PageItem item, int position) {

        }
    }

    static class BottomViewHolder extends BasePageViewHolder<PageItem> {

        public BottomViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(PageItem item, int position) {

        }
    }
}
