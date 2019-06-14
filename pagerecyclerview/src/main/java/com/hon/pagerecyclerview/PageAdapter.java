package com.hon.pagerecyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.pagerecyclerview.item.BottomPageItem;
import com.hon.pagerecyclerview.item.LoadingPageItem;
import com.hon.pagerecyclerview.item.PageItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
@SuppressWarnings("all")
public abstract class PageAdapter extends RecyclerView.Adapter<BasePageViewHolder<PageItem>> {

    protected List<PageItem> itemList;

    private BasePageViewHolder<PageItem> mLoadingViewHolder;
    private BasePageViewHolder<PageItem> mErrorViewHolder;
    private BasePageViewHolder<PageItem> mBottomViewHolder;

    private boolean isLoading =false;
    private boolean mIsErrorShowing = false;
    private boolean isBotomShowing = false;

    private PageRecyclerView mTargetView;

    public PageAdapter(List<PageItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public BasePageViewHolder<PageItem> onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case PageItem.NORMAL_ITEM:
                return createNormalViewHolder(parent);
            case PageItem.LOADING_ITEM:
                if (mLoadingViewHolder != null)
                    return mLoadingViewHolder;
                else
                    return new LoadingViewHolder(
                            LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.default_loading_layout, parent, false)
                    );
            case PageItem.ERROR_ITEM:
                if (mErrorViewHolder != null)
                    return mErrorViewHolder;
                else
                    return new ErrorViewHolder(
                            LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.default_error_layout, parent, false)
                    );
            case PageItem.BOTTOM_ITEM:
                if (mBottomViewHolder != null)
                    return mBottomViewHolder;
                else
                    return new BottomViewHolder(
                            LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.default_no_more_layout, parent, false)
                    );
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BasePageViewHolder<PageItem> holder, int position) {
        holder.bindView(itemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).itemType();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        Log.d("hong", "onAttachedToRecyclerView: ");

        if (recyclerView instanceof PageRecyclerView) {
            this.mTargetView = (PageRecyclerView) recyclerView;
        } else {
            throw new RuntimeException("PageAdapter can only attach PageRecyclerView");
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BasePageViewHolder<PageItem> holder) {
        super.onViewAttachedToWindow(holder);

//        Log.d("hong", "onViewAttachedToWindow: ");
//        if (mTargetView != null && mTargetView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
//            int position = holder.getAdapterPosition();
//            if (position == itemList.size() - 1) {
//                PageRecyclerView.OnLoadMoreListener onLoadMoreListener = mTargetView.getOnLoadMoreListener();
//                if (onLoadMoreListener != null) {
//                    itemList.add(new LoadingPageItem());
//                    onLoadMoreListener.onLoadMore();
//                }
//            }
//        }
    }

    public void showLoading() {
        itemList.add(new LoadingPageItem());
        notifyItemInserted(itemList.size() - 1);

        isLoading=true;
    }

    public void hideLoading() {
        int last = itemList.size() - 1;
        itemList.remove(last);
        notifyItemRemoved(last);
        isLoading=false;
    }

    public void showBottom() {
        if (!isBotomShowing) {
            hideLoading();
            itemList.add(new BottomPageItem());
            notifyItemInserted(itemList.size() - 1);

            isBotomShowing = true;
        }
    }

    public void addAll(boolean initial,List<PageItem> items){
        if(!initial){
            hideLoading();
        }
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        isBotomShowing=false;
        itemList.clear();
        notifyDataSetChanged();
    }

    public void insertItems(boolean initial, int positionStart, int itemCount) {
        if(!initial){
            hideLoading();
        }
//        notifyItemRangeInserted(positionStart, itemCount);
    }

    boolean isLoading(){
        return isLoading;
    }

    boolean canLoadMore(){
        return !isBotomShowing;
    }

    protected abstract BasePageViewHolder<PageItem> createNormalViewHolder(ViewGroup parent);

    protected void setLoadingViewHolder(BasePageViewHolder<PageItem> viewHolder) {
        this.mLoadingViewHolder = viewHolder;
    }

    protected void setErrorViewHolder(BasePageViewHolder<PageItem> viewHolder) {
        this.mErrorViewHolder = viewHolder;
    }

    protected void setBottomViewHolder(BasePageViewHolder<PageItem> viewHolder) {
        this.mBottomViewHolder = viewHolder;
    }

    static class LoadingViewHolder extends BasePageViewHolder<PageItem> {

        LoadingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(PageItem item, int position) {

        }

    }

    static class ErrorViewHolder extends BasePageViewHolder<PageItem> {

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
