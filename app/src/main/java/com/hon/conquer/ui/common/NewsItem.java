package com.hon.conquer.ui.common;

import com.hon.pagerecyclerview.item.PageItem;

/**
 * Created by Frank_Hon on 3/28/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class NewsItem implements PageItem {

    private String title;

    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int itemType() {
        return PageItem.NORMAL_ITEM;
    }
}
