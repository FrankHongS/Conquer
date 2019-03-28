package com.hon.pagerecyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
@SuppressWarnings("all")
public class PageRecyclerView extends RecyclerView {

    private OnLoadMoreListener mOnLoadMoreListener;

    private float y1,y2;
    private ScrollDetector mScrollDetector;
    private static final int Y_THRESHOLD=100;

    public PageRecyclerView(Context context) {
        this(context,null);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mScrollDetector=new ScrollDetector();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                y1=e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                y2=e.getY();
                if(y2<y1-Y_THRESHOLD){
                    mScrollDetector.handleLoadMore(this);
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(e);
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{

        void onLoadMore();

    }
}
