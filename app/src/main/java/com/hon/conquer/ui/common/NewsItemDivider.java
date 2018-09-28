package com.hon.conquer.ui.common;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hon.conquer.util.Util;
import com.hon.optimizedrecyclerviewlib.BaseAdapter;

import timber.log.Timber;

/**
 * Created by Frank on 2018/2/12.
 * E-mail:frank_hon@foxmail.com
 */

public class NewsItemDivider extends RecyclerView.ItemDecoration{

    // unit dip
    private static final int ITEM_DIVIDER = 1;

    private int mBottomDivider;
    private ColorDrawable mDividerColor;

    /**
     * News Item Divider
     */
    public NewsItemDivider(int color){
        mBottomDivider= Util.dip2px(ITEM_DIVIDER);
        if(color!=0){
            mDividerColor=new ColorDrawable(color);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        LinearLayoutManager layoutManager= (LinearLayoutManager) parent.getLayoutManager();
        if(layoutManager.getOrientation()==LinearLayoutManager.VERTICAL){
            if(parent.getChildAdapterPosition(view)!=layoutManager.getItemCount()-1){
                outRect.set(0,0,0,mBottomDivider);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        LinearLayoutManager layoutManager= (LinearLayoutManager) parent.getLayoutManager();
        int childCount=layoutManager.getChildCount();
        if(mDividerColor==null||childCount==0)
            return;

        int left,right,bottom,top;
        if(layoutManager.getOrientation()==LinearLayoutManager.VERTICAL){
            for(int i=0;i<childCount-1;i++){
                View child=parent.getChildAt(i);
                RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();

                left=layoutManager.getDecoratedLeft(child);
                top=child.getBottom()+params.bottomMargin;
                right=parent.getWidth()-left;
                bottom=top+mBottomDivider;
                mDividerColor.setBounds(left,top,right,bottom);
                mDividerColor.draw(c);
            }
        }

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
